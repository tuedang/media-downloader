package com.demo.music.downloader;

public class Status {
    public enum StatusType {
        START, PARSING, DOWNLOAD_IMAGE, DOWNLOAD_SOUNDTRACK, FINISH, ERROR
    }

    private StatusType statusType;
    private String comment;
    private int totalTrack;
    private int currentTrack;

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType statusType) {
        this.statusType = statusType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getTotalTrack() {
        return totalTrack;
    }

    public void setTotalTrack(int totalTrack) {
        this.totalTrack = totalTrack;
    }

    public int getCurrentTrack() {
        return currentTrack;
    }

    public void setCurrentTrack(int currentTrack) {
        this.currentTrack = currentTrack;
    }

}
