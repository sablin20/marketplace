package marketplace.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductRepositorySqlImplTest {

    @Autowired
    ProductRepositorySqlImpl repository;

    @Autowired
    ProductController controller;

    @Test
    void mTest() {
        var result = repository.findById(1);

        assertEquals(result.getId(), 1);

    }

    @Test
    void mTest2() {

        controller.create(2, Product.builder()
                .id(22)
                .name("Ihsasa")
                .storeId(1)
                .category("TV")
                .price(new BigDecimal(100_000))
                .brand("LG")
                .build());

    }
}