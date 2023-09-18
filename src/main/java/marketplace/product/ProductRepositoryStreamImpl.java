package marketplace.product;

import marketplace.customexception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryStreamImpl implements ProductRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepositoryStreamImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return специальный метод, который возвращает список товаров, по нему будем искать подходящие товары с помощью Stream API
     */
    public List<Product> requestSql() {
        return jdbcTemplate.query("SELECT * FROM Product", new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public void create(Product product) {
        jdbcTemplate.update("INSERT INTO Product VALUES (?,?,?,?,?,?)",
                product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getBrand(), product.getAmount());
    }

    @Override
    public String removeById(String id) {
        var product = requestSql().stream().
                filter(p -> p.getId().equals(id)).
                findFirst().
                orElseThrow(() -> new CustomException(String.format("No Product by id = %s", id)));
        jdbcTemplate.update("DELETE FROM Product WHERE id = ?", product.getId());
        return "Product by id: " + product.getId() + " remove!";
    }

    @Override
    public Product findById(String id) {
        return requestSql().stream().
                filter(p -> p.getId().equals(id)).
                findFirst().
                orElseThrow(() -> new CustomException(String.format("No Product by id = %s", id)));
    }

    @Override
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
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.max(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category)).
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.min(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        return requestSql().stream().
                filter(p -> p.getCategory().matches(category)).
                collect(Collectors.toList());
    }

    @Override
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
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.max(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getBrand().matches(brand)).
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.min(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        return requestSql().stream().
                filter(p -> p.getCategory().matches(category) && p.getBrand().matches(brand)).
                collect(Collectors.toList());
    }

    @Override
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
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.max(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getName().matches(name)).
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.min(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        return requestSql().stream().
                filter(p -> p.getCategory().matches(category) && p.getName().matches(name)).
                collect(Collectors.toList());
    }

    @Override
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
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.max(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getCategory().matches(category) && p.getName().matches(name) && p.getBrand().matches(brand)).
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.min(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        return requestSql().stream().
                filter(p -> p.getCategory().matches(category) && p.getName().matches(name) && p.getBrand().matches(brand)).
                collect(Collectors.toList());
    }

    @Override
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
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.max(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand)).
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.min(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        return requestSql().stream().filter(p -> p.getBrand().matches(brand)).collect(Collectors.toList());
    }

    @Override
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
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.max(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getName().matches(name)).
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.min(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        return requestSql().stream().filter(p -> p.getName().matches(name)).collect(Collectors.toList());
    }

    @Override
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
                    sorted().
                    collect(Collectors.toList());
        }

        if (price.equals("DESC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand) && p.getName().matches(name)).
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.max(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        if (price.equals("ASC")) {
            return requestSql().stream().
                    filter(p -> p.getBrand().matches(brand) && p.getName().matches(name)).
                    sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return Math.min(o1.getPrice(), o2.getPrice());
                        }
                    }).
                    collect(Collectors.toList());
        }

        return requestSql().stream().
                filter(p -> p.getBrand().matches(brand) && p.getName().matches(name)).
                collect(Collectors.toList());
    }

    @Override
    public List<Product> findByPriceInBetween(int price1, int price2) {
        return requestSql().stream().
                filter(p -> p.getPrice() >= price1 && p.getPrice() <= price2).
                collect(Collectors.toList());
    }
}