package com.demo.parser.zing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.demo.music.sdo.Track;
import com.demo.parser.common.HtmlParser;

public class ZingPlaylistParser {
    public List<Track> parseFromPlaylist(String url) throws ParserConfigurationException, SAXException, IOException {
        int id=0;
        // get the factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // Using factory get an instance of document builder
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document dom = db.parse(url);
        Element docEle = dom.getDocumentElement();

        NodeList nl = docEle.getElementsByTagName("item");
        List<Track> tracks = new ArrayList<Track>();
        if(nl != null && nl.getLength() > 0) {
            for(int i = 0 ; i < nl.getLength();i++) {
                Element el = (Element)nl.item(i);
                Track track = getTrack(++id, el);
                tracks.add(track);
            }
        }
        return tracks;
    }

    private Track getTrack(int id, Element empEl) {
        String title = HtmlParser.getTextValue(empEl,"title");
        String creator = HtmlParser.getTextValue(empEl, "performer");
        String location =HtmlParser.getTextValue(empEl, "source");
        return new Track(id, title, creator, location);
    }


}
