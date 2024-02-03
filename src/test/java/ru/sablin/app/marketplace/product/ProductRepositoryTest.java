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

    Product createdProduct() {
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
        repository.create(2, createdProduct());

        var productExpected = repository.findById(createdProduct().getId());

        assertNotNull(productExpected);
        assertEquals(productExpected, createdProduct());
    }

    @Test
    void buyProduct() {
        var nameProduct = "Iphone8";
        var amount = 1;

        var buyProduct = repository.buyProduct(111, 1, 1, amount);

        assertEquals(buyProduct.getName(), "Iphone8");
        assertEquals(buyProduct.getBrand(), "Apple");
        assertEquals(buyProduct.getPrice(), BigDecimal.valueOf(10_000));
        assertEquals(buyProduct.getAmount(), String.format("There are few of this product left. Congratulations on your purchase %d %s!", amount, nameProduct));

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

        var expectedListProducts = repository.findProducts(
                "TV",null,null,null,null,null,null
        );

        assertNotNull(expectedListProducts);
        assertEquals(expectedListProducts.size(), 2);
        assertEquals(expectedListProducts, productList);
    }
}