package ru.sablin.app.marketplace;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "db.stream-api")
public class DbApiImplementationProperty {
    private boolean enabled;

}
