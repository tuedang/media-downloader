package com.demo.music.downloader;

import com.demo.parser.NhacvuiParser;
import com.demo.parser.common.StringHtmlUtils;
import com.demo.music.downloader.TargetOutputStreamContext.TargetType;
import com.demo.music.sdo.Album;
import com.demo.parser.api.MusicParser;
import com.demo.parser.NctParser;
import com.demo.parser.ZingParser;

import java.io.File;
import java.net.URL;
import java.util.concurrent.Callable;

public class MusicDownloadBrokerHandler implements Callable<String> {
    private String url;
    private String dest;
    private boolean discographyType;
    private DownloadCallback downloadCallback;
    private Status status = new Status();

    public MusicDownloadBrokerHandler(String url, String dest) {
        this.url= url;
        this.dest=dest;
        this.downloadCallback = new DownloadCallback() {
            public void updateStatus(Status status) {
                // Do nothing
            }
        };
    }

    public MusicDownloadBrokerHandler(String url, String dest, boolean discography, DownloadCallback downloadCallback) {
        this.url = url;
        this.dest = dest;
        this.discographyType = discography;
        this.downloadCallback = downloadCallback;
    }

    @Override
    public String call() throws Exception {
        status.setStatusType(StatusType.START);
        downloadCallback.updateStatus(status);

        MusicParser musicParser;
        if (url.indexOf("nhaccuatui") != -1) {
            musicParser = new NctParser();
        } else if (url.indexOf("mp3.zing.vn") != -1) {
            musicParser = new ZingParser();
        } else {
            musicParser = new NhacvuiParser();
        }

        status.setStatusType(StatusType.PARSING);
        downloadCallback.updateStatus(status);

        if (discographyType) {
            throw new RuntimeException("Not supported yet");
        } else {
            Album album = musicParser.getAlbum(new URL(url));
            if (album == null || album.getTracks().isEmpty()) {
                return "FAILED";
            }

            String destFolder = new File(dest, StringHtmlUtils.trimCommonFileName(album.getName())).getAbsolutePath();
            AlbumDownloader albumDownloader = new AlbumDownloader(album, new TargetOutputStreamContext(destFolder));
            albumDownloader.downloadAlbum(downloadCallback);
        }

        return "FINISH";
    }

}
