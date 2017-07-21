package net.utsman.example.bookmarks;

import net.utsman.example.bookmarks.core.config.AbstractDummyDataCommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HateoasApplication extends AbstractDummyDataCommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(HateoasApplication.class, args);
    }
}
