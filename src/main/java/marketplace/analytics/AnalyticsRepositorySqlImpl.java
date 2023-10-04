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
    public List<Store> findStoreNameAndAmountProductForSales() {
         return jdbcTemplate.query("SELECT s.name, COUNT(p.category) " +
                                "FROM Store s JOIN Product p ON s.id = p.store_id " +
                                "WHERE p.category IN (SELECT category FROM p WHERE category NOT IN ('PC', 'TV')) " +
                                "GROUP BY s.name", new BeanPropertyRowMapper<>(Store.class));
    }

    @Override
    public List<Client> findClientNameAndSumCashByBrand(String brand) {
        return jdbcTemplate.query("SELECT DISTINCT c.name, SUM(p.price) " +
                "FROM PurchaseHistory ph JOIN Client c ON ph.client_id = c.id " +
                "JOIN Product p ON ph.product_id = p.id " +
                "WHERE brand = ? " +
                "GROUP BY c.name",
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
    public BigDecimal findAvgPriceByCategory(String category) {
        return null;
    }

    @Override
    public Map<String, BigDecimal> findCategoryAndMaxPrice(String category) {
        return null;
    }

    @Override
    public Map<Product, String> findProductByMaxPriceBrand() {
        return null;
    }

    @Override
    public Map<Store, Integer> findStoreAndAmountProductsOneBrand(String brand) {
        return null;
    }

    @Override
    public Map<Store, BigDecimal> findStoreAndSumCashByBrand(String brand) {
        return null;
    }

    @Override
    public Map<Client, BigDecimal> findClientAndCashByStore(String storeId) {
        return null;
    }
}