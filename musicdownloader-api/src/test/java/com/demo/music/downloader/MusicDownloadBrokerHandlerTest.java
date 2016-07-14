package com.demo.music.downloader;

import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MusicDownloadBrokerHandlerTest {
    @Test
    public void runAll() throws IOException, ExecutionException, InterruptedException {
        String dest = "/Data/NCT/";

        MusicDownloadManager musicDownloadManager = MusicDownloadManager.getInstance(status -> System.out.println(status));
//        musicDownloadManager.download("http://mp3.zing.vn/album/Tim-Lai-Bau-Troi-Tuan-Hung/ZWZ9E89F.html", dest);
        musicDownloadManager.download("http://www.nhaccuatui.com/playlist/anh-cu-di-di-single-hari-won.LSzTSgccoNrA.html", dest);

    }
}
