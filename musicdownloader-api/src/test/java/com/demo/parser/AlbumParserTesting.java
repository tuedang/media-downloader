package com.demo.parser;

import com.demo.music.sdo.Album;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.*;

public class AlbumParserTesting {
    @Test
    public void chiasenhacParser() throws IOException {
        String url = "http://chiasenhac.vn/nghe-album/the-portal-part-1~medwyn-goodall~ts303dm7qnwm8t.html";

        Album album = new CsnParser().getAlbum(new URL(url));
        assertEquals("Medwyn Goodall", album.getArtist());
        assertEquals("The Portal", album.getName());
        assertEquals(album.getPlaylistLink(), "");
        assertEquals("http://data.chiasenhac.com/data/cover/54/53577.jpg", album.getImageLink());

        assertEquals(8, album.getTracks().size());

        //2
        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("Orbit"))));
        assertThat(album.getTracks(), hasItems(hasProperty("creator", equalTo("Medwyn Goodall"))));
        assertThat(album.getTracks(), hasItems(hasProperty("location", containsString("http://data2.chiasenhac.com/downloads/1633/"))));
        assertThat(album.getTracks(), hasItems(hasProperty("location", containsString("1632548-85848bc8"))));

    }

    @Test
    public void nctParser() throws IOException {
        NctParser nctParser = new NctParser();
        String url = "http://www.nhaccuatui.com/playlist/tuyen-tap-nhung-ca-khuc-rock-quoc-te-hay-adam-lambert.xQBpvCYhDpjq.html";

        Album album = nctParser.getAlbum(new URL(url));
        assertEquals("Adam Lambert", album.getArtist());
        assertEquals("Tuyển Tập Những Ca Khúc Rock Quốc Tế Hay", album.getName());
        assertEquals("http://www.nhaccuatui.com/flash/xml?html5=true&key2=30c3ec16f202334185ba0a107fe2f5ad", album.getPlaylistLink());
        assertNull(album.getImageLink());

        assertEquals(40, album.getTracks().size());

        //0
        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("The Unforgiven Ii"))));
        assertThat(album.getTracks(), hasItems(hasProperty("creator", equalTo("Metallica"))));
        assertThat(album.getTracks(), hasItems(hasProperty("location", containsString("Unv_Audio57/TheUnforgivenIi-Metallica"))));

        //39
        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("The Misery"))));
        assertThat(album.getTracks(), hasItems(hasProperty("creator", equalTo("Sonata Arctica"))));
        assertThat(album.getTracks(), hasItems(hasProperty("location", containsString("NhacCuaTui010/TheMisery-SonataArctica"))));
    }

    @Test
    public void nhacvuiParser() throws IOException {
        String url = "http://nhac.vui.vn/album-anh-cu-di-di-single-hari-won-a55076p30510.html";

        Album album = new NhacvuiParser().getAlbum(new URL(url));
        assertEquals("Hari Won", album.getArtist());
        assertEquals("Anh Cứ Đi Đi (Single)", album.getName());
        assertEquals("", album.getPlaylistLink());
        assertEquals("http://static-img.nhac.vui.vn/imageupload/upload2016/2016-2/album/2016-06-06/1465220100_sdfasdf.jpg", album.getImageLink());

        assertEquals(4, album.getTracks().size());

        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("Anh Cứ Đi Đi"))));
        assertThat(album.getTracks(), hasItems(hasProperty("creator", equalTo("Hari Won"))));
        assertThat(album.getTracks(), hasItems(hasProperty("location", equalTo("http://nhac.vui.vn/download.php?id=547586&from=album"))));
    }

    @Test
    public void zingParser() throws IOException {
        String url = "http://mp3.zing.vn/album/Tim-Lai-Bau-Troi-Tuan-Hung/ZWZ9E89F.html";

        Album album = new ZingParser().getAlbum(new URL(url));
        assertEquals("Tuấn Hưng", album.getArtist());
        assertEquals("Tìm Lại Bầu Trời", album.getName());
        assertEquals("", album.getPlaylistLink());
        assertEquals("http://zmp3-photo.d.za.zdn.vn/thumb/165_165/covers/f/1/f1bbd4f8e7b70635be3cc1d85b21f22d_1479365818.jpg", album.getImageLink());

        assertEquals(10, album.getTracks().size());

        //0
        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("Tìm Lại Bầu Trời"))));
        assertThat(album.getTracks(), hasItems(hasProperty("creator", equalTo("Tuấn Hưng"))));
        assertThat(album.getTracks(), hasItems(hasProperty("location", containsString("http://zmp3-mp3-s1.r.za.zdn.vn"))));

        //9
        assertThat(album.getTracks(), hasItems(hasProperty("title", equalTo("Anh Sẽ Vui ... Nếu (Instrumental)"))));
    }
}
