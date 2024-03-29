package ru.sablin.app.marketplace.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class AnalyticsSqlImplDao implements AnalyticsDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<AnalyticsDto> findNameStoreAndAmountCategoryForSales() {

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
    public List<AnalyticsSumMoneyDto> findNameClientAndSumCashByBrand(String brand) {

        return jdbcTemplate.query("""
            SELECT DISTINCT c.name AS client_name, SUM(p.price) AS sum_cash
            FROM Purchase_history ph JOIN Client c ON ph.client_id = c.id
            JOIN Product p ON ph.product_id = p.id
            WHERE brand = ?
            GROUP BY c.name""",
            ((rs, rowNum) -> {
                return AnalyticsSumMoneyDto.builder()
                        .name(rs.getString("client_name"))
                        .sumMoney(rs.getBigDecimal("sum_cash"))
                        .build();
            }), brand);
    }

    @Override
    public List<AnalyticsDto> findNameStoreByMaxSalesByBrand(String brand) {

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
    public AnalyticsDto findNameClientAndCountIn3Category(Integer clientId) {

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
    public List<AnalyticsDto> findNameBrandAndAmountCategoryByPrice(BigDecimal priceLimit) {

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
    public AnalyticsAvgPriceDto findAvgPriceByCategory(String category) {

        return jdbcTemplate.queryForObject("""
            SELECT category, AVG(price) AS avg_price
            FROM Product
            WHERE category = ?
            GROUP BY category;""",
            ((rs, rowNum) -> {
                return AnalyticsAvgPriceDto.builder()
                        .name(rs.getString("category"))
                        .avgPrice(rs.getBigDecimal("avg_price"))
                        .build();
            }), category);
    }

    @Override
    public AnalyticsMaxPriceDto findCategoryAndMaxPrice(String category) {

        return jdbcTemplate.queryForObject("""
            SELECT category, MAX(price) AS max_price
            FROM Product
            WHERE category = ?
            GROUP BY category;""",
            ((rs, rowNum) -> {
                return AnalyticsMaxPriceDto.builder()
                        .name(rs.getString("category"))
                        .maxPrice(rs.getBigDecimal("max_price"))
                        .build();
            }), category);
    }

    @Override
    public List<AnalyticsMaxPriceDto> findProductByMaxPriceBrand() {

        return jdbcTemplate.query("""
            SELECT DISTINCT brand, MAX(price) AS max_price
            FROM Product
            GROUP BY brand;""",
            ((rs, rowNum) -> {
                return AnalyticsMaxPriceDto.builder()
                        .name(rs.getString("brand"))
                        .maxPrice(rs.getBigDecimal("max_price"))
                        .build();
            }));
    }

    @Override
    public List<AnalyticsDto> findStoreAndAmountProductsOneBrand(String brand) {

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
    public List<AnalyticsSumMoneyDto> findClientAndCashByStore(Integer storeId) {

        return jdbcTemplate.query("""
            SELECT c.name AS client_name, SUM(p.price) AS sum_cash
            FROM Client c
            JOIN Purchase_history ph ON c.id = ph.client_id
            JOIN Product p ON p.id = ph.product_id
            WHERE store_id = ?
            GROUP BY c.name;""",
            ((rs, rowNum) -> {
                return AnalyticsSumMoneyDto.builder()
                        .name(rs.getString("client_name"))
                        .sumMoney(rs.getBigDecimal("sum_cash"))
                        .build();
            }), storeId);
    }

    @Override
    public List<AnalyticsSumMoneyDto> findStoreAndSumCashByBrand(String brand) {
        return jdbcTemplate.query("""
            SELECT s.name AS store_name, SUM(p.price) AS sum_cash
            FROM Purchase_history ph
                JOIN Product p ON ph.product_id = p.id
                JOIN Store s ON p.store_id = s.id
            WHERE p.brand = ?
            GROUP BY s.name;""", ((rs, rowNum) -> {
            return AnalyticsSumMoneyDto.builder()
                .name(rs.getString("store_name"))
                .sumMoney(rs.getBigDecimal("sum_cash"))
                .build();
            }), brand);
    }
}