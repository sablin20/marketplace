package ru.sablin.app.marketplace.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductRepositorySqlImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProductRepositorySqlImpl repository;

    @Mock
    private Product product;

    @BeforeEach
    void factoryProduct() {
        product = new Product();
        product.setId(32);
        product.setBrand("Apple");
        product.setCategory("Smartphone");
        product.setName("Iphone15");
        product.setStoreId(4);
        product.setPrice(new BigDecimal(100_000));
    }

    @Test
    void updateProducts() {
    }

    @Test
    void create() {
    }

    @Test
    void buyProduct() {
    }

    @Test
    void findById() {
        when(jdbcTemplate.queryForObject("select * from Product where id = ?",
                new BeanPropertyRowMapper<>(Product.class), product.getId())).thenReturn(product);

        var result = repository.findById(product.getId());

        assertNotNull(result);
        assertEquals(result, product);
    }

    @Test
    void removeById() {
    }

    @Test
    void findProducts() {
    }
}