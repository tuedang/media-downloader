package com.demo.music.sdo;

public class Track {
    private int id;
    private String title;
    private String creator;
    private String location;

    public Track() {
    }

    public Track(int id, String title, String creator, String location) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    @Override
    public String toString() {
        return String.format("%s:%s-%s-[%s]", id, title, creator, location);
    }

}
