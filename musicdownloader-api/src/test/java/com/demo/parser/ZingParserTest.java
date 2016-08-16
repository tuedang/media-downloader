package com.demo.parser;

import com.demo.music.sdo.Album;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.hasProperty;

public class ZingParserTest {

    @Test
    public void parseLink() throws IOException {
        String url = "http://mp3.zing.vn/album/Tim-Lai-Bau-Troi-Tuan-Hung/ZWZ9E89F.html";

        Album album = new ZingParser().getAlbum(new URL(url));
        assertEquals("Tuấn Hưng", album.getArtist());
        assertEquals("Tìm Lại Bầu Trời", album.getName());
        assertEquals("", album.getPlaylistLink());
        assertEquals("http://image.mp3.zdn.vn/thumb/165_165/covers/8/0/80839c0573a283bcf4a5fc9adaa7655b_1326512899.jpg", album.getImageLink());

        assertEquals(10, album.getTracks().size());

        //0
        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("Tìm Lại Bầu Trời"))));
        assertThat(album.getTracks(), hasItems(hasProperty("creator", equalTo("Tuấn Hưng"))));
//        assertThat(album.getTracks(), hasItems(hasProperty("location", containsString("http://mp3.zing.vn/xml/load-song/"))));

        //9
        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("Anh Sẽ Vui ... Nếu (Instrumental)"))));
    }
}
