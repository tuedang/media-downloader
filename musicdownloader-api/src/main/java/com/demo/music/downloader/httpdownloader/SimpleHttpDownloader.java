package com.demo.music.downloader.httpdownloader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.demo.music.downloader.HttpDownloader;

public class SimpleHttpDownloader implements HttpDownloader {
	public void download(String url, OutputStream out) throws IOException {
		if(url==null || url.isEmpty()) {
			return;
		}
		String fileName = FilenameUtils.getName(url); 
		System.out.println("Downloading: "+fileName);
		
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);

		HttpResponse response = client.execute(httpGet);

		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			InputStream in = new BufferedInputStream(response.getEntity().getContent());
			IOUtils.copy(in, out);
			IOUtils.closeQuietly(in);
			IOUtils.closeQuietly(out);
			
			out.flush();
			out.close();
			System.out.println("Downloaded: "+fileName);
		}
		
	}
}
