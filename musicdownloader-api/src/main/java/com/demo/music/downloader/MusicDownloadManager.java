package com.demo.music.downloader;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by tuedang on 6/29/16.
 */

public class MusicDownloadManager {

    private DownloadCallback downloadCallback;

    private MusicDownloadManager(DownloadCallback downloadCallback) {
        this.downloadCallback = downloadCallback;
    }

    public static MusicDownloadManager getInstance(DownloadCallback downloadCallback) {
        if (downloadCallback == null) {
            throw new IllegalArgumentException("Must declare a download callback");
        }
        return new MusicDownloadManager(downloadCallback);
    }

    public void download(String url, String downloadDest) throws ExecutionException, InterruptedException, IOException {
        MusicDownloadBrokerHandler musicDownloadBrokerHandler = new MusicDownloadBrokerHandler(url, downloadDest, false, downloadCallback);

        //TODO: bad implementation, ask expert to improve this.
        CompletableFuture.runAsync(() -> {
            try {
                musicDownloadBrokerHandler.download();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).get();

    }
}
