package api;

public class Image {
    private int id;
    private String title;
    private String location;

    public Image(int id, String title, String location) {
        this.id = id;
        this.title = title;
        this.location = location;
    }

    public Image(int id, String location) {
        this.id = id;
        this.location = location;
        this.title = "p-" + id + ".jpg";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
