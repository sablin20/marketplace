package marketplace.product;

import lombok.RequiredArgsConstructor;
import marketplace.customexception.ProductNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Primary для того чтобы не было неоднозначности при запуске и внедрении зависимости т.к.
 * Два бина(ProductRepositorySqlImpl и ProductRepositoryStreamImpl) яв-ся кандидатами на внедрение,
 * дальше сделаем с помощью .property
 */
@Primary
@Repository
@RequiredArgsConstructor
public class ProductRepositorySqlImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    private void validValue(List<Product> product) {
        if (product.size() == 0) {
            throw new ProductNotFoundException("No Products by this parameters");
        }
    }

    @Override
    public Product create(Product product) {
        jdbcTemplate.update("INSERT INTO Product VALUES (?,?,?,?,?)",
                product.getName(), product.getPrice(), product.getCategory(), product.getBrand(), product.getAmount());
        return product;
    }

    @Override
    public Product findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Product WHERE id = ?", Product.class, id);
    }

    @Override
    public void removeById(String id) {
        jdbcTemplate.update("DELETE FROM Product WHERE id = ?", id);
    }

    @Override
    public List<Product> findProducts(String category, String name, String brand, String sortedName, String sortedPrice, BigDecimal price1, BigDecimal price2) {

        var condition = "";
        var substringCategory = "";
        var substringName = "";
        var substringBrand = "";
        var sortedByNameOrPrice = "";
        var sortedByPriceBetween = "";

        if (category != null) {
            condition += " WHERE ";
            substringCategory = String.format("category LIKE '%%%s%%'", category);
            condition += substringCategory;
        }

        if (name != null) {
            substringName = String.format("name LIKE '%%%s%%'",  name);
            if (!condition.isEmpty()) {
                condition += " AND ";
            } else {
                condition += " WHERE ";
            }
            condition += substringName;
        }

        if (brand != null) {
            substringBrand = String.format("brand LIKE '%%%s%%'", brand);
            if (!condition.isEmpty()) {
                condition += " AND ";
            } else {
                condition += " WHERE ";
            }
            condition += substringBrand;
        }

        if (price1 != null && price2 != null) {
            sortedByPriceBetween = String.format("price BETWEEN %s AND %s", price1, price2);
            if (!condition.isEmpty()) {
                condition += " AND ";
            } else {
                condition += " WHERE ";
            }
            condition += sortedByPriceBetween;
        }

        if (sortedName.equals("ASC") || sortedName.equals("DESC")) {
            sortedByNameOrPrice = String.format(" ORDER BY name %s", sortedName);
            condition += sortedByNameOrPrice;
        }
        if (sortedPrice.equals("ASC") || sortedPrice.equals("DESC")) {
            sortedByNameOrPrice = String.format(" ORDER BY price %s", sortedPrice);
            condition += sortedByNameOrPrice;
        }

        var resultSql = String.format("SELECT * FROM Product %s",
                condition);

        return jdbcTemplate.query(resultSql, new BeanPropertyRowMapper<>(Product.class));
    }
}