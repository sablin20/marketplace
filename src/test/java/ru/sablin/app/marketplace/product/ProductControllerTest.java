package ru.sablin.app.marketplace.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductController controller;

    @Mock
    Product productMock;

    @BeforeEach
    void m(){
        productMock = new Product();
        productMock.setId(1);
        productMock.setBrand("Apple");
        productMock.setCategory("Smartphone");
        productMock.setName("Iphone15");
        productMock.setStoreId(4);
        productMock.setPrice(new BigDecimal(100_000));
    }

    @Test
    void create_() {
        controller.create(1, productMock);

        verify(repository).create(1, productMock);
    }

    @Test
    void removeById() {
        controller.removeById(productMock.getId());

        assertNull(controller.getById(productMock.getId()));
        verify(repository).removeById(productMock.getId());
    }

    @Test
    void getById_returnProductById_isExist() {
        when(repository.findById(1)).thenReturn(productMock);

        var result = controller.getById(1);

        assertNotNull(result);
        assertEquals(result.getStoreId(), productMock.getStoreId());
        assertEquals(result, productMock);
        verify(repository).findById(1);
    }

    @Test
    void getProducts_returnProductsByParams_isExist() {
        var productList = List.of(
                ProductDto.builder()
                .id(11)
                .brand("Apple")
                .category("Smartphone")
                .name("Iphone11")
                .storeName("OZON")
                .price(new BigDecimal(55_000))
                .build(),

                ProductDto.builder()
                .id(1)
                .brand("Apple")
                .category("Smartphone")
                .name("Iphone15")
                .storeName("AppStore")
                .price(new BigDecimal(100_000))
                .build());

        when(repository.findProducts("Smartphone",
                null,
                null,
                null,
                null,
                null,
                null)).thenReturn(productList);

        var result = controller.getProducts("Smartphone",
                null,
                null,
                null,
                null,
                null,
                null);

        assertNotNull(result);
        assertEquals(result, productList);
    }

    @Test
    void updateProducts() {
        controller.updateProducts(4, productMock);

        verify(repository).updateProducts(4, productMock);
    }

    @Test
    void buyProduct() {
        var productDto = ProductDto.builder()
                .id(productMock.getId())
                .category(productMock.getCategory())
                .brand(productMock.getBrand())
                .name(productMock.getName())
                .price(productMock.getPrice())
                .storeName("DNS")
                .build();
        when(repository.buyProduct(1,2,productMock.getId(),2)).thenReturn(productDto);

        var result = controller.buyProduct(1,2, 1, 2);

        assertEquals(result.getPrice(), productDto.getPrice());
        assertEquals(result.getName(), productDto.getName());
        assertEquals(result.getBrand(), productDto.getBrand());
        assertEquals(result.getCategory(), productDto.getCategory());
        assertEquals(result.getAmount(), productDto.getAmount());
        assertEquals(result.getId(), productDto.getId());
        assertEquals(result.getStoreName(), productDto.getStoreName());
        assertEquals(result, productDto);
        verify(repository).buyProduct(1,2,1,2);
    }
}