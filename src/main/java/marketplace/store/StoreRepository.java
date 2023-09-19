package marketplace.store;

import marketplace.product.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository {
    Store create(Store store);
    Store findById(String id);
    void addProductForSale(String storeId, String productId);
    void deleteProductSales(String storeId, String productId);
    void updateProducts(String storeId, Product product);
}