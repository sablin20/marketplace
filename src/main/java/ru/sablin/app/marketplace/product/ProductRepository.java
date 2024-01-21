package ru.sablin.app.marketplace.product;

import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository {

    void updateProducts(Integer storeId, Product product);
    void create(Integer amount, Product product);
    void removeById(Integer id);
    Product findById(Integer id);
    ProductDto buyProduct(Integer id,
                          Integer clientId,
                          Integer productId,
                          Integer amount);
    List<ProductDto> findProducts(String category,
                                  String name,
                                  String brand,
                                  String sortedName,
                                  String sortedPrice,
                                  BigDecimal priceFirst,
                                  BigDecimal priceLast);
}
