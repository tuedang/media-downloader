package com.demo.parser.nct;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import com.demo.parser.common.HtmlPageContent;
import com.google.common.io.Resources;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.demo.music.sdo.Track;
import com.demo.parser.nct.NctPlaylistParser;

public class PlaylistParserTest {
    @Test
    public void parse() throws ParserConfigurationException, SAXException, IOException {
        String url ="http://www.nhaccuatui.com/flash/xml?key2=2e576dbbc6badc42e0609ada2df014c3";
        List<Track> tracks = new NctPlaylistParser().parseFromPlaylist(url);
        for (Track track : tracks) {
            System.out.println(track);
        }
    }

    @Test
    public void parseJsoup() throws IOException {
        new NctPlaylistParser().parseJsoup(HtmlPageContent.fromURL(Resources.getResource("com/demo/parser/tuyen-tap-nhung-ca-khuc-rock-quoc-te-hay-adam-lambert.playlist.xml"), HtmlPageContent.ContentType.XML));
    }

}
