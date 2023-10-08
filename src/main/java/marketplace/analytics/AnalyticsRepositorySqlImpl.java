package marketplace.analytics;

import lombok.RequiredArgsConstructor;
import marketplace.client.Client;
import marketplace.product.Product;
import marketplace.store.Store;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AnalyticsRepositorySqlImpl implements Analytics {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Store> findStoreNameAndAmountProductForSales() { // OK
         return jdbcTemplate.query("""
                                        SELECT s.name, COUNT(p.category)
                                        FROM Store s JOIN Product p ON s.id = p.store_id
                                        WHERE p.category IN(SELECT category FROM p WHERE category NOT IN ('PC', 'TV'))
                                        GROUP BY s.name""",
                                        new BeanPropertyRowMapper<>(Store.class));
    }

    @Override
    public List<Client> findClientNameAndSumCashByBrand(String brand) { // OK
        return jdbcTemplate.query("""
                                        SELECT DISTINCT c.name, SUM(p.price)
                                        FROM Purchase_history ph JOIN Client c ON ph.client_id = c.id
                                        JOIN Product p ON ph.product_id = p.id
                                        WHERE brand = ?
                                        GROUP BY c.name""",
                                        new BeanPropertyRowMapper<>(Client.class), brand);
    }

    @Override
    public List<Store> findNameStoreByMaxSalesByBrand(String brand) {
//        return jdbcTemplate.query("SELECT DISTINCT s.name " +
//                            "FROM Store s " +
//                            "JOIN Purchase_history ph ON s.id = ph.store_id " +
//                            "JOIN Product p ON p.store_id = s.id " +
//                            "WHERE brand = ?",
//                new BeanPropertyRowMapper<>(Store.class), brand);
        return null;
    }

    @Override
    public Map<String, String> findNameClientAndNameStoreByMax(String clientId) {
        return null;
    }

    @Override
    public Map<String, String> findNameClientAndNameStoreByMaxCash(String clientId) {
        return null;
    }

    @Override
    public Map<String, Integer> findNameClientAndCountIn3Category(String clientId) {
        return null;
    }

    @Override
    public Map<String, Integer> findNameBrandAndCountCategoryByPrice() {
        return null;
    }

    @Override
    public Product findAvgPriceByCategory(String category) { // OK
        return jdbcTemplate.queryForObject("SELECT AVG(price) FROM Product WHERE category = ?",
                new BeanPropertyRowMapper<>(Product.class), category);
    }

    @Override
    public Product findCategoryAndMaxPrice(String category) { // OK
        return jdbcTemplate.queryForObject("""
                                                SELECT category, MAX(price) 
                                                FROM Product WHERE category = ? 
                                                GROUP BY category;""",
                                                new BeanPropertyRowMapper<>(Product.class), category);
    }

    @Override
    public Product findProductByMaxPriceBrand() { // OK
        return jdbcTemplate.queryForObject("""
                                                SELECT DISTINCT brand, MAX(price)
                                                FROM Product
                                                GROUP BY brand;""",
                                                new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Store findStoreAndAmountProductsOneBrand(String brand) { // OK
        return jdbcTemplate.queryForObject("""
                                                SELECT s.name AS store_name, pa.amount AS amount_product
                                                FROM Store s
                                                JOIN Product p on s.id = p.store_id
                                                JOIN Product_amount pa on p.id = pa.product_id
                                                WHERE brand = ? AND pa.amount >= 3;""",
                                                new BeanPropertyRowMapper<>(Store.class), brand);
    }

    @Override
    public Map<Store, BigDecimal> findStoreAndSumCashByBrand(String brand) {
        return null;
    }

    @Override
    public List<Client> findClientAndCashByStore(String storeId) { // OK
        return jdbcTemplate.query("""
                                        SELECT c.name AS client_name, SUM(p.price) AS sum_cash
                                        FROM Client c
                                        JOIN Purchase_history ph ON c.id = ph.client_id
                                        JOIN Product p ON p.id = ph.product_id
                                        WHERE store_id = ?
                                        GROUP BY c.name;""",
                                        new BeanPropertyRowMapper<>(Client.class), storeId);
    }
}