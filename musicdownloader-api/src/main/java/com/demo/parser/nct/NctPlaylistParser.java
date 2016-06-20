package com.demo.parser.nct;

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

public class NctPlaylistParser {
	public List<Track> parseFromPlaylist(String url) throws ParserConfigurationException, SAXException, IOException {
		int id=0;
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		// Using factory get an instance of document builder
		DocumentBuilder db = dbf.newDocumentBuilder();

		Document dom = db.parse(url);
		Element docEle = dom.getDocumentElement();

		NodeList nl = docEle.getElementsByTagName("track");
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
		String title = getTextValue(empEl,"title");
		String creator = getTextValue(empEl, "creator");
		String location =getTextValue(empEl, "location");
		return new Track(id, title, creator, location);
	}
	
	public static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if(nl != null && nl.getLength() > 0) {
			Element el = (Element)nl.item(0);
			textVal = el.getTextContent().trim();
		}

		return textVal;
	}
	
	
}
