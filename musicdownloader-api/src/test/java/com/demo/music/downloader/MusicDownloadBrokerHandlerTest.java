package com.demo.music.downloader;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MusicDownloadBrokerHandlerTest {
    @Test
    public void runAll() throws ExecutionException, InterruptedException {
        String url = "http://chiasenhac.com/nghe-album/invocation-part-3~medwyn-goodall~1382118.html";
        String dest = "/Data/NCT/";
        MusicDownloadBrokerHandler musicDownloadBrokerHandler = new MusicDownloadBrokerHandler(url, dest, false, new DownloadCallback() {
            @Override
            public void updateStatus(Status status) {
                System.out.println(status);
            }
        });
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> result = es.submit(musicDownloadBrokerHandler);
        result.get();

    }
}
