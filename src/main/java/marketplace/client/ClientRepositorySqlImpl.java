package marketplace.client;

import lombok.RequiredArgsConstructor;
import marketplace.store.ProductInStock;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class ClientRepositorySqlImpl implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Client create(Client client) {
        jdbcTemplate.update("INSERT INTO Client VALUES (?,?)", client.getName(), client.getLast_name());
        return jdbcTemplate.queryForObject("SELECT * FROM Client WHERE id = ? AND name = ? AND last_name = ?",
                Client.class, client.getId(), client.getName(), client.getLast_name());
    }

    @Override
    public Client findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Client WHERE id = ?", Client.class, id);
    }

    @Override
    public void removeById(String id) {
        jdbcTemplate.update("DELETE FROM Client WHERE id = ?", id);
    }

    @Override
    public void buyProduct(String clientId, String productId) {
        var amountProduct = jdbcTemplate.queryForObject(
                "SELECT amount FROM Product WHERE id = ?", BigDecimal.class, productId);

        assert amountProduct != null;
        if (amountProduct.intValue() == 0) {
            System.out.println("Not available");
        } else if (amountProduct.intValue() >= 1 && amountProduct.intValue() < 4) {
            System.out.println("There are few of this product left");
        } else if (amountProduct.intValue() > 3 && amountProduct.intValue() < 8) {
            System.out.println("This product is enough");
        } else if (amountProduct.intValue() > 7) {
            System.out.println("There is a lot of this product in stock");
        }

        var product = jdbcTemplate.queryForObject("SELECT * FROM ProductInStock WHERE productId = ?",
                                                                                    ProductInStock.class, productId);
        assert product != null;
        jdbcTemplate.update("INSERT INTO PurchaseHistory VALUES (?,?,?)", clientId, product.getStoreId(), productId);

    }

    @Override
    public void addToFavorites(String clientId, String productId) {
        jdbcTemplate.update("INSERT INTO Favorites VALUES (?,?)", clientId, productId);
    }

    @Override
    public void removeFromFavorites(String clientId, String productId) {
        jdbcTemplate.update("DELETE FROM Favorites WHERE clientId = ? AND productId = ?", clientId, productId);
    }
}
