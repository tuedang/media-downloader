package com.demo.parser.zing;

import java.util.List;

import org.testng.annotations.Test;

import com.demo.music.sdo.Track;

public class ZingPlaylistParserTest {
    private ZingPlaylistParser p = new ZingPlaylistParser();

    @Test
    public void parseFromPlaylist() throws Exception {
        String url="http://mp3.zing.vn/xml/album-xml/ZHJnTLGaVxLpBLdtLDctvnLH";
        List<Track> tracks = p.parseFromPlaylist(url);
        for (Track track : tracks) {
            System.out.println(track);
        }
    }

}
