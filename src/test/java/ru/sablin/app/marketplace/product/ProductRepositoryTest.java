package ru.sablin.app.marketplace.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql({"/drop-schema.sql", "/test-schema.sql"})
@Sql(scripts = "/test-data.sql")
class ProductRepositoryTest {

    @Autowired
    ProductRepository repository;

    Product created_product() {
        var product = new Product();
        product.setId(32);
        product.setBrand("Apple");
        product.setCategory("Smartphone");
        product.setName("Iphone15");
        product.setStoreId(4);
        product.setPrice(new BigDecimal(100_000));
        return product;
    }

    @Test
    void updateProducts() {
        var product = new Product();
        product.setId(4);
        product.setName("MacBook15");
        product.setCategory("Laptop");
        product.setBrand("Apple");
        product.setStoreId(1);
        product.setPrice(BigDecimal.valueOf(77_900));

        repository.updateProducts(1, product);

        var expected = repository.findById(product.getId());

        assertNotNull(expected);
        assertEquals(expected.getId(), product.getId());
        assertEquals(expected.getBrand(), product.getBrand());
        assertEquals(expected.getName(), product.getName());
        assertEquals(expected, product);
    }

    @Test
    void create() {
        repository.create(2, created_product());

        var product_expected = repository.findById(created_product().getId());

        assertNotNull(product_expected);
        assertEquals(product_expected, created_product());
    }

    @Test
    void buyProduct() {
        var nameProduct = "Iphone8";
        var amount = 1;

        var buy_product = repository.buyProduct(111, 1, 1, amount);

        assertEquals(buy_product.getName(), "Iphone8");
        assertEquals(buy_product.getBrand(), "Apple");
        assertEquals(buy_product.getPrice(), BigDecimal.valueOf(10_000));
        assertEquals(buy_product.getAmount(), String.format("There are few of this product left. Congratulations on your purchase %d %s!", amount, nameProduct));

    }

    @Test
    void findById() {
        var result = repository.findById(1);

        assertNotNull(result);
        assertEquals(result.getId(), 1);
        assertEquals(result.getName(), "Iphone8");
        assertEquals(result.getBrand(), "Apple");
        assertEquals(result.getPrice(), BigDecimal.valueOf(10_000));
    }

    @Test
    void removeById() {
        var result = repository.findById(2);

        var removeProduct = ProductDto.builder()
                .name(result.getName())
                .price(result.getPrice())
                .brand(result.getBrand())
                .category(result.getCategory())
                .storeName("Citilink")
                .id(result.getId())
                .amount("This product is enough")
                .build();

        repository.removeById(2);

        var listProductsByCategoryTV = repository.findProducts(
                removeProduct.getCategory(),
                null,
                null,
            null,
            null,
             null,
            null);

        assertEquals(listProductsByCategoryTV.size(), 1);
        assertFalse(listProductsByCategoryTV.contains(removeProduct));
    }

    @Test
    void findProducts() {
        List<ProductDto> productList = Arrays.asList(
                ProductDto.builder()
                        .name("UHD600tv12")
                        .price(BigDecimal.valueOf(60_000))
                        .brand("LG")
                        .category("TV")
                        .storeName("Citilink")
                        .id(2)
                        .amount("This product is enough")
                        .build(),

                ProductDto.builder()
                        .name("Sony8K")
                        .price(BigDecimal.valueOf(77_000))
                        .brand("Sony")
                        .category("TV")
                        .storeName("OZON")
                        .id(5)
                        .amount("There is a lot of this product in stock")
                        .build()
        );

        var expected_list_products = repository.findProducts(
                "TV",null,null,null,null,null,null
        );

        assertNotNull(expected_list_products);
        assertEquals(expected_list_products.size(), 2);
        assertEquals(expected_list_products, productList);
    }
}