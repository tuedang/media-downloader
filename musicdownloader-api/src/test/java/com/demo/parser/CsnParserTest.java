package com.demo.parser;

import com.demo.music.sdo.Album;
import com.demo.parser.common.StringHtmlUtils;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class CsnParserTest {

    @Test
    public void parseLink() throws IOException {
        String url = "http://chiasenhac.com/nghe-album/the-portal-part-1~medwyn-goodall~1632548.html";

        Album album = new CsnParser().getAlbum(new URL(url));
        assertEquals("Medwyn Goodall", album.getArtist());
        assertEquals("The Portal (Part 1)", album.getName());
        assertEquals(album.getPlaylistLink(), "");
        assertEquals("http://data.chiasenhac.com/data/cover/54/53577.jpg", album.getImageLink());

        assertEquals(8, album.getTracks().size());

        //2
        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("Orbit"))));
        assertThat(album.getTracks(), hasItems(hasProperty("creator", equalTo("Medwyn Goodall"))));
        assertThat(album.getTracks(), hasItems(hasProperty("location", containsString("http://data.chiasenhac.com/downloads/1633/0/1632548-85848bc8"))));

    }

}
