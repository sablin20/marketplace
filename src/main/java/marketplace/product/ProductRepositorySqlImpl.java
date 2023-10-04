package marketplace.product;

import lombok.RequiredArgsConstructor;
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

    @Override
    public void create(Integer amount, Product product) {
        jdbcTemplate.update("INSERT INTO Product VALUES (?,?,?,?,?,?)",
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getBrand(),
                product.getStoreId());

        jdbcTemplate.update("INSERT INTO Product_amount VALUES(?,?)", product.getId(), amount);
    }

    @Override
    public Product findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Product WHERE id = ?",
                new BeanPropertyRowMapper<>(Product.class), id);
    }

    @Override
    public void removeById(Integer id) {
        jdbcTemplate.update("DELETE FROM Product WHERE id = ?", id);
    }

    @Override
    public List<Product> findProducts(String category, String name, String brand, String sortedName, String sortedPrice, BigDecimal price1, BigDecimal price2) {

        var condition = "";

        if (category != null) {
            condition += " WHERE ";
            condition += String.format("category LIKE '%%%s%%'", category);
        }

        if (name != null) {
            condition = getString(condition);
            condition += String.format("name LIKE '%%%s%%'",  name);
        }

        if (brand != null) {
            condition = getString(condition);
            condition += String.format("brand LIKE '%%%s%%'", brand);
        }

        if (price1 != null && price2 != null) {
            condition = getString(condition);
            condition += String.format("price BETWEEN %s AND %s", price1, price2);
        }

        if (sortedName != null && (sortedName.equals("ASC") || sortedName.equals("DESC"))) {
            condition += String.format(" ORDER BY name %s", sortedName);
        }

        if (sortedPrice != null && (sortedPrice.equals("ASC") || sortedPrice.equals("DESC"))) {
            condition += String.format(" ORDER BY price %s", sortedPrice);
        }

        var resultSql = String.format("SELECT * FROM Product %s", condition);

        return jdbcTemplate.query(resultSql, new BeanPropertyRowMapper<>(Product.class));
    }

    private String getString(String condition) {
        if (!condition.isEmpty()) {
            condition += " AND ";
        } else {
            condition += " WHERE ";
        }
        return condition;
    }
}