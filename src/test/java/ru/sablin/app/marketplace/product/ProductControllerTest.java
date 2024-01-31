package ru.sablin.app.marketplace.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @MockBean
    ProductRepository repository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MockMvc mockMvc;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    ObjectMapper objectMapper;

    Product created_product(){
        var productMock = new Product();
        productMock.setId(1);
        productMock.setBrand("Apple");
        productMock.setCategory("Smartphone");
        productMock.setName("Iphone15");
        productMock.setStoreId(4);
        productMock.setPrice(new BigDecimal(100_000));
        return productMock;
    }

    @Test
    void create_() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("amount", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created_product())))
                .andExpect(status().isOk());

        verify(repository, times(1)).create(1, created_product());
    }

    @Test
    void removeById() throws Exception {
        mockMvc.perform(delete("/product/{id}", 1))
                .andExpect(status().isOk());

        verify(repository, times(1)).removeById(1);
    }

    @Test
    void getById_returnProductById_isExist() throws Exception {
        when(repository.findById(1)).thenReturn(created_product());

        mockMvc.perform(get("/product/")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Iphone15"))
                .andExpect(jsonPath("$.brand").value("Apple"))
                .andExpect(jsonPath("$.category").value("Smartphone"))
                .andExpect(jsonPath("$.price").value(BigDecimal.valueOf(100_000)))
                .andExpect(jsonPath("$.storeId").value(4));

        verify(repository, times(1)).findById(1);
    }

    @Test
    void getProducts_returnProductsByParams_isExist() throws Exception {
        var productDtoExpected = ProductDto.builder()
                .id(11)
                .brand("Apple")
                .category("Smartphone")
                .name("Iphone11")
                .storeName("OZON")
                .price(new BigDecimal(55_000))
                .build();

        when(repository.findProducts("Smartphone",
                null,
                null,
                null,
                null,
                null,
                null)).thenReturn(List.of(productDtoExpected));

        mockMvc.perform(get("/product/findProducts")
                        .param("category", "Smartphone"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value(productDtoExpected.getName()))
                .andExpect(jsonPath("$.[0].category").value(productDtoExpected.getCategory()))
                .andExpect(jsonPath("$.[0].price").value(productDtoExpected.getPrice()))
                .andExpect(jsonPath("$.[0].brand").value(productDtoExpected.getBrand()));

        verify(repository, times(1)).findProducts("Smartphone",
                                                                null,
                                                                null,
                                                                null,
                                                                null,
                                                                null,
                                                                null);
    }

    @Test
    void updateProducts() throws Exception {
        mockMvc.perform(put("/product/")
                        .param("storeId", "4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(created_product())))
                .andExpect(status().isOk());

        verify(repository, times(1)).updateProducts(4, created_product());
    }

    @Test
    void buyProduct() throws Exception {
        var productDto = ProductDto.builder()
                .id(created_product().getId())
                .category(created_product().getCategory())
                .brand(created_product().getBrand())
                .name(created_product().getName())
                .price(created_product().getPrice())
                .storeName("DNS")
                .build();

        when(repository.buyProduct(18, 77, created_product().getId(), 2)).thenReturn(productDto);

        mockMvc.perform(post("/product/buy")
                        .param("id", "18")
                        .param("clientId", "77")
                        .param("productId", "1")
                        .param("amount", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(productDto.getName()))
                .andExpect(jsonPath("$.price").value(productDto.getPrice()))
                .andExpect(jsonPath("$.brand").value(productDto.getBrand()))
                .andExpect(jsonPath("$.category").value(productDto.getCategory()));

        verify(repository, times(1)).buyProduct(18, 77, created_product().getId(), 2);
    }
}