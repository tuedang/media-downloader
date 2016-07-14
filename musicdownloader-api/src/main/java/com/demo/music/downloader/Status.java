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


    //builder
    public Status statusType(StatusType statusType) {
        setStatusType(statusType);
        return this;
    }
    public Status comment(String comment) {
        setComment(comment);
        return this;
    }
    public Status track(int track) {
        setCurrentTrack(track);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status status = (Status) o;

        if (currentTrack != status.currentTrack) return false;
        return statusType == status.statusType;

    }

    @Override
    public int hashCode() {
        int result = statusType != null ? statusType.hashCode() : 0;
        result = 31 * result + currentTrack;
        return result;
    }

    @Override
    public String toString() {
        return "Status{" +
                "statusType=" + statusType +
                ", comment='" + comment + '\'' +
                ", totalTrack=" + totalTrack +
                ", currentTrack=" + currentTrack +
                '}';
    }

    @Override
    public Status clone() {
        Status status = new Status();
        status.setCurrentTrack(getCurrentTrack());
        status.setStatusType(getStatusType());
        status.setComment(getComment());
        status.setTotalTrack(getTotalTrack());
        return status;
    }
}
