package marketplace.store;

import marketplace.product.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository {
    Store create(Store store);
    Store findById(Integer id);
//    void deleteProductSales(Integer storeId, Integer productId);
    void updateProducts(Integer storeId, Product product);
}