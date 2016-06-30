package hello;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.UIEvents;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Random;

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
        addPollListener(new UIEvents.PollListener() {
            @Override
            public void poll(UIEvents.PollEvent event) {
                if(new Random().nextBoolean()) {
                    listCustomers("b");
                } else {
                    listCustomers("p");
                }
            }
        });
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
        grid.setColumns("id", "firstName", "lastName");

        downloadUrl.setInputPrompt("Grap the link from browser and paste here!");
        downloadUrl.setWidth("100%");

        // Replace listing with filtered content when user changes downloadUrl
        downloadUrl.addTextChangeListener(e -> listCustomers(e.getText()));

        // Initialize listing
        listCustomers(null);
    }
    // tag::listCustomers[]
    private void listCustomers(String text) {
        if (StringUtils.isEmpty(text)) {
            grid.setContainerDataSource(new BeanItemContainer(Customer.class, repo.findAll()));
        } else {
            grid.setContainerDataSource(new BeanItemContainer(Customer.class,
                    repo.findByLastNameStartsWithIgnoreCase(text)));
        }
    }

}
