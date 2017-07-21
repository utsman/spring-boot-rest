package net.utsman.example.bookmarks;

import net.utsman.example.bookmarks.core.config.AbstractDummyDataCommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class SecurityApplication extends AbstractDummyDataCommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:8080")
                    .allowedMethods("POST","GET","OPTIONS","DELETE")
                        .maxAge(60 * 60)
                        .allowCredentials(false)
                        .allowedHeaders("Access-Control-Allow-Headers","Origin","Accept","X-Requested-With",
                                "Content-Type","Access-Control-Request-Method","Access-Control-Request-Headers","Authorization");
            }
        };
    }
}
