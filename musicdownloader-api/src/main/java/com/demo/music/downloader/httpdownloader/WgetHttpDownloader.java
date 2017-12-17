package com.demo.music.downloader.httpdownloader;

import com.demo.music.downloader.HttpDownloader;
import com.github.axet.wget.WGet;
import com.github.axet.wget.info.DownloadInfo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WgetHttpDownloader implements HttpDownloader {

    private static ExecutorService executorService;

    public static WgetHttpDownloader getInstance(int poolsize) {
        executorService = Executors.newFixedThreadPool(poolsize);
        return new WgetHttpDownloader();
    }

    @Override
    public void download(String url, OutputStream out) throws IOException {
        throw new IOException("Not support");
    }

    @Override
    public void download(String url, File target) throws IOException {
        executorService.submit(() -> {
            try {
                DownloadInfo info = new DownloadInfo(new URL(url));
                WGet w = new WGet(info, target);
                w.download();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
