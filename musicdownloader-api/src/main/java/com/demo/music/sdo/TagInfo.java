package com.demo.music.sdo;


public class TagInfo {
    private Integer trackNumber;
    private Integer totalTrack;
    private String artist;
    private String albumTitle;
    private String songTitle;

    public TagInfo() {
    }

    public TagInfo(Integer trackNumber, String artist, String albumTitle, String songtitle, int totalTrack) {
        this.trackNumber = trackNumber;
        this.artist = artist;
        this.albumTitle = albumTitle;
        this.songTitle = songtitle;
        this.totalTrack = totalTrack;
    }
    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public void setSongTitle(String songTitle) {
        this.songTitle = songTitle;
    }

    public Integer getTotalTrack() {
        return totalTrack;
    }

    public void setTotalTrack(Integer totalTrack) {
        this.totalTrack = totalTrack;
    }

    @Override
    public String toString() {
        return String.format("track:%s, artist:%s, title:%s, songtitle:%s", trackNumber, artist, albumTitle, songTitle);
    }


}
