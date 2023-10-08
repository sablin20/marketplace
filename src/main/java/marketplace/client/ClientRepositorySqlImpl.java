package marketplace.client;

import lombok.RequiredArgsConstructor;
import marketplace.customexception.ProductNotFoundException;
import marketplace.product.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClientRepositorySqlImpl implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Client create(Client client) {
        jdbcTemplate.update("INSERT INTO Client VALUES (?,?,?);",
                client.getId(),
                client.getName(),
                client.getLastName());
        return jdbcTemplate.queryForObject("SELECT * FROM Client WHERE id = ?;",
               new BeanPropertyRowMapper<>(Client.class), client.getId());
    }

    @Override
    public Client findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Client WHERE id = ?", new BeanPropertyRowMapper<>(Client.class), id);
    }

    @Override
    public void removeById(Integer id) {
        jdbcTemplate.update("DELETE FROM Client WHERE id = ?", id);
    }

    @Override
    public void buyProduct(Integer id, Integer clientId, Integer productId, Integer amount) {

        var amountProduct = jdbcTemplate.queryForObject(
                "SELECT amount FROM Product_amount WHERE product_id = ?",
                Integer.class, productId);

        if (amountProduct != null && amountProduct >= 0) {
            switch (amountProduct) {
                case 0 -> System.out.println("Not available");
                case 1, 2, 3 -> System.out.println("There are few of this product left");
                case 4, 5, 6, 7 -> System.out.println("This product is enough");
                default -> System.out.println("There is a lot of this product in stock");
            }
        } else {
            throw new ProductNotFoundException("Product not found");
        }

        var product = jdbcTemplate.queryForObject("SELECT * FROM Product WHERE id = ?",
                                                            new BeanPropertyRowMapper<>(Product.class), productId);
        // проверяем что продукт есть и его количество больше либо равно запрошенному на покупку количеству
        if (product != null && amountProduct >= amount) {
            jdbcTemplate.update("INSERT INTO PurchaseHistory VALUES (?,?,?,?)",
                    id,
                    clientId,
//                    product.getStoreId(),
                    productId,
                    amount);
        }

        // обновляем поле количество продукта в зависимости от количества купленного
        jdbcTemplate.update("UPDATE Product_amount SET amount = ? WHERE product_id = ?",
                amountProduct - amount,
                productId);
    }
}