package com.demo.parser.nct;

import org.testng.annotations.Test;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Discography;
import com.demo.music.sdo.Track;

public class NctParserTest {
    @Test
    public void parseLink() {
        NctParser nctParser = new NctParser();
        String url = "http://www.nhaccuatui.com/playlist/boyband-va-nhung-ca-khuc-hay-nhat-backstreet-boys-ft-westlife-ft-blue.m1k8VVeWinCn.html";
        Album album = nctParser.getAlbum(url);
        System.out.println(album.getArtist());
        System.out.println(album.getName());
        System.out.println(album.getImageLink());
        for (Track track : album.getTracks()) {
            System.out.println(track);
        }
    }

    @Test
    public void getDiscography() {
        String url="http://www.nhaccuatui.com/nghe?L=GaLiPWY0pzO3";
        NctParser nctParser = new NctParser();
        Discography discography= nctParser.getDiscography(url);
        System.out.println(discography);
    }

}
