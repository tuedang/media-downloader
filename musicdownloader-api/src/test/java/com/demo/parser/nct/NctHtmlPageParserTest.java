package com.demo.parser.nct;

import java.util.List;

import org.testng.annotations.Test;

public class NctHtmlPageParserTest {

    private String url="http://www.nhaccuatui.com/playlist/boyband-va-nhung-ca-khuc-hay-nhat-backstreet-boys-ft-westlife-ft-blue.m1k8VVeWinCn.html";
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

    @Test (enabled=false)
    public void parseDiscographyLink() throws Exception {
        List<String> discographyLinks = nctHtmlParser.parseDiscographyLink();
        System.out.println(discographyLinks.size());
        for (String string : discographyLinks) {
            System.out.println(string);
        }
    }


}
