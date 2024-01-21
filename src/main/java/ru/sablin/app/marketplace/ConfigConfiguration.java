package ru.sablin.app.marketplace;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.sablin.app.marketplace.product.ProductRepository;
import ru.sablin.app.marketplace.product.ProductRepositorySqlImpl;
import ru.sablin.app.marketplace.product.ProductRepositoryStreamImpl;

@Configuration
@RequiredArgsConstructor
public class ConfigConfiguration {

    private final JdbcTemplate jdbcTemplate;

    @Bean
    @ConditionalOnProperty(value = "db.stream-api.enabled", havingValue = "true")
    public ProductRepository createDbApiRepository() {
        return new ProductRepositoryStreamImpl(jdbcTemplate);
    }

    @Bean
    @ConditionalOnMissingBean(name = "createDbApiRepository")
    public ProductRepository createDbRepository() {
        return new ProductRepositorySqlImpl(jdbcTemplate);
    }
}