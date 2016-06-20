package com.demo.music.downloader;

import java.io.File;
import java.util.concurrent.Callable;

import com.demo.common.StringUtils;
import com.demo.music.downloader.TargetOutputStreamContext.TargetType;
import com.demo.music.sdo.Album;
import com.demo.music.sdo.Discography;
import com.demo.parser.common.MusicParser;
import com.demo.parser.nct.NctParser;
import com.demo.parser.zing.ZingParser;

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
        this.url= url;
        this.dest=dest;
        this.discographyType = discography;
        this.downloadCallback=downloadCallback;
    }

    @Override
    public String call() throws Exception {
        status.setStatusType(StatusType.START);
        downloadCallback.updateStatus(status);

        MusicParser musicParser;
        if(url.indexOf("nhaccuatui")!=-1) {
            musicParser = new NctParser();
        } else {
            musicParser = new ZingParser();
        }

        status.setStatusType(StatusType.PARSING);
        downloadCallback.updateStatus(status);

        if(discographyType) {
            Discography discography = musicParser.getDiscography(url);
            File discographyFolder = new File(dest, discography.getName());
            discographyFolder.mkdirs();
            for(Album album: discography.getAlbums()) {
//				AlbumDownloader albumDownloader = new AlbumDownloader(album, discographyFolder);
//				albumDownloader.downloadAlbum(downloadCallback);
            }
        } else {
            Album album = musicParser.getAlbum(url);
            if(album==null|| album.getTracks().isEmpty()) {
                return "FAILED";
            }

            String destFolder = new File(dest, StringUtils.trimCommonFileName(album.getName())).getAbsolutePath();
            AlbumDownloader albumDownloader = new AlbumDownloader(album, new TargetOutputStreamContext(destFolder, TargetType.FILE_SYSTEM));
            albumDownloader.downloadAlbum(downloadCallback);
        }

        return "FINISH";
    }

}
