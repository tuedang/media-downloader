package com.demo.parser;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Track;
import com.demo.parser.common.HtmlPageContent;
import com.demo.parser.api.MusicParser;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class NctParser implements MusicParser {

    @Override
    public String getDomain() {
        return "http://www.nhaccuatui.com/";
    }

    public Album getAlbum(URL url) throws IOException {
        HtmlPageContent htmlPageContent = HtmlPageContent.fromURL(url, HtmlPageContent.ContentType.HTML);
        System.out.println("Discovering NCT website at " + url);
        String pageLink = htmlPageContent.searchText("div.playing_absolute", "player.peConfig.xmlURL", 4, "\";");
        if (pageLink == null) {
            System.out.println("Cannot find the song(s)");
            return null;
        }

        AtomicInteger trackIdInteger = new AtomicInteger(0);
        List<Track> tracks = HtmlPageContent.fromURL(new URL(pageLink), HtmlPageContent.ContentType.XML).getJsoupDocument()
                .select("track")
                .parallelStream()
                .map(e -> new Track(trackIdInteger.incrementAndGet(),
                        e.select("title").text(),
                        e.select("creator").text(),
                        e.select("location").text()))
                .collect(Collectors.toList());
        if (tracks.size() > 0) {
            System.out.println("Playlist found.....");
        }

        String albumLink = null;
        String albumName = htmlPageContent.cssSelector("div.name_title h1[itemprop='name']");
        String artist = htmlPageContent.cssSelector("div.name_title .name-singer");
        return new Album(albumName, url.toString(), artist, albumLink, tracks, pageLink);
    }

}
