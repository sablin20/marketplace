package marketplace.product;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Data
@RestController
@RequestMapping("/product")
public class ProductController {

    private JdbcTemplate jdbcTemplate;

    @GetMapping("/category")
    public Product findByCategory(@RequestParam("category") String category) {
        var product = jdbcTemplate.queryForObject(
                "SELECT category FROM Product WHERE category LIKE '%?%'",
                Product.class,  category.toUpperCase());
        return product;
    }

    @GetMapping("/categoryAndBrand")
    public Product findByCategoryAndBrand(@RequestParam("category") String category,
                                          @RequestParam("brand") String brand) {
        var product = jdbcTemplate.queryForObject(
                "SELECT category, brand FROM Product WHERE category LIKE '%?%' AND brand LIKE '%?%'",
                Product.class, category.toUpperCase(), brand.toUpperCase());
        return product;
    }

    @GetMapping("/categoryAndName")
    public Product findByCategoryAndName(@RequestParam("category") String category,
                                         @RequestParam("name") String name) {
        var product = jdbcTemplate.queryForObject(
                "SELECT category, name FROM Product WHERE category LIKE '%?%' AND name LIKE '%?%'",
                Product.class, category.toUpperCase(), name);
        return product;
    }

    @GetMapping("/categoryAndNameAndBrand")
    public Product findByCategoryAndNameAndBrand(@RequestParam("category") String category,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("brand") String brand) {
        var product = jdbcTemplate.queryForObject(
                "SELECT category, name, brand FROM Product WHERE category LIKE '%?%' AND name LIKE '%?%' AND brand LIKE '%?%'",
                Product.class, category.toUpperCase(), name, brand.toUpperCase());
        return product;
    }

    @GetMapping("/brand")
    public Product findByBrand(@RequestParam("brand") String brand) {
        var product = jdbcTemplate.queryForObject(
                "SELECT brand FROM Product WHERE brand LIKE '%?%'",
                Product.class, brand.toUpperCase());
        return product;
    }

    @GetMapping("/brandAndName")
    public Product findByBrandAndName(@RequestParam("brand") String brand,
                                      @RequestParam("name") String name) {
        var product = jdbcTemplate.queryForObject(
                "SELECT brand, name FROM Product WHERE brand LIKE '%?%' AND name LIKE '%?%'",
                Product.class, brand.toUpperCase(), name);
        return product;
    }

    @GetMapping("/name")
    public Product findByName(@RequestParam("name") String name) {
        var product = jdbcTemplate.queryForObject(
                "SELECT name FROM Product WHERE name LIKE '%?%'",
                Product.class, name);
        return product;
    }
}