package marketplace.product;

import lombok.RequiredArgsConstructor;
import marketplace.customexception.ProductNotFoundException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryStreamImpl implements ProductRepository{

    private final JdbcTemplate jdbcTemplate;

    /**
     * @return специальный метод, который возвращает список товаров, по нему будем искать подходящие товары с помощью Stream API
     */
    public List<Product> requestSql() {
        return jdbcTemplate.query("SELECT * FROM Product", new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public Product create(Product product) {
        jdbcTemplate.update("INSERT INTO Product VALUES (?,?,?,?,?)",
                product.getName(), product.getPrice(), product.getCategory(), product.getBrand(), product.getAmount());
        return product;
    }

    @Override
    public void removeById(Integer id) {
        var product = requestSql().stream().
                filter(p -> p.getId().equals(id)).
                findFirst().
                orElseThrow(() -> new ProductNotFoundException(String.format("No Product by id = %s", id)));
        jdbcTemplate.update("DELETE FROM Product WHERE id = ?", product.getId());
    }

    @Override
    public Product findById(Integer id) {
        return requestSql().stream().
                filter(p -> p.getId().equals(id)).
                findFirst().
                orElseThrow(() -> new ProductNotFoundException(String.format("No Product by id = %s", id)));
    }


    public List<Product> findByCategory(String category, String sorted, String price) {
        if (sorted.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category)).
                    sorted((n1, n2) -> n2.getName().compareTo(n1.getName())).
                    collect(Collectors.toList());
        }

        if (sorted.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category)).
                    sorted().
                    collect(Collectors.toList());
        }

        if (price.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category)).
                    sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice())).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category)).
                    sorted((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())).
                    collect(Collectors.toList());
        }

        return requestSql().stream().
                filter(p -> p.getCategory().matches(category)).
                collect(Collectors.toList());
    }


    public List<Product> findByCategoryAndBrand(String category, String brand, String sorted, String price) {
        if (sorted.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getBrand().matches(brand)).
                    sorted((n1, n2) -> n2.getName().compareTo(n1.getName())).
                    collect(Collectors.toList());
        }

        if (sorted.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getBrand().matches(brand)).
                    sorted().
                    collect(Collectors.toList());
        }

        if (price.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getBrand().matches(brand)).
                    sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice())).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getBrand().matches(brand)).
                    sorted((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())).
                    collect(Collectors.toList());
        }

        return requestSql().stream().
                filter(p -> p.getCategory().matches(category) && p.getBrand().matches(brand)).
                collect(Collectors.toList());
    }


    public List<Product> findByCategoryAndName(String category, String name, String sorted, String price) {
        if (sorted.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getName().matches(name)).
                    sorted((n1, n2) -> n2.getName().compareTo(n1.getName())).
                    collect(Collectors.toList());
        }

        if (sorted.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getName().matches(name)).
                    sorted().
                    collect(Collectors.toList());
        }

        if (price.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getName().matches(name)).
                    sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice())).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getName().matches(name)).
                    sorted((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())).
                    collect(Collectors.toList());
        }

        return requestSql().stream().
                filter(p -> p.getCategory().matches(category) && p.getName().matches(name)).
                collect(Collectors.toList());
    }


    public List<Product> findByCategoryAndNameAndBrand(String category, String name, String brand, String sorted, String price) {
        if (sorted.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getName().matches(name) && p.getBrand().matches(brand)).
                    sorted((n1, n2) -> n2.getName().compareTo(n1.getName())).
                    collect(Collectors.toList());
        }

        if (sorted.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getName().matches(name) && p.getBrand().matches(brand)).
                    sorted().
                    collect(Collectors.toList());
        }

        if (price.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getName().matches(name) && p.getBrand().matches(brand)).
                    sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice())).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getName().matches(name) && p.getBrand().matches(brand)).
                    sorted((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())).
                    collect(Collectors.toList());
        }

        return requestSql().stream().
                filter(p -> p.getCategory().matches(category) && p.getName().matches(name) && p.getBrand().matches(brand)).
                collect(Collectors.toList());
    }

    public List<Product> findByBrand(String brand, String sorted, String price) {
        if (sorted.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand)).
                    sorted((n1, n2) -> n2.getName().compareTo(n1.getName())).
                    collect(Collectors.toList());
        }

        if (sorted.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand)).
                    sorted().
                    collect(Collectors.toList());
        }

        if (price.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand)).
                    sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice())).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand)).
                    sorted((p1, p2) -> p1.getPrice().compareTo(p2.getPrice())).
                    collect(Collectors.toList());
        }

        return requestSql().stream().filter(p -> p.getBrand().matches(brand)).collect(Collectors.toList());
    }


    public List<Product> findByName(String name, String sorted, String price) {
        if (sorted.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getName().matches(name)).
                    sorted((n1, n2) -> n2.getName().compareTo(n1.getName())).
                    collect(Collectors.toList());
        }

        if (sorted.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getName().matches(name)).
                    sorted().
                    collect(Collectors.toList());
        }

        if (price.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getName().matches(name)).
                    sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice())).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getName().matches(name)).
                    sorted().
                    collect(Collectors.toList());
        }

        return requestSql().stream().filter(p -> p.getName().matches(name)).collect(Collectors.toList());
    }


    public List<Product> findByBrandAndName(String brand, String name, String sorted, String price) {
        if (sorted.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand) && p.getName().matches(name)).
                    sorted((n1, n2) -> n2.getName().compareTo(n1.getName())).
                    collect(Collectors.toList());
        }

        if (sorted.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand) && p.getName().matches(name)).
                    sorted((p, p1) -> p.getPrice().compareTo(p1.getPrice())).
                    collect(Collectors.toList());
        }

        if (price.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand) && p.getName().matches(name)).
                    sorted((p1, p2) -> p2.getPrice().compareTo(p1.getPrice())).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand) && p.getName().matches(name)).
                    sorted((p, p1) -> p.getPrice().compareTo(p1.getPrice())).
                    collect(Collectors.toList());
        }

        return requestSql().stream().
                filter(p -> p.getBrand().matches(brand) && p.getName().matches(name)).
                collect(Collectors.toList());
    }

    @Override
    public List<Product> findProducts(String category, String name, String brand, String sortedName, String sortedPrice, BigDecimal priceFirst, BigDecimal priceLast) {

        if (category != null && name != null && brand != null) {
            return findByCategoryAndNameAndBrand(category, name, brand, sortedName, sortedPrice);
        }

        if (category != null && brand != null) {
            return findByCategoryAndBrand(category, brand, sortedName, sortedPrice);
        }

        if (category != null && name != null) {
            return findByCategoryAndName(category, name, sortedName, sortedPrice);
        }

        if (brand != null && name != null) {
            return findByBrandAndName (brand, name, sortedName, sortedPrice);
        }

        if (brand != null) {
            return findByBrand(brand, sortedName, sortedPrice);
        }

        if (name != null) {
            return findByName(name, sortedName, sortedPrice);
        }

        if (category != null) {
            return findByCategory(category, sortedName, sortedPrice);
        }

        return requestSql();
    }
}