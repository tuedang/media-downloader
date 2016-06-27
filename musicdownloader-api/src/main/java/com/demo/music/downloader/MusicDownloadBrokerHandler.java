package com.demo.music.downloader;

import com.demo.music.sdo.Album;
import com.demo.parser.api.MusicParser;
import com.demo.parser.api.PageParserRegistry;
import com.demo.parser.common.StringHtmlUtils;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.concurrent.Callable;

public class MusicDownloadBrokerHandler implements Callable<String> {
    private String url;
    private String dest;
    private boolean discographyType;
    private DownloadCallback downloadCallback;
    private Status status = new Status();
    private PageParserRegistry pageParserRegistry = new PageParserRegistry();

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

        Optional<MusicParser> musicParserLookup = pageParserRegistry.lookup(url);
        if (!musicParserLookup.isPresent()) {
            throw new IllegalArgumentException(String.format("Download from site is not supported yet, link=%s", url));
        }
        MusicParser musicParser = musicParserLookup.get();

        status.setStatusType(StatusType.PARSING);
        downloadCallback.updateStatus(status);

        Album album = musicParser.getAlbum(new URL(url));
        if (album == null || album.getTracks().isEmpty()) {
            status.setStatusType(StatusType.ERROR);
            downloadCallback.updateStatus(status);
            return StatusType.ERROR.name();
        }

        String destFolder = new File(dest, StringHtmlUtils.trimCommonFileName(album.getName())).getAbsolutePath();
        AlbumDownloader albumDownloader = new AlbumDownloader(album, new TargetOutputStreamContext(destFolder));
        albumDownloader.downloadAlbum(downloadCallback);

        return StatusType.FINISH.name();
    }

}
