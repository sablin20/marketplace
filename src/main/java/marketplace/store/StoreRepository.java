package marketplace.store;

import marketplace.product.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository {
    String create(Store store);
    Store findById(String id);
    String addProductForSale(String storeId, String productId); // добавить Товар На Продажу
    String deleteProductSales(String storeId, String productId); // удалить Товар С Продажи
    String updateProducts(String storeId, Product product); // изменить товар(цену, описание)
}