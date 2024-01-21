package ru.sablin.app.marketplace.product;

import lombok.RequiredArgsConstructor;
import ru.sablin.app.marketplace.customexception.ProductNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

//@Primary
//@Repository
@RequiredArgsConstructor
public class ProductRepositoryStreamImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    private final static String SELECT_PRODUCT_SQL = """
            SELECT pa.amount, p.id, p.name, p.category, p.price, p.brand, s.name AS store_name
            FROM Product_amount pa JOIN Product p ON pa.product_id = p.id
            JOIN Store s ON s.id = p.store_id
            """;

    /**
     * @return специальный метод, который возвращает список товаров, по нему будем искать подходящие товары с помощью Stream API
     */
    public List<Product> requestSql() {

        return jdbcTemplate.query("SELECT * FROM Product", new BeanPropertyRowMapper<>(Product.class));
    }

    @Override
    public void create(Integer amount, Product product) {
        System.out.println("================ STREAM***********************");
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
    public void removeById(Integer id) {
        var product = requestSql().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(String.format("No Product by id = %s", id)));
        jdbcTemplate.update("DELETE FROM Product WHERE id = ?", product.getId());
    }

    @Override
    public Product findById(Integer id) {
        return requestSql().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(String.format("No Product by id = %s", id)));
    }

    @Override
    public ProductDto buyProduct(Integer id,
                                 Integer clientId,
                                 Integer productId,
                                 Integer amount) {

        // запрашиваем данные по продукту
        var productDTO = jdbcTemplate.queryForObject("""
            SELECT pa.amount, p.id AS product_id, p.name AS product_name, p.category, p.price, p.brand, s.name AS store_name
            FROM Product_amount pa JOIN Product p ON pa.product_id = p.id
            JOIN Store s ON s.id = p.store_id
            WHERE p.id = ?
            """, (rs, rowNum) -> {

            int amountResult = rs.getInt("amount");

            //проверяем что количество запрошенного продукта удовлетворяет наличие и обновляем таблицу Product_amount
            if (amountResult >= amount) {
                jdbcTemplate.update("UPDATE Product_amount SET amount = ? WHERE product_id = ?",
                        amountResult - amount, productId);
            }

            var amountStr = switch (amountResult) {
                case 0 -> "Not available";
                case 1, 2, 3 -> String.format("There are few of this product left. Congratulations on your purchase %d %s!", amount, rs.getString("product_name"));
                case 4, 5, 6, 7 -> String.format("This product is enough. Congratulations on your purchase %d %s!", amount, rs.getString("product_name"));
                default -> String.format("There is a lot of this product in stock. Congratulations on your purchase %d %s!", amount, rs.getString("product_name"));
            };

            // формируем productDto который вернём клиенты после покупки
            return ProductDto.builder()
                    .id(rs.getInt("product_id"))
                    .name(rs.getString("product_name"))
                    .category(rs.getString("category"))
                    .brand(rs.getString("brand"))
                    .price(rs.getBigDecimal("price"))
                    .storeName(rs.getString("store_name"))
                    .amount(amountStr)
                    .build();
        }, productId);

        // добавляем новую запись в таблицу PurchaseHistory
        jdbcTemplate.update("INSERT INTO Purchase_history VALUES (?,?,?,?)",
                id, clientId, productId, amount);

        return productDTO;
    }

    @Override
    public void updateProducts(Integer storeId, Product product) {
        jdbcTemplate.update("""
                            UPDATE Product SET
                            id = ?,
                            name = ?,
                            price = ?,
                            category = ?,
                            brand = ?,
                            store_id = ?
                            WHERE store_id = ? AND id = ?""",
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            product.getCategory(),
                            product.getBrand(),
                            product.getStoreId(),
                            storeId, product.getId());
    }

    @Override
    public List<ProductDto> findProducts(String category,
                                         String name,
                                         String brand,
                                         String sortedName,
                                         String sortedPrice,
                                         BigDecimal priceFirst,
                                         BigDecimal priceLast) {

        var products = jdbcTemplate.query(SELECT_PRODUCT_SQL, (rs, rowNum) -> {
            int amountResult = rs.getInt("amount");
            var amountStr = switch (amountResult) {
                case 0 -> "Not available";
                case 1, 2, 3 -> "There are few of this product left";
                case 4, 5, 6, 7 -> "This product is enough";
                default -> "There is a lot of this product in stock";
            };
            return ProductDto.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("name"))
                    .category(rs.getString("category"))
                    .brand(rs.getString("brand"))
                    .price(rs.getBigDecimal("price"))
                    .storeName(rs.getString("store_name"))
                    .amount(amountStr)
                    .build();
        });

        if (name != null) {
            products = products.stream()
                    .filter(p -> p.getName().contains(name))
                    .toList();
        }

        if (category != null) {
            products = products.stream()
                    .filter(p -> p.getCategory().contains(category))
                    .toList();
        }

        if (brand != null) {
            products = products.stream()
                    .filter(p -> p.getBrand().contains(brand))
                    .toList();
        }

        if (sortedName != null && sortedName.equals("ASC")) {
            products = products.stream()
                    .sorted(Comparator.comparing(ProductDto::getName))
                    .toList();
        }
        if (sortedName != null && sortedName.equals("DESC")) {
            products = products.stream()
                    .sorted(Comparator.comparing(ProductDto::getName).reversed())
                    .toList();
        }
        if (sortedPrice != null && sortedPrice.equals("ASC")) {
            products = products.stream()
                    .sorted(Comparator.comparing(ProductDto::getPrice))
                    .toList();
        }
        if (sortedPrice != null && sortedPrice.equals("DESC")) {
            products = products.stream()
                    .sorted(Comparator.comparing(ProductDto::getPrice).reversed())
                    .toList();
        }

        if (priceFirst != null && priceLast != null) {
            products = products.stream()
                    .filter(p -> p.getPrice().intValue() >= priceFirst.intValue() && p.getPrice().intValue() < priceLast.intValue())
                    .toList();
        }

        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductDto p : products) {
            productDtoList.add(
                    ProductDto.builder()
                            .id(p.getId())
                            .name(p.getName())
                            .category(p.getCategory())
                            .brand(p.getBrand())
                            .price(p.getPrice())
                            .storeName(p.getStoreName())
                            .amount(p.getAmount())
                            .build());
        }

        return productDtoList;
    }
}