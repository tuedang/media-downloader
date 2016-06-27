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
import com.demo.parser.common.StringHtmlUtils;

public class ZingParser implements MusicParser {

    @Override
    public String getDomain() {
        return "http://mp3.zing.vn/";
    }

    @Override
    public Album getAlbum(URL url) throws IOException{
        HtmlPageContent htmlPageContent = HtmlPageContent.fromURL(url, HtmlPageContent.ContentType.HTML);
        System.out.println("Discovering ZING website at " + url);

        String pageLink = htmlPageContent.getJsoupDocument()
                .select("[data-xml^='http://mp3.zing.vn/xml/album-xml']")
                .attr("data-xml");
        if (pageLink == null) {
            System.out.println("Cannot find the song(s)");
            return null;
        }

        AtomicInteger trackIdInteger = new AtomicInteger(0);
        List<Track> tracks = HtmlPageContent.fromURL(new URL(pageLink), HtmlPageContent.ContentType.XML).getJsoupDocument()
                .select("item")
                .stream()
                .map(e -> new Track(trackIdInteger.incrementAndGet(),
                        e.select("title").text(),
                        e.select("performer").text(),
                        e.select("source").text()))
                .collect(Collectors.toList());
        if (tracks.size() > 0) {
            System.out.println("Playlist found.....");
        }

        String albumLink = htmlPageContent.getJsoupDocument().select("img.pthumb").attr("src");
        String albumName = StringHtmlUtils.removeSpecialCharacter(htmlPageContent.getJsoupDocument().select("h1.txt-primary").text());
        String artist = htmlPageContent.getJsoupDocument().select("h2.txt-primary").text();
        return new Album(albumName, url.toString(), artist, albumLink, tracks, pageLink);
    }

}
