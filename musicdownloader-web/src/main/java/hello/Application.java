package hello;

import com.media.browser.AppLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).headless(false).run(args);
    }

    @Bean
    public CommandLineRunner loadData(CustomerRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new AudioTrackProgress("Jack", "Bauer"));
            repository.save(new AudioTrackProgress("Chloe", "O'Brian"));
            repository.save(new AudioTrackProgress("Kim", "Bauer"));
            repository.save(new AudioTrackProgress("David", "Palmer"));
            repository.save(new AudioTrackProgress("Michelle", "Dessler"));

            // fetch all customers
            log.info("Customers found with findAll():");
            log.info("-------------------------------");
            for (AudioTrackProgress audioTrackProgress : repository.findAll()) {
                log.info(audioTrackProgress.toString());
            }
            log.info("");

            // fetch an individual audioTrackProgress by ID
            AudioTrackProgress audioTrackProgress = repository.findOne(1L);
            log.info("AudioTrackProgress found with findOne(1L):");
            log.info("--------------------------------");
            log.info(audioTrackProgress.toString());
            log.info("");

            // fetch customers by last name
            log.info("AudioTrackProgress found with findByLastNameStartsWithIgnoreCase('Bauer'):");
            log.info("--------------------------------------------");
            for (AudioTrackProgress bauer : repository
                    .findByLastNameStartsWithIgnoreCase("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");
            AppLauncher.main(null);
        };
    }
}
