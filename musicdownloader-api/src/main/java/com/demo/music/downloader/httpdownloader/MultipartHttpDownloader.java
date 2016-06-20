package com.demo.music.downloader.httpdownloader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.demo.common.FileUtils;
import com.demo.music.downloader.HttpDownloader;

public class MultipartHttpDownloader implements HttpDownloader{
	@Override
	public void download(String url, OutputStream out) throws IOException {
	}
	
    private int getFileSize(URL url) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            conn.getInputStream();
            return conn.getContentLength();
        } catch (IOException e) {
            return -1;
        } finally {
            conn.disconnect();
        }
    }
    
    public void downloadMultipart(String url, File target, int retry) throws IllegalStateException, IOException {
		for(int i=0; i<retry; i++) {
			try{
				downloadMultipart(url, target);
				break;
			}catch (Exception ex) {
				System.out.println("Download failed"+ex.getMessage());
			}
		}
    }
	public void downloadMultipart(String url, File target) throws IllegalStateException, IOException, InterruptedException, ExecutionException {
		if(url==null || url.isEmpty()) {
			return;
		}
		System.out.println("target="+target.getAbsolutePath());
		
		URL mURL = new URL(url);
		int totalContentLength = getFileSize(mURL);
		int buffer_size=calculateBufferSize(totalContentLength);
		int totalPart = (totalContentLength/buffer_size)+1;
		
		String fileName = FilenameUtils.getName(url);
		String fsize = FileUtils.byteCountToDisplaySize(totalContentLength);
		System.out.println(String.format("Downloading: %s in %s parts (%s)", fileName, totalPart, fsize));
		
		final HttpPart[] httpParts = new HttpPart[totalPart];
		for(int i=0; i<totalPart; i++) {
			HttpPart httpPart = new HttpPart(mURL, buffer_size * i, Math.min(buffer_size * (i + 1) -1, totalContentLength), totalContentLength);
			httpParts[i] = httpPart;
		}
		
		ByteArrayOutputStream[] finalResults = downloadParts(httpParts);
		
		FileOutputStream fout = new FileOutputStream(target);
		for (ByteArrayOutputStream r : finalResults) {
			r.writeTo(fout);
		}
		
		fout.flush();
		fout.close();
			
		System.out.println("Downloaded: "+fileName);
		
	}
	private ByteArrayOutputStream[] downloadParts(final HttpPart[] httpParts) throws InterruptedException {
		int partSize = httpParts.length;
		
		List<Callable<ByteArrayOutputStream>> tasks = new ArrayList<Callable<ByteArrayOutputStream>>();
		for (HttpPart httpPart : httpParts) {
			tasks.add(prepareDownloadPart(httpPart));
		}
		
		ThreadPoolExecutor es=(ThreadPoolExecutor)Executors.newFixedThreadPool(4);
		Map<Callable<ByteArrayOutputStream>, Future<ByteArrayOutputStream>> callingTasks = new LinkedHashMap<Callable<ByteArrayOutputStream>, Future<ByteArrayOutputStream>>();
		for(Callable<ByteArrayOutputStream> task: tasks) {
			callingTasks.put(task, es.submit(task));
		}
		
		ByteArrayOutputStream[] finalResults = new ByteArrayOutputStream[partSize];
		System.out.print(String.format("Completed part(total=%s):", partSize));
		while(es.getCompletedTaskCount() < es.getTaskCount()) {
			es.awaitTermination(2, TimeUnit.SECONDS);
			
			List<Callable<ByteArrayOutputStream>> callableKeys = new ArrayList<Callable<ByteArrayOutputStream>>(callingTasks.keySet());
			for (int i = 0; i < callableKeys.size(); i++) {
				Callable<ByteArrayOutputStream> task = callableKeys.get(i);
				Future<ByteArrayOutputStream> f = callingTasks.get(task);
				if (f.isDone()&& finalResults[i]==null) {
					try{
						finalResults[i] = f.get();
					}catch(Exception e) {
						System.out.println("download error at part="+i +"=>"+e.getMessage()+" RESTARTING....");
						callingTasks.put(task, es.submit(task));
					}
					
					System.out.print(String.format("(%s) ", i+1));
				}
			}
			
		}
		System.out.println("");
		return finalResults;
	}
	
	private Callable<ByteArrayOutputStream> prepareDownloadPart(final HttpPart httpPart) throws InterruptedException {
		Callable<ByteArrayOutputStream> task= new Callable<ByteArrayOutputStream>() {
			@Override
			public ByteArrayOutputStream call() throws Exception {
				int bufferSize = httpPart.getToByte()-httpPart.getFromByte();
				String byteRange = httpPart.getFromByte() + "-" + httpPart.getToByte();
				ByteArrayOutputStream bo = new ByteArrayOutputStream(bufferSize);
				HttpURLConnection conn = (HttpURLConnection)httpPart.getUrl().openConnection();
				conn.setRequestProperty("Range", "bytes=" + byteRange);
				InputStream inputStream = conn.getInputStream();
				IOUtils.copy(inputStream, bo);
				conn.disconnect();
				return bo;
			}
		};
		return task;
		
	}
	
	private int calculateBufferSize(int totalContentLength) {

		int buff=1024*1000*2; //2MB 1 part
		if(totalContentLength>7*FileUtils.ONE_MB) {
			buff=1024*1000*3;
		} else if(totalContentLength>20*FileUtils.ONE_MB) {
			buff=1024*1000*5;
		}else if(totalContentLength>40*FileUtils.ONE_MB) {
			buff=1024*1000*8;
		}
		return buff;
	}

}
