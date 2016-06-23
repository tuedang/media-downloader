package com.demo.parser.zing;

import java.util.ArrayList;
import java.util.List;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Discography;
import com.demo.music.sdo.Track;
import com.demo.parser.common.DiscographyHelper;
import com.demo.parser.common.MusicParser;

public class ZingParser implements MusicParser{
    private ZingPlaylistParser playlistParser = new ZingPlaylistParser();

    @Override
    public Album getAlbum(String url) {
        System.out.println("Discovering Zing website at "+url);
        try {
            ZingHtmlPageParser zingHtmlPageParser = new ZingHtmlPageParser(url);
            String pageLink = zingHtmlPageParser.parsePlaylistLink();
            if(pageLink==null) {
                System.out.println("Cannot find the song(s)");
                return null;
            }

            List<Track> tracks = playlistParser.parseFromPlaylist(pageLink);
            if(tracks.size()>0) {
                System.out.println("Playlist found.....");
            }
            String albumLink = zingHtmlPageParser.parseAlbumLink();
            String albumName = zingHtmlPageParser.parseAlbumName();
            String artist = zingHtmlPageParser.parseArtist();
            return new Album(albumName, url, artist, albumLink, tracks);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
