package com.demo.parser;

import com.demo.music.sdo.Album;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ZingParserTest {

    @Test
    public void parseLink() throws IOException {
        String url = "http://mp3.zing.vn/album/Tim-Lai-Bau-Troi-Tuan-Hung/ZWZ9E89F.html";

        Album album = new ZingParser().getAlbum(new URL(url));
        assertEquals("Tuấn Hưng", album.getArtist());
        assertEquals("Tìm Lại Bầu Trời", album.getName());
        assertThat(album.getPlaylistLink(), CoreMatchers.containsString("http://mp3.zing.vn/xml/album-xml/"));
        assertEquals("http://image.mp3.zdn.vn/thumb/165_165/covers/8/0/80839c0573a283bcf4a5fc9adaa7655b_1326512899.jpg", album.getImageLink());

        assertEquals(10, album.getTracks().size());
        assertEquals("Tìm Lại Bầu Trời", album.getTracks().get(0).getTitle());
        assertEquals("Tuấn Hưng", album.getTracks().get(0).getCreator());
        assertThat(album.getTracks().get(0).getLocation(), CoreMatchers.containsString("http://mp3.zing.vn/xml/load-song/"));

        assertEquals("Anh Sẽ Vui ... Nếu (Instrumental)", album.getTracks().get(9).getTitle());
        assertEquals("Tuấn Hưng", album.getTracks().get(9).getCreator());
        assertThat(album.getTracks().get(9).getLocation(), CoreMatchers.containsString("http://mp3.zing.vn/xml/load-song/"));
    }
}
