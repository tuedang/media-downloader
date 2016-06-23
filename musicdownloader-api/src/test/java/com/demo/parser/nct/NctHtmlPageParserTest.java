package com.demo.parser.nct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class NctHtmlPageParserTest {

    private String url="http://www.nhaccuatui.com/playlist/tuyen-tap-ca-khuc-hay-nhat-cua-boyband-vol-1-va.m1k8VVeWinCn.html";
    private NctHtmlPageParser nctHtmlParser = new NctHtmlPageParser(url);

    @Test
    public void parsePlaylistLink() throws Exception{
        String playlistLink = nctHtmlParser.parsePlaylistLink();
        System.out.println(playlistLink);
    }

    @Test
    public void parseAlbumLink() throws Exception{
        String playlistLink = nctHtmlParser.parseAlbumLink();
        System.out.println(playlistLink);
    }

    @Test
    public void parseAlbumName() throws Exception{
        String playlistLink = nctHtmlParser.parseAlbumName();
        System.out.println(playlistLink);
    }


    @Test
    public void parseArtist() throws Exception{
        String playlistLink = nctHtmlParser.parseArtist();
        System.out.println(playlistLink);
    }

}
