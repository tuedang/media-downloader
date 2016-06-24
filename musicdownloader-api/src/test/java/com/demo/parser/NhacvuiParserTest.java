package com.demo.parser;

import com.demo.music.sdo.Album;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class NhacvuiParserTest {

    @Test
    public void parseLink() throws IOException {
        String url = "http://nhac.vui.vn/album-anh-cu-di-di-single-hari-won-a55076p30510.html";

        Album album = new NhacvuiParser().getAlbum(new URL(url));
        assertEquals("Hari Won", album.getArtist());
        assertEquals("Anh Cứ Đi Đi (Single)", album.getName());
        assertEquals("", album.getPlaylistLink());
        assertEquals("http://static-img.nhac.vui.vn/imageupload/upload2016/2016-2/album/2016-06-06/1465220100_sdfasdf.jpg", album.getImageLink());

        assertEquals(2, album.getTracks().size());

        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("Anh Cứ Đi Đi"))));
        assertThat(album.getTracks(), hasItems(hasProperty("creator", equalTo("Hari Won"))));
        assertThat(album.getTracks(), hasItems(hasProperty("location", equalTo("http://nhac.vui.vn/download.php?id=547586&from=album"))));
    }
}
