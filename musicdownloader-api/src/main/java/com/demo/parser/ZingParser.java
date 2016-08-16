package com.demo.parser;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Track;
import com.demo.parser.api.MusicParser;
import com.demo.parser.common.HtmlPageContent;
import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
                        e.select(".tool-song div.direct a").attr("href")))
                .map(track -> {
                    try {
                        Document document = HtmlPageContent.fromURL(new URL(track.getLocation()), HtmlPageContent.ContentType.HTML).getJsoupDocument();
                        String jsonLink = document.select("#html5player").attr("data-xml");
                        String jsonContent = IOUtils.toString(new URL(jsonLink), "UTF-8");

                        JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonContent);
                        JSONObject data = (JSONObject) ((JSONArray) jsonObject.get("data")).get(0);
                        track.setLocation(data.get("source_base") + (String) ((JSONArray)data.get("source_list")).get(1));
                        return track;
                    } catch (IOException | ParseException e) {
                        return track;
                    }
                })
                .collect(Collectors.toList());

        String albumLink = htmlPageContent.getJsoupDocument().select(".info-top-play img.pthumb").attr("src");
        String albumName = htmlPageContent.getJsoupDocument().select(".info-top-play h1.txt-primary").text();
        String artist = htmlPageContent.getJsoupDocument().select(".info-top-play .info-artist a").text();
        return new Album(albumName, url.toString(), artist, albumLink, tracks, "");
    }

}
