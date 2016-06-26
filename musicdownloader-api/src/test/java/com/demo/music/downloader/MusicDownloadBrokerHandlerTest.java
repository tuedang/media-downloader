package com.demo.music.downloader;

import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MusicDownloadBrokerHandlerTest {
    @Test
    public void runAll() throws ExecutionException, InterruptedException {
        String url = "http://mp3.zing.vn/album/Tim-Lai-Bau-Troi-Tuan-Hung/ZWZ9E89F.html";
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
