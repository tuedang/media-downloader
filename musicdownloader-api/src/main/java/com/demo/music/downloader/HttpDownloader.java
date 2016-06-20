package com.demo.music.downloader;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpDownloader {
    static final int MB = 0x100000;
    static final int KB = 0x400;

    void download(String url, OutputStream out) throws IOException;

}
