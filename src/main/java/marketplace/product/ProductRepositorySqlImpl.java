package marketplace.product;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
//@Primary
@Repository
@RequiredArgsConstructor
public class ProductRepositorySqlImpl implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final static String SELECT_PRODUCT_SQL =  """
            SELECT pa.amount, p.id, p.name, p.category, p.price, p.brand, s.name AS store_name
            FROM Product_amount pa JOIN Product p ON pa.product_id = p.id
            JOIN Store s ON s.id = p.store_id %s
            """;

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
    public ProductDto buyProduct(Integer id, Integer clientId, Integer productId, Integer amount) {

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
    public Product findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Product WHERE id = ?",
                new BeanPropertyRowMapper<>(Product.class), id);
    }

    @Override
    public void removeById(Integer id) {
        jdbcTemplate.update("DELETE FROM Product WHERE id = ?", id);
    }

    @Override
    public List<ProductDto> findProducts(String category, String name, String brand, String sortedName, String sortedPrice, BigDecimal price1, BigDecimal price2) {

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

        var resultSql = String.format(SELECT_PRODUCT_SQL, condition);

        return jdbcTemplate.query(resultSql, (rs, rowNum) -> {
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