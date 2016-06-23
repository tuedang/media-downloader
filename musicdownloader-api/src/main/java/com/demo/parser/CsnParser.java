package com.demo.parser;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Track;
import com.demo.parser.api.MusicParser;
import com.demo.parser.common.HtmlPageContent;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CsnParser implements MusicParser {
    @Override
    public Album getAlbum(URL url) throws IOException{
        HtmlPageContent htmlPageContent = HtmlPageContent.fromURL(url, HtmlPageContent.ContentType.HTML);
        System.out.println("Discovering Chiasenhac website at " + url);

        AtomicInteger trackIdInteger = new AtomicInteger(0);
        List<Track> tracks = htmlPageContent.getJsoupDocument()
                .select("#playlist table tr[title]")
                .stream()
                .map(e -> new Track(trackIdInteger.incrementAndGet(),
                        e.select("title").text(),
                        e.select("performer").text(),
                        e.select("source").text()))
                .collect(Collectors.toList());
        if (tracks.size() > 0) {
            System.out.println("Playlist found.....");
        }

        String albumLink = htmlPageContent.getJsoupDocument().select("#fulllyric img").first().attr("src");
        String albumName = htmlPageContent.getJsoupDocument().select("span.maintitle").text();
        String artist = htmlPageContent.getJsoupDocument().select("#fulllyric [href^='http://search.chiasenhac.com/search.php?mode=artist&']").text();
        return new Album(albumName, url.getRef(), artist, albumLink, tracks, "");
    }

}
