package com.demo.music.downloader;

import com.demo.music.downloader.httpdownloader.SimpleHttpDownloader;
import com.demo.music.sdo.Album;
import com.demo.parser.api.MusicParser;
import com.demo.parser.api.PageParserRegistry;
import com.demo.parser.common.StringHtmlUtils;
import com.demo.music.downloader.Status.StatusType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

public class MusicDownloadBrokerHandler {
    private String url;
    private String dest;
    private boolean discographyType;
    private DownloadCallback downloadCallback;
    private Status status = new Status();
    private PageParserRegistry pageParserRegistry = new PageParserRegistry();
    private HttpDownloader httpDownloader = new SimpleHttpDownloader();

    public MusicDownloadBrokerHandler(String url, String dest, boolean discography, DownloadCallback downloadCallback) {
        this.url = url;
        this.dest = dest;
        this.discographyType = discography;
        this.downloadCallback = downloadCallback;
    }

    public String download() throws IOException {
        downloadCallback.updateStatus(status.track(0).statusType(StatusType.START).comment("Download start"));

        Optional<MusicParser> musicParserLookup = pageParserRegistry.lookup(url);
        if (!musicParserLookup.isPresent()) {
            throw new IOException(String.format("Download from site is not supported yet, link=%s", url));
        }
        MusicParser musicParser = musicParserLookup.get();

        downloadCallback.updateStatus(status.track(0).statusType(StatusType.PARSING).comment("Underlying domain: " + musicParser.getDomain()));

        Album album = musicParser.getAlbum(new URL(url));
        if (album == null || album.getTracks().isEmpty()) {
            downloadCallback.updateStatus(status.track(0).statusType(StatusType.ERROR));
            return StatusType.ERROR.name();
        }

        String destFolder = new File(dest, StringHtmlUtils.trimCommonFileName(album.getName())).getAbsolutePath();
        AlbumDownloader albumDownloader = new AlbumDownloader(album, new TargetOutputStreamContext(destFolder), httpDownloader);
        albumDownloader.downloadAlbum(downloadCallback);

        return StatusType.FINISH.name();
    }

}
