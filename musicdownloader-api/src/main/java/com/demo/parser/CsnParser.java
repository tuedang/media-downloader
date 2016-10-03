package com.demo.parser;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Track;
import com.demo.parser.api.MusicParser;
import com.demo.parser.common.HtmlPageContent;
import com.demo.parser.common.StringHtmlUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CsnParser implements MusicParser {
    @Override
    public String getDomain() {
        return "http://chiasenhac.vn";
    }

    @Override
    public Album getAlbum(URL url) throws IOException {
        HtmlPageContent htmlPageContent = HtmlPageContent.fromURL(url, HtmlPageContent.ContentType.HTML);

        AtomicInteger trackIdInteger = new AtomicInteger(0);
        List<Track> tracks = htmlPageContent.getJsoupDocument()
                .select("#playlist table tr[title]")
                .stream()
                .map(e -> new Track(trackIdInteger.incrementAndGet(),
                        e.select(".musictitle").text(),
                        e.select("td span.gen").text().split("-")[1].trim(),
                        e.select("span.gen>a").first().attr("href")))
                .map(track -> {
                    try {
                        Document document = HtmlPageContent.fromURL(new URL(track.getLocation()), HtmlPageContent.ContentType.HTML).getJsoupDocument();
                        String rawParseContent = document.select("#downloadlink script:nth-child(2)").html();
                        Document jsoupJSContent = Jsoup.parse(rawParseContent);

                        String link320 = jsoupJSContent.select("a[href*='/320/']").attr("href");
                        if(jsoupJSContent.select("a span").text().contains("Lossless")) {
                            String losslessLink = link320
                                    .replace("[MP3 320kbps]", "[FLAC Lossless]")
                                    .replace(".mp3", ".flac")
                                    .replace("/320/", "/flac/");
                            track.setLocation(losslessLink);
                        } else {
                            track.setLocation(link320);
                        }
                        return track;
                    } catch (MalformedURLException e) {
                        return track;
                    }
                })
                .collect(Collectors.toList());

        String albumLink = htmlPageContent.getJsoupDocument().select("img[src*='/cover/']").attr("src");
        String albumName = htmlPageContent.getJsoupDocument().select("#fulllyric .genmed a[href*='mode=album']").text();
        String artist = htmlPageContent.getJsoupDocument().select("#fulllyric .genmed a[href*='mode=artist']").text();
        return new Album(albumName, url.toString(), artist, albumLink, tracks, "");
    }

}
