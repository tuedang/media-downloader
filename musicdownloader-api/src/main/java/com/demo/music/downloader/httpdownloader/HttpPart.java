package com.demo.music.downloader.httpdownloader;

import java.net.URL;

public class HttpPart {
    private URL url;
    private int totalLength;
    private int fromByte;
    private int toByte;

    public HttpPart(URL url, int fromByte, int toByte, int totalLength) {
        this.url = url;
        this.fromByte = fromByte;
        this.toByte = toByte;
        this.totalLength = totalLength;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    public int getFromByte() {
        return fromByte;
    }

    public void setFromByte(int fromByte) {
        this.fromByte = fromByte;
    }

    public int getToByte() {
        return toByte;
    }

    public void setToByte(int toByte) {
        this.toByte = toByte;
    }

}
