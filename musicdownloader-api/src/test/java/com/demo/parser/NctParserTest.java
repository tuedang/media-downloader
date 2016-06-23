package com.demo.parser;

import com.demo.music.sdo.Album;
import com.demo.parser.NctParser;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

public class NctParserTest {
    @Test
    public void parseLink() throws IOException {
        NctParser nctParser = new NctParser();
        String url = "http://www.nhaccuatui.com/playlist/tuyen-tap-nhung-ca-khuc-rock-quoc-te-hay-adam-lambert.xQBpvCYhDpjq.html";

        Album album = nctParser.getAlbum(new URL(url));
        assertEquals("Adam Lambert", album.getArtist());
        assertEquals("Tuyển Tập Những Ca Khúc Rock Quốc Tế Hay", album.getName());
        assertEquals("http://www.nhaccuatui.com/flash/xml?html5=true&key2=30c3ec16f202334185ba0a107fe2f5ad", album.getPlaylistLink());
        assertNull(album.getImageLink());

        assertEquals(40, album.getTracks().size());
        assertEquals("The Unforgiven II", album.getTracks().get(0).getTitle());
        assertEquals("Metallica", album.getTracks().get(0).getCreator());
        assertThat(album.getTracks().get(0).getLocation(), CoreMatchers.containsString("Unv_Audio13/TheUnforgivenIi-Metallica"));

        assertEquals("The Misery", album.getTracks().get(39).getTitle());
        assertEquals("Sonata Arctica", album.getTracks().get(39).getCreator());
        assertThat(album.getTracks().get(39).getLocation(), CoreMatchers.containsString("NhacCuaTui010/TheMisery-SonataArctica"));
    }

}
