package com.demo.parser.nct;

import java.util.ArrayList;
import java.util.List;

import com.demo.music.sdo.Album;
import com.demo.music.sdo.Discography;
import com.demo.music.sdo.Track;
import com.demo.parser.common.DiscographyHelper;
import com.demo.parser.common.MusicParser;

public class NctParser implements MusicParser{

    private NctPlaylistParser playlistParser = new NctPlaylistParser();

    public Album getAlbum(String url) {
        System.out.println("Discovering NCT website at "+url);
        try {
            NctHtmlPageParser nctHtmlPageParser = new NctHtmlPageParser(url);
            String pageLink = nctHtmlPageParser.parsePlaylistLink();
            if(pageLink==null) {
                System.out.println("Cannot find the song(s)");
                return null;
            }

            List<Track> tracks = playlistParser.parseFromPlaylist(pageLink);
            if(tracks.size()>0) {
                System.out.println("Playlist found.....");
            }
            String albumLink = nctHtmlPageParser.parseAlbumLink();
            String albumName = nctHtmlPageParser.parseAlbumName();
            String artist = nctHtmlPageParser.parseArtist();
            return new Album(albumName, url, artist, albumLink, tracks);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
