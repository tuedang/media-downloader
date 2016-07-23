package com.demo.music.downloader;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public interface HttpDownloader {

    void download(String url, OutputStream out) throws IOException;

    void download(String url, File target) throws IOException;

}
