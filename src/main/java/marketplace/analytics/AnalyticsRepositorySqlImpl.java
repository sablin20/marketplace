package marketplace.analytics;

import lombok.RequiredArgsConstructor;
import marketplace.client.Client;
import marketplace.product.Product;
import marketplace.store.Store;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AnalyticsRepositorySqlImpl implements Analytics {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Integer> findStoreNameAndAmountProductForSales() {
        return null;
    }

    @Override
    public Map<String, BigDecimal> findClientNameAndSumCashByBrand(String brand) {
        var name = jdbcTemplate.query("SELECT Client.name, SUM(Product.price) " +
                "FROM Product, PurchaseHistory AS PH JOIN Client ON PH.clientId = Client.id AND PH.productId = Product.id " +
                "WHERE brand = ? " +
                "GROUP BY Client.name", new BeanPropertyRowMapper<>(), brand);
        return null;
    }

    @Override
    public String findNameStoreByBrand(String brand) {
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
