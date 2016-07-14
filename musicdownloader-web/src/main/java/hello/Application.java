package hello;

import com.media.browser.AppLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        AppLauncher.load();
        new SpringApplicationBuilder(Application.class).headless(false).run(args);
    }

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> {
//            AppLauncher.load();
        };
    }
}
