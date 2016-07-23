package com.demo.music.downloader.httpdownloader;

import com.demo.music.downloader.HttpDownloader;
import com.github.axet.wget.WGet;
import com.github.axet.wget.info.DownloadInfo;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

public class WgetHttpDownloader implements HttpDownloader {

    @Override
    public void download(String url, OutputStream out) throws IOException {
        throw new IOException("Not support");
    }

    @Override
    public void download(String url, File target) throws IOException {
        DownloadInfo info = new DownloadInfo(new URL(url));
        WGet w = new WGet(info, target);
        w.download();
    }
}
