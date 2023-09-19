package marketplace.client;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import marketplace.customexception.CustomException;
import marketplace.product.ProductRepository;
import marketplace.store.Store;
import marketplace.store.StoreRepository;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Data
@Repository
@RequiredArgsConstructor
public class ClientRepositorySqlImpl implements ClientRepository {

    private final JdbcTemplate jdbcTemplate;
    private ProductRepository productRepository;
    private StoreRepository storeRepository;

    @Override
    public void create(Client client) {
        jdbcTemplate.update("INSERT INTO Client VALUES (?,?,?)",
                client.getId(), client.getName(), client.getLast_name());
    }

    @Override
    public Client findById(String id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Client WHERE id = ?", Client.class, id);
    }

    @Override
    public void removeById(String id) {
        jdbcTemplate.update("DELETE FROM Client WHERE id = ?",  id);
    }

    @Override
    public void buyProduct(String clientId, String productId) {
        var client = findById(clientId);
        var product = productRepository.findById(productId);
        var store = jdbcTemplate.query("SELECT * FROM Store", new BeanPropertyRowMapper<>(Store.class));
        store.stream().
                filter(s -> s.getProducts().contains(product)).
                findAny().
                orElseThrow(() -> new CustomException("No Store for this Product"));
        jdbcTemplate.update("UPDATE Client SET history = ? WHERE id = ?", product.getId(), client.getId());
    }

    @Override
    public void addToFavorites(String clientId, String productId) {
        var client = findById(clientId);
        var product = productRepository.findById(productId);
        jdbcTemplate.update("UPDATE Client SET favorites = ? WHERE id = ?", product.getId(), client.getId());
    }

    @Override
    public void removeFromFavorites(String clientId, String productId) {
        var client = findById(clientId);
        client.getFavorites().stream().
                filter(p -> p.getId().equals(productId)).
                findFirst().
                orElseThrow(() -> new CustomException(String.format("There is no such product id = %s in the favorites list", productId)));
        jdbcTemplate.update("DELETE favorites FROM Client WHERE favorites = ?", productId);
    }
}
