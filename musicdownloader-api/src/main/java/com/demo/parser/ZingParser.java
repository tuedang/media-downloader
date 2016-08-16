package com.demo.parser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Track;
import com.demo.parser.common.HtmlPageContent;
import com.demo.parser.api.MusicParser;

public class ZingParser implements MusicParser {

    @Override
    public String getDomain() {
        return "http://mp3.zing.vn/";
    }

    @Override
    public Album getAlbum(URL url) throws IOException{
        HtmlPageContent htmlPageContent = HtmlPageContent.fromURL(url, HtmlPageContent.ContentType.HTML);

        AtomicInteger trackIdInteger = new AtomicInteger(0);
        List<Track> tracks = htmlPageContent.getJsoupDocument()
                .select(".playlist li")
                .stream()
                .map(e -> new Track(trackIdInteger.incrementAndGet(),
                        e.select(".item-song h3 a").text(),
                        e.select(".item-song h4 a").text(),
                        decorateDownloadlink(e.select(".tool-song div.direct a").attr("href"))))
                .collect(Collectors.toList());

        String albumLink = htmlPageContent.getJsoupDocument().select(".info-top-play img.pthumb").attr("src");
        String albumName = htmlPageContent.getJsoupDocument().select(".info-top-play h1.txt-primary").text();
        String artist = htmlPageContent.getJsoupDocument().select(".info-top-play .info-artist a").text();
        return new Album(albumName, url.toString(), artist, albumLink, tracks, "");
    }

    private String decorateDownloadlink(String refLink) {
        return refLink;
    }

}
