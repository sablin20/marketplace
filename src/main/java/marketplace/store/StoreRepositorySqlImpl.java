package marketplace.store;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import marketplace.customexception.NoSuchProduct;
import marketplace.product.Product;
import marketplace.product.ProductRepositorySqlImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Data
@RequiredArgsConstructor
@Repository
public class StoreRepositorySqlImpl implements StoreRepository {

    private final JdbcTemplate jdbcTemplate;
    private ProductRepositorySqlImpl productRepositorySql;

    @Override
    public Store create(Store store) {
        jdbcTemplate.update("INSERT INTO Store VALUES (?,?,?)",
                                store.getId(), store.getName(), store.getProducts());
        return store;
    }

    @Override
    public Store findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Store WHERE id = ?", Store.class, id);
    }

    @Override
    public void addProductForSale(String storeId, String productId) {
        var store = findById(storeId);
        var product = productRepositorySql.findById(productId);
        jdbcTemplate.update("UPDATE Store SET products = ? WHERE id = ?", product.getId(), store.getId());
    }

    @Override
    public void deleteProductSales(String storeId, String productId) {
        var store = findById(storeId);
        store.getProducts().stream().
                filter(p -> p.getId().equals(productId)).
                findFirst().
                orElseThrow(() -> new NoSuchProduct(String.format("There is no such product id = %s in the product for sales", productId)));
        jdbcTemplate.update("DELETE products FROM Store WHERE products = ?", productId);
    }

    @Override
    public void updateProducts(String storeId, Product product) {
        var store = findById(storeId);
        var productListInStore = jdbcTemplate.query("SELECT products FROM Store WHERE id = ?",
                                                                new BeanPropertyRowMapper<>(Product.class), store.getId());
        var productForUpdate = productListInStore.stream().
                filter(p -> p.getId().equals(product.getId())).
                findFirst().
                orElseThrow(() -> new NoSuchProduct(String.format("There is no such product id = %s in the product for sales", product.getId())));
        productForUpdate.setId(product.getId());
        productForUpdate.setName(product.getName());
        productForUpdate.setPrice(product.getPrice());
        productForUpdate.setCategory(product.getCategory());
        productForUpdate.setBrand(product.getBrand());
        product.setAmount(product.getAmount());
    }
}