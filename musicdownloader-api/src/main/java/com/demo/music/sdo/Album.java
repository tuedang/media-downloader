package com.demo.music.sdo;

import java.util.List;

public class Album {
    private Long id;
    private String refLink;
    private String name;
    private String artist;
    private String imageLink;
    private List<Track> tracks;
    private String playlistLink;

    public Album() {
    }

    public Album(String name, String refLink, String artist, String imageLink, List<Track> tracks, String playlistLink) {
        this.name = name;
        this.refLink = refLink;
        this.artist = artist;
        this.imageLink = imageLink;
        this.tracks = tracks;
        this.playlistLink = playlistLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public String getRefLink() {
        return refLink;
    }

    public void setRefLink(String refLink) {
        this.refLink = refLink;
    }

    public String getPlaylistLink() {
        return playlistLink;
    }

    public void setPlaylistLink(String playlistLink) {
        this.playlistLink = playlistLink;
    }
}
