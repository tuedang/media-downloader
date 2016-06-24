package com.demo.parser;

import com.demo.music.sdo.Album;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasProperty;

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

        //0
        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("The Unforgiven II"))));
        assertThat(album.getTracks(), hasItems(hasProperty("creator", equalTo("Metallica"))));
        assertThat(album.getTracks(), hasItems(hasProperty("location", containsString("Unv_Audio13/TheUnforgivenIi-Metallica"))));

        //39
        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("The Misery"))));
        assertThat(album.getTracks(), hasItems(hasProperty("creator", equalTo("Sonata Arctica"))));
        assertThat(album.getTracks(), hasItems(hasProperty("location", containsString("NhacCuaTui010/TheMisery-SonataArctica"))));
    }

}
