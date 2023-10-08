package marketplace.store;

import lombok.RequiredArgsConstructor;
import marketplace.product.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreRepositorySqlImpl implements StoreRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Store create(Store store) {
        jdbcTemplate.update("INSERT INTO Store VALUES (?,?)",
                                store.getId(),
                                store.getName());

        return jdbcTemplate.queryForObject("SELECT * FROM Store WHERE id = ?",
                new BeanPropertyRowMapper<>(Store.class), store.getId());
    }

    @Override
    public Store findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Store WHERE id = ?",
                new BeanPropertyRowMapper<>(Store.class), id);
    }

//    @Override
//    public void deleteProductSales(Integer storeId, Integer productId) {
//        jdbcTemplate.update("DELETE FROM Product WHERE store_id = ? AND product_id = ?", storeId, productId);
//    }

    @Override
    public void updateProducts(Integer storeId, Product product) {
        jdbcTemplate.update("""
                            UPDATE Product SET
                            id = ?,
                            name = ?,
                            price = ?,
                            category = ?,
                            brand = ?,
                            store_id = ?
                            WHERE store_id = ? AND id = ?""",
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getCategory(),
                            product.getBrand(),
                            product.getStoreId(),
                            storeId, product.getId());
    }
}