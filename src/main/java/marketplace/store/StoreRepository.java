package marketplace.store;

import marketplace.product.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository {
    Store create(Store store);
    Store findById(Integer id);
    void addProductForSale(Integer id, Integer storeId, Integer productId);
    void deleteProductSales(Integer storeId, Integer productId);
    void updateProducts(Integer id, Integer storeId, Product product);
}