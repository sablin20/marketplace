package marketplace.product;

import marketplace.customexception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProductRepositorySqlImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepositorySqlImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void validValue(List<Product> product) {
        if (product.size() == 0) {
            throw new CustomException("No Products by this parameters");
        }
    }

    @Override
    public void create(Product product) {
        jdbcTemplate.update("INSERT INTO Product VALUES (?,?,?,?,?,?)",
                product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getBrand(), product.getAmount());
    }

    @Override
    public Product findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Product WHERE id = ?", Product.class, id);
    }

    @Override
    public String removeById(String id) {
        jdbcTemplate.update("DELETE FROM Product WHERE id = ?", id);
        return "Product id: " + id + " remove! Good job!";
    }

    @Override
    public List<Product> findByCategory(String category, String sorted, String price) {
        var product = jdbcTemplate.query("SELECT * FROM Product WHERE category LIKE '%?%'",
               new BeanPropertyRowMapper<>(Product.class),  category.toUpperCase());

        validValue(product);

        var sqlName = String.format("SELECT * FROM Product WHERE category LIKE '%%?%%' ORDER BY name %s", sorted);
        var sqlPrice = String.format("SELECT * FROM Product WHERE category LIKE '%%?%%' ORDER BY price %s", price);

        if (sorted.equals("ASC") || sorted.equals("DESC")) {
            return jdbcTemplate.query(sqlName, new BeanPropertyRowMapper<>(Product.class),  category.toUpperCase());
        }

        if (price.equals("ASC") || price.equals("DESC")) {
            return jdbcTemplate.query(sqlPrice, new BeanPropertyRowMapper<>(Product.class),  category.toUpperCase());
        }

        return product;
    }

    @Override
    public List<Product> findByCategoryAndBrand(String category, String brand, String sorted, String price) {

        var product = jdbcTemplate.query("SELECT * FROM Product WHERE category LIKE '%?%' AND brand LIKE '%?%'",
                new BeanPropertyRowMapper<>(Product.class), category.toUpperCase(), brand.toUpperCase());

        validValue(product);

        var sqlName = String.format("SELECT * FROM Product WHERE category LIKE '%%?%%' AND brand LIKE '%%?%%' ORDER BY name %s", sorted);
        var sqlPrice = String.format("SELECT * FROM Product WHERE category LIKE '%%?%%' AND brand LIKE '%%?%%' ORDER BY price %s", price);

        if (sorted.equals("ASC") || sorted.equals("DESC")) {
            return jdbcTemplate.query(sqlName, new BeanPropertyRowMapper<>(Product.class),  category.toUpperCase());
        }

        if (price.equals("ASC") || price.equals("DESC")) {
            return jdbcTemplate.query(sqlPrice, new BeanPropertyRowMapper<>(Product.class),  category.toUpperCase());
        }

        return product;
    }

    @Override
    public List<Product> findByCategoryAndName(String category, String name, String sorted, String price) {

        var product = jdbcTemplate.query("SELECT * FROM Product WHERE category LIKE '%?%' AND name LIKE '%?%'",
                new BeanPropertyRowMapper<>(Product.class), category.toUpperCase(), name.toUpperCase());

        validValue(product);

        var sqlName = String.format("SELECT * FROM Product WHERE category LIKE '%%?%%' AND name LIKE '%%?%%' ORDER BY name %s", sorted);
        var sqlPrice = String.format("SELECT * FROM Product WHERE category LIKE '%%?%%' AND name LIKE '%%?%%' ORDER BY price %s", price);

        if (sorted.equals("ASC") || sorted.equals("DESC")) {
            return jdbcTemplate.query(sqlName, new BeanPropertyRowMapper<>(Product.class),  category.toUpperCase());
        }

        if (price.equals("ASC") || price.equals("DESC")) {
            return jdbcTemplate.query(sqlPrice, new BeanPropertyRowMapper<>(Product.class),  category.toUpperCase());
        }

        return product;
    }

    @Override
    public List<Product> findByCategoryAndNameAndBrand(String category, String name, String brand, String sorted, String price) {

        var product = jdbcTemplate.query("SELECT * FROM Product WHERE category LIKE '%?%' AND name LIKE '%?%' AND brand LIKE '%?%'",
                new BeanPropertyRowMapper<>(Product.class), category.toUpperCase(), name.toUpperCase(), brand.toUpperCase());

        validValue(product);

        var sqlName = String.format("SELECT * FROM Product WHERE category LIKE '%%?%%' AND name LIKE '%%?%%' AND brand LIKE '%%?%%' ORDER BY name %s", sorted);
        var sqlPrice = String.format("SELECT * FROM Product WHERE category LIKE '%%?%%' AND name LIKE '%%?%%' AND brand LIKE '%%?%%' ORDER BY price %s", price);

        if (sorted.equals("ASC") || sorted.equals("DESC")) {
            return jdbcTemplate.query(sqlName, new BeanPropertyRowMapper<>(Product.class),  category.toUpperCase());
        }

        if (price.equals("ASC") || price.equals("DESC")) {
            return jdbcTemplate.query(sqlPrice, new BeanPropertyRowMapper<>(Product.class),  category.toUpperCase());
        }

        return product;
    }

    @Override
    public List<Product> findByBrand(String brand, String sorted, String price) {

        var product = jdbcTemplate.query("SELECT * FROM Product WHERE brand LIKE '%?%'",
                new BeanPropertyRowMapper<>(Product.class), brand.toUpperCase());

        validValue(product);

        var sqlName = String.format("SELECT * FROM Product WHERE brand LIKE '%%?%%' ORDER BY name %s", sorted);
        var sqlPrice = String.format("SELECT * FROM Product WHERE brand LIKE '%%?%%' ORDER BY price %s", price);

        if (sorted.equals("ASC") || sorted.equals("DESC")) {
            return jdbcTemplate.query(sqlName, new BeanPropertyRowMapper<>(Product.class),  brand.toUpperCase());
        }

        if (price.equals("ASC") || price.equals("DESC")) {
            return jdbcTemplate.query(sqlPrice, new BeanPropertyRowMapper<>(Product.class),  brand.toUpperCase());
        }

        return product;
    }

    @Override
    public List<Product> findByBrandAndName(String brand, String name, String sorted, String price) {
        
        var product = jdbcTemplate.query("SELECT * FROM Product WHERE brand LIKE '%?%' AND name LIKE '%?%'",
                new BeanPropertyRowMapper<>(Product.class), brand.toUpperCase(), name);
        validValue(product);

        var sqlName = String.format("SELECT * FROM Product WHERE brand LIKE '%%?%%' name LIKE '%%?%%' ORDER BY name %s", sorted);
        var sqlPrice = String.format("SELECT * FROM Product WHERE brand LIKE '%%?%%' name LIKE '%%?%%' ORDER BY price %s", price);

        if (sorted.equals("ASC") || sorted.equals("DESC")) {
            return jdbcTemplate.query(sqlName, new BeanPropertyRowMapper<>(Product.class),  brand.toUpperCase(), name);
        }

        if (price.equals("ASC") || price.equals("DESC")) {
            return jdbcTemplate.query(sqlPrice, new BeanPropertyRowMapper<>(Product.class),  brand.toUpperCase(), name);
        }

        return product;
    }

    @Override
    public List<Product> findByName(String name, String sorted, String price) {

        var product = jdbcTemplate.query("SELECT * FROM Product WHERE name LIKE '%?%'",
                new BeanPropertyRowMapper<>(Product.class), name);

        validValue(product);

        var sqlName = String.format("SELECT * FROM Product WHERE name LIKE '%%?%%' ORDER BY name %s", sorted);
        var sqlPrice = String.format("SELECT * FROM Product WHERE name LIKE '%%?%%' ORDER BY price %s", price);

        if (sorted.equals("ASC") || sorted.equals("DESC")) {
            return jdbcTemplate.query(sqlName, new BeanPropertyRowMapper<>(Product.class), name);
        }

        if (price.equals("ASC") || price.equals("DESC")) {
            return jdbcTemplate.query(sqlPrice, new BeanPropertyRowMapper<>(Product.class), name);
        }

        return product;
    }

    @Override
    public List<Product> findByPriceInBetween(int price1, int price2) {
        var product = jdbcTemplate.query("SELECT * FROM Product WHERE price BETWEEN ? AND ?",
                new BeanPropertyRowMapper<>(Product.class), price1, price2);
        validValue(product);
        return product;
    }
}