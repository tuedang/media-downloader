package hello;

import com.demo.music.downloader.MusicDownloadManager;
import com.demo.music.downloader.Status;
import com.demo.music.downloader.Status.StatusType;
import com.demo.music.sdo.MusicProfile;
import com.media.profile.AppProfiler;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringUI
@Theme("valo")
public class MusicDownloaderUI extends UI {

    private final Grid grid;

    private final TextField downloadUrl;

    private final TextField downloadFolder;

    private final Button downloadButton;

    List<Status> statuses = new CopyOnWriteArrayList();

    public MusicDownloaderUI() {
        this.grid = new Grid();
        this.downloadUrl = new TextField();
        this.downloadFolder = new TextField();
        downloadFolder.setInputPrompt("Download folder");
        this.downloadButton = new Button("Download", FontAwesome.DOWNLOAD);
    }

    @Override
    protected void init(VaadinRequest request) {
        render();
    }

    public void render() {
        // build layout
        HorizontalLayout actions = new HorizontalLayout(downloadUrl, downloadButton, downloadFolder);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid);
        setContent(mainLayout);

        // Configure layouts and components
        actions.setSpacing(true);
        actions.setWidth("100%");
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.setWidth("100%");

        grid.setWidth("100%");
        grid.setHeight("100%");
        grid.setColumns("currentTrack", "comment", "statusType");
        grid.getColumn("currentTrack").setMaximumWidth(140);

        downloadUrl.setInputPrompt("Grap the link from browser and paste here!");
        downloadUrl.setWidth("100%");

        MusicProfile musicProfile = AppProfiler.load();
        downloadUrl.setValue(StringUtils.defaultIfBlank(musicProfile.getUrl(), "http://mp3.zing.vn/album/Tim-Lai-Bau-Troi-Tuan-Hung/ZWZ9E89F.html"));
        downloadFolder.setValue(musicProfile.getDestFolder());

        downloadButton.addClickListener(e -> {
            String dest = downloadFolder.getValue();
            AppProfiler.persistProfile(new MusicProfile(downloadUrl.getValue(), dest, false));
            statuses.clear();
            setPollInterval(1000);
            MusicDownloadManager musicDownloadManager = MusicDownloadManager.getInstance(status -> statuses.add(status.clone()));
            musicDownloadManager.download(downloadUrl.getValue(), dest);
        });

        this.addPollListener(pollEvent -> {
            Status lastestStatus = statuses.isEmpty()? null: statuses.get(statuses.size() - 1);
            grid.setContainerDataSource(new BeanItemContainer(Status.class, statuses));
            grid.scrollToEnd();

            if (lastestStatus == null
                    || lastestStatus.getStatusType() == StatusType.FINISH
                    || lastestStatus.getStatusType() == StatusType.ERROR) {
                setPollInterval(-1);
            }
        });
    }
}
