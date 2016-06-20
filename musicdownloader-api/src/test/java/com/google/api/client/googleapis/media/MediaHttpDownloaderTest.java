package com.google.api.client.googleapis.media;

import java.io.IOException;
import java.io.OutputStream;

import org.testng.annotations.Test;

import com.demo.music.downloader.HttpDownloader;
import com.demo.music.downloader.dropbox.DropboxOutputStream;
import com.demo.music.downloader.httpdownloader.StreamHttpDownloader;

public class MediaHttpDownloaderTest {
	@Test
	public void mulDownload() throws IOException {
		HttpDownloader downloader = new StreamHttpDownloader();
//		OutputStream out = new FileOutputStream(new File("d:/giong2.mp4"));
		OutputStream out = new DropboxOutputStream("Giong2.mp3");
		downloader.download("http://stream41.gonct.info/748b25d789ba4f386d088722bc36f7ea/510f62ce/NhacCuaTui798/Giong-HaAnhTuan_4ftb9.mp3", out);
		out.close();
	}

}

