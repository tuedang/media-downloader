package com.demo.music.downloader.httpdownloader;

import java.io.IOException;
import java.io.OutputStream;

import com.demo.music.downloader.HttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloader;
import com.google.api.client.googleapis.media.MediaHttpDownloaderProgressListener;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.apache.ApacheHttpTransport;

public class StreamHttpDownloader implements HttpDownloader{

	@Override
	public void download(String url, OutputStream out) throws IOException {
		MediaHttpDownloader downloader = new MediaHttpDownloader(new ApacheHttpTransport(), null);
		downloader.setProgressListener(new CustomProgressListener());
		downloader.setChunkSize(HttpDownloader.MB*2);
//		downloader.setDirectDownloadEnabled(true);
		downloader.download(new GenericUrl(url), out);
		
	}

}

class CustomProgressListener implements MediaHttpDownloaderProgressListener {
	public void progressChanged(MediaHttpDownloader downloader) {
		switch (downloader.getDownloadState()) {
		case MEDIA_IN_PROGRESS:
			System.out.println(Math.round(downloader.getProgress()*100)+"%");
			break;
		case MEDIA_COMPLETE:
			System.out.println("Download is complete!");
		default:
			break;
		}
	}
}
