package marketplace.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AnalyticsDaoSqlImpl implements AnalyticsDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<AnalyticsDto> findNameStoreAndAmountCategoryForSales() { // OK

        return jdbcTemplate.query("""
            SELECT s.name AS store_name, COUNT(p.category) AS amount_category
            FROM Store s JOIN Product p ON s.id = p.store_id
            WHERE p.category IN(SELECT category FROM Product WHERE category NOT IN ('PC', 'TV'))
            GROUP BY s.name""",
            ((rs, rowNum) -> {
                return AnalyticsDto.builder()
                        .name(rs.getString("store_name"))
                        .amount(rs.getInt("amount_category"))
                        .build();
            }));
    }

    @Override
    public List<AnalyticsDtoSumMoney> findNameClientAndSumCashByBrand(String brand) { // OK

        return jdbcTemplate.query("""
            SELECT DISTINCT c.name AS client_name, SUM(p.price) AS sum_cash
            FROM Purchase_history ph JOIN Client c ON ph.client_id = c.id
            JOIN Product p ON ph.product_id = p.id
            WHERE brand = ?
            GROUP BY c.name""",
            ((rs, rowNum) -> {
                return AnalyticsDtoSumMoney.builder()
                        .name(rs.getString("client_name"))
                        .sumMoney(rs.getBigDecimal("sum_cash"))
                        .build();
            }), brand);
    }

    @Override
    public List<AnalyticsDto> findNameStoreByMaxSalesByBrand(String brand) { //OK

        return jdbcTemplate.query("""
            SELECT DISTINCT s.name AS store_name, MAX(ph.amount) AS max_amount_buy
            FROM Product p
            JOIN Purchase_history ph ON p.id = ph.product_id
            JOIN Store s ON s.id = p.store_id
            JOIN Product_amount pa ON pa.product_id = p.id
            WHERE brand = ?
            GROUP BY s.name
            HAVING MAX(ph.amount) > COUNT(ph.amount);""",
            ((rs, rowNum) -> {
                return AnalyticsDto.builder()
                        .name(rs.getString("store_name"))
                        .amount(rs.getInt("max_amount_buy"))
                        .build();
            }), brand);
    }

    @Override
    public AnalyticsDto findNameClientAndCountIn3Category(Integer clientId) { //OK

        return jdbcTemplate.queryForObject("""
            SELECT c.name AS client_name, COUNT(ph.amount) AS amount_buy_product
            FROM Client c
            JOIN Purchase_history ph on c.id = ph.client_id
            JOIN Product p ON p.id = ph.product_id
            WHERE c.id = ?
            GROUP BY c.name
            HAVING COUNT(DISTINCT category) >= 3;""",
            ((rs, rowNum) -> {
                return AnalyticsDto.builder()
                        .name(rs.getString("client_name"))
                        .amount(rs.getInt("amount_buy_product"))
                        .build();
            }), clientId);
    }

    @Override
    public List<AnalyticsDto> findNameBrandAndAmountCategoryByPrice(BigInteger priceLimit) { //OK

        // сделать чтобы метод принимал переменную, и она вставлялась в запрос sql ************************************
        return jdbcTemplate.query("""
            SELECT brand, COUNT(DISTINCT category) AS amount_category
            FROM Product
            WHERE price >= ?
            GROUP BY brand;""",
            ((rs, rowNum) -> {
                return AnalyticsDto.builder()
                        .name(rs.getString("brand"))
                        .amount(rs.getInt("amount_category"))
                        .build();
            }), priceLimit);
    }

    @Override
    public AnalyticsDtoAvgPrice findAvgPriceByCategory(String category) { // OK

        return jdbcTemplate.queryForObject("""
            SELECT category, AVG(price) AS avg_price
            FROM Product
            WHERE category = ?
            GROUP BY category;""",
            ((rs, rowNum) -> {
                return AnalyticsDtoAvgPrice.builder()
                        .name(rs.getString("category"))
                        .avgPrice(rs.getBigDecimal("avg_price"))
                        .build();
            }), category);
    }

    @Override
    public AnalyticsDtoMaxPrice findCategoryAndMaxPrice(String category) { // OK

        return jdbcTemplate.queryForObject("""
            SELECT category, MAX(price) AS max_price
            FROM Product
            WHERE category = ?
            GROUP BY category;""",
            ((rs, rowNum) -> {
                return AnalyticsDtoMaxPrice.builder()
                        .name(rs.getString("category"))
                        .maxPrice(rs.getBigDecimal("max_price"))
                        .build();
            }), category);
    }

    @Override
    public List<AnalyticsDtoMaxPrice> findProductByMaxPriceBrand() { // OK

        return jdbcTemplate.query("""
            SELECT DISTINCT brand, MAX(price) AS max_price
            FROM Product
            GROUP BY brand;""",
            ((rs, rowNum) -> {
                return AnalyticsDtoMaxPrice.builder()
                        .name(rs.getString("brand"))
                        .maxPrice(rs.getBigDecimal("max_price"))
                        .build();
            }));
    }

    @Override
    public List<AnalyticsDto> findStoreAndAmountProductsOneBrand(String brand) { // OK

        return jdbcTemplate.query("""
            SELECT s.name AS store_name, pa.amount AS amount_product
            FROM Store s
            JOIN Product p on s.id = p.store_id
            JOIN Product_amount pa on p.id = pa.product_id
            WHERE brand = ? AND pa.amount >= 3;""",
            ((rs, rowNum) -> {
                return AnalyticsDto.builder()
                        .name(rs.getString("store_name"))
                        .amount(rs.getInt("amount_product"))
                        .build();
            }), brand);
    }

    @Override
    public List<AnalyticsDtoSumMoney> findClientAndCashByStore(Integer storeId) { // OK

        return jdbcTemplate.query("""
            SELECT c.name AS client_name, SUM(p.price) AS sum_cash
            FROM Client c
            JOIN Purchase_history ph ON c.id = ph.client_id
            JOIN Product p ON p.id = ph.product_id
            WHERE store_id = ?
            GROUP BY c.name;""",
            ((rs, rowNum) -> {
                return AnalyticsDtoSumMoney.builder()
                        .name(rs.getString("client_name"))
                        .sumMoney(rs.getBigDecimal("sum_cash"))
                        .build();
            }), storeId);
    }

    @Override
    public List<AnalyticsDtoSumMoney> findStoreAndSumCashByBrand(String brand) { //OK
        return jdbcTemplate.query("""
            SELECT s.name AS store_name, SUM(p.price) AS sum_cash
            FROM Purchase_history ph
                JOIN Product p ON ph.product_id = p.id
                JOIN Store s ON p.store_id = s.id
            WHERE p.brand = ?
            GROUP BY s.name;""", ((rs, rowNum) -> {
            return AnalyticsDtoSumMoney.builder()
                .name(rs.getString("store_name"))
                .sumMoney(rs.getBigDecimal("sum_cash"))
                .build();
            }), brand);
    }

    @Override
    public Map<String, String> findNameClientAndNameStoreByMax(Integer clientId) {
        return null;
    }

    @Override
    public Map<String, String> findNameClientAndNameStoreByMaxCash(Integer clientId) {
        return null;
    }
}