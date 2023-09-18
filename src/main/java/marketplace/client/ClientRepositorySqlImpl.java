package marketplace.client;

import marketplace.customexception.CustomException;
import marketplace.product.ProductRepositorySqlImpl;
import marketplace.store.Store;
import marketplace.store.StoreRepositorySqlImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClientRepositorySqlImpl implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;
    private ProductRepositorySqlImpl productRepositorySql;
    private StoreRepositorySqlImpl storeRepositorySql;

    @Autowired
    public ClientRepositorySqlImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String create(Client client) {
        jdbcTemplate.update("INSERT INTO Client VALUES (?,?,?)",
                client.getId(), client.getName(), client.getLast_name());

        return "Client " + client + " create! Good job!";
    }

    @Override
    public Client findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Client WHERE id = ?", Client.class, id);
    }

    @Override
    public String removeById(String id) {
        jdbcTemplate.update("DELETE FROM Client WHERE id = ?",  id);
        return "Client id: " + id + " remove! Good job!";
    }

    @Override
    public String buyProduct(String clientId, String productId) {
        var client = findById(clientId);
        var product = productRepositorySql.findById(productId);
        var store = jdbcTemplate.query("SELECT * FROM Store", new BeanPropertyRowMapper<>(Store.class));
        store.stream().
                filter(s -> s.getProducts().contains(product)).
                findAny().
                orElseThrow(() -> new CustomException("No Store for this Product"));
        jdbcTemplate.update("UPDATE Client SET history = ? WHERE id = ?", product.getId(), client.getId());
        return "Congratulations on your purchase product: !!!" + product;
    }

    @Override
    public String addToFavorites(String clientId, String productId) {
        var client = findById(clientId);
        var product = productRepositorySql.findById(productId);
        jdbcTemplate.update("UPDATE Client SET favorites = ? WHERE id = ?", product.getId(), client.getId());
        return String.format("You have added a product id: %s to your favorites", product.getId());
    }

    @Override
    public String removeFromFavorites(String clientId, String productId) {
        var client = findById(clientId);
        client.getFavorites().stream().
                filter(p -> p.getId().equals(productId)).
                findFirst().
                orElseThrow(() -> new CustomException(String.format("There is no such product id = %s in the favorites list", productId)));
        jdbcTemplate.update("DELETE favorites FROM Client WHERE favorites = ?", productId);
        return String.format("Product id = %s removed", productId);
    }
}
