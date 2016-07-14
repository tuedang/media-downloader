package hello;

import com.demo.music.downloader.DownloadCallback;
import com.demo.music.downloader.MusicDownloadManager;
import com.demo.music.downloader.Status;
import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@SpringUI
@Theme("valo")
public class VaadinUI extends UI {

    private final CustomerRepository repo;

    private final Grid grid;

    private final TextField downloadUrl;

    private final Button downloadButton;

    @Autowired
    public VaadinUI(CustomerRepository repo) {
        this.repo = repo;
        this.grid = new Grid();
        this.downloadUrl = new TextField();
        this.downloadButton = new Button("Download", FontAwesome.DOWNLOAD);
    }

    @Override
    protected void init(VaadinRequest request) {
        setPollInterval(1000);
        addPollListener(pollEvent -> listProgress());
        render();
    }

    public void render() {
        // build layout
        HorizontalLayout actions = new HorizontalLayout(downloadUrl, downloadButton);
        VerticalLayout mainLayout = new VerticalLayout(actions, grid);
        setContent(mainLayout);

        // Configure layouts and components
        actions.setSpacing(true);
        actions.setWidth("100%");
        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        mainLayout.setWidth("100%");

        grid.setWidth("100%");
        grid.setColumns("currentTrack", "comment", "statusType");

        downloadUrl.setInputPrompt("Grap the link from browser and paste here!");
        downloadUrl.setValue("http://www.nhaccuatui.com/playlist/anh-cu-di-di-single-hari-won.LSzTSgccoNrA.html");
        downloadUrl.setWidth("100%");

        downloadButton.addClickListener(e -> {
            String dest = "/Data/NCT/";
            MusicDownloadManager musicDownloadManager = MusicDownloadManager.getInstance(new DownloadCallback() {
                @Override
                public void updateStatus(Status status) {
                    listDownloadAudioTrack(status);
                }
            });
            musicDownloadManager.download(downloadUrl.getValue(), dest);
        });
    }

//    private Set<Status> statuses = ConcurrentHashMap.newKeySet();
    List<Status> statuses = new CopyOnWriteArrayList();
    private void listDownloadAudioTrack(Status status) {
        statuses.add(status.clone());
    }
    private void listProgress() {
        grid.setContainerDataSource(new BeanItemContainer(Status.class, statuses));
    }

}
