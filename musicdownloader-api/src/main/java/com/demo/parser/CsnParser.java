package com.demo.parser;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Track;
import com.demo.parser.api.MusicParser;
import com.demo.parser.common.HtmlPageContent;
import com.demo.parser.common.StringHtmlUtils;
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
    public Album getAlbum(URL url) throws IOException {
        HtmlPageContent htmlPageContent = HtmlPageContent.fromURL(url, HtmlPageContent.ContentType.HTML);
        System.out.println("Discovering Chiasenhac website at " + url);

        AtomicInteger trackIdInteger = new AtomicInteger(0);
        List<Track> tracks = htmlPageContent.getJsoupDocument()
                .select("#playlist table tr[title]")
                .stream()
                .map(e -> new Track(trackIdInteger.incrementAndGet(),
                        e.select(".musictitle").text(),
                        e.select("td span.gen").text().split("-")[1].trim(),
                        decorateDownloadlink(e.select("span.gen>a").first().attr("href"))))
                .collect(Collectors.toList());
        if (tracks.size() > 0) {
            System.out.println("Playlist found.....");
        }

        String albumLink = htmlPageContent.getJsoupDocument().select("#fulllyric img").first().attr("src");
        String albumName = htmlPageContent.getJsoupDocument().select("span.maintitle").text();
        String artist = htmlPageContent.getJsoupDocument().select("#fulllyric [href*='mode=artist']").text();
        return new Album(albumName, url.toString(), artist, albumLink, tracks, "");
    }

    private String decorateDownloadlink(String refLink) {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> refLink)
        .thenApply((input) -> {
            try {
                Document document = HtmlPageContent.fromURL(new URL(input), HtmlPageContent.ContentType.HTML).getJsoupDocument();
                return document.select("#downloadlink a[href*='/320/']").attr("href");
            } catch (MalformedURLException e) {
                return "ERROR:" + refLink;
            }
        })
        .thenApply(transformedLink -> transformedLink
                    .replace("[MP3 320kbps]", "[FLAC Lossless]")
                    .replace(".mp3", ".flac")
                    .replace("/320/", "/flac/"))
        .thenApply(input -> StringHtmlUtils.encodeParamUrl(input));

        try {
            return completableFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

}
