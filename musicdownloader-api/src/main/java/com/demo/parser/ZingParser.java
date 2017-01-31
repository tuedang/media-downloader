package com.demo.parser;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Track;
import com.demo.parser.api.MusicParser;
import com.demo.parser.common.HtmlPageContent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;

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
    public Album getAlbum(URL url) throws IOException {
        HtmlPageContent htmlPageContent = HtmlPageContent.fromURL(url, HtmlPageContent.ContentType.HTML);

        AtomicInteger trackIdInteger = new AtomicInteger(0);

        String jsonZingLink = htmlPageContent.getJsoupDocument().select("#html5player").attr("data-xml");
        String jsonZingContent = Jsoup.connect(jsonZingLink).ignoreContentType(true).execute().body();

        JSONArray dataArr = null;
        try {
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(jsonZingContent);
            dataArr = (JSONArray) jsonObject.get("data");
        } catch (ParseException e) {
            throw new IOException("Cannot parse json data", e);
        }

        List<Track> tracks = (List) dataArr
                .stream()
                .map(jsonData -> {
                    JSONObject jsonObj = (JSONObject) jsonData;
                    return new Track(trackIdInteger.incrementAndGet(),
                            jsonObj.get("name").toString(),
                            jsonObj.get("artist").toString(),
                            ((JSONArray) jsonObj.get("source_list")).stream().findFirst().get().toString());
                })
                .collect(Collectors.toList());

        String albumLink = htmlPageContent.getJsoupDocument().select(".info-top-play img.pthumb").attr("src");
        String albumName = htmlPageContent.getJsoupDocument().select(".info-top-play h1.txt-primary").first().childNodes().get(0).toString();
        String artist = htmlPageContent.getJsoupDocument().select(".info-top-play .info-artist a").text();
        return new Album(albumName, url.toString(), artist, albumLink, tracks, "");
    }

}
