package marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(Config.class)
public class MarketplaceApp {
    public static void main(String[] args) {
        SpringApplication.run(MarketplaceApp.class, args);
    }
}