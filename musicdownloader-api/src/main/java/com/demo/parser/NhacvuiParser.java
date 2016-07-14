package com.demo.parser;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Track;
import com.demo.parser.api.MusicParser;
import com.demo.parser.common.HtmlPageContent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class NhacvuiParser implements MusicParser {

    @Override
    public String getDomain() {
        return "http://nhac.vui.vn/";
    }

    @Override
    public Album getAlbum(URL url) throws IOException{
        HtmlPageContent htmlPageContent = HtmlPageContent.fromURL(url, HtmlPageContent.ContentType.HTML_JS);

        AtomicInteger trackIdInteger = new AtomicInteger(0);
        List<Track> tracks = htmlPageContent.getJsoupDocument()
                .select("div.playListAlbum .playListAlbum-item")
                .parallelStream()
                .map(e -> new Track(trackIdInteger.incrementAndGet(),
                            e.select(".playListAlbum-item a.plst-baihat").text(),
                            e.select(".playListAlbum-item a.plst-casi").text(),
                            "http://nhac.vui.vn" + e.select(".playListAlbum-item .listNhac-btn .link-download").attr("href")))
                .collect(Collectors.toList());

        String albumLink = htmlPageContent.getJsoupDocument().select(".albumInfo-img img").attr("src");
        String albumName = htmlPageContent.getJsoupDocument().select(".albumInfo-txt .nghenhac-baihat").text().split("-")[0].trim();
        String artist = htmlPageContent.getJsoupDocument().select(".albumInfo-txt .nghenhac-baihat").text().split("-")[1].trim();
        return new Album(albumName, url.toString(), artist, albumLink, tracks, "");
    }

}
