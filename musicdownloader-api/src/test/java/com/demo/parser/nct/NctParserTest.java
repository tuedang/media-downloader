package com.demo.parser.nct;

import com.demo.music.sdo.Album;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class NctParserTest {
    @Test
    public void parseLink() {
        NctParser nctParser = new NctParser();
        String url = "http://www.nhaccuatui.com/playlist/tuyen-tap-nhung-ca-khuc-rock-quoc-te-hay-adam-lambert.xQBpvCYhDpjq.html";
        Album album = nctParser.getAlbum(url);
        assertEquals("Adam Lambert", album.getArtist());
        assertEquals("Tuyển Tập Những Ca Khúc Rock Quốc Tế Hay", album.getName());
        assertEquals("http://www.nhaccuatui.com/flash/xml?key2=30c3ec16f202334185ba0a107fe2f5ad", album.getPlaylistLink());
        assertNull(album.getImageLink());

        assertEquals(40, album.getTracks().size());
        assertEquals("The Unforgiven II", album.getTracks().get(0).getTitle());
        assertEquals("Metallica", album.getTracks().get(0).getCreator());
        assertEquals("http://aredir.nixcdn.com/316c2905b090d1ed0993a28389e30e9d/576bb099/Unv_Audio13/TheUnforgivenIi-Metallica-2706602.mp3",
                album.getTracks().get(0).getLocation());

        assertEquals("The Misery", album.getTracks().get(39).getTitle());
        assertEquals("Sonata Arctica", album.getTracks().get(39).getCreator());
        assertEquals("http://aredir.nixcdn.com/3f38c3dc9c9c67c4c1f16e9f1f4dc8bd/576bb099/NhacCuaTui010/TheMisery-SonataArctica_36gt.mp3",
                album.getTracks().get(39).getLocation());
    }

}
