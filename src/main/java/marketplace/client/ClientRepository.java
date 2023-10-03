package marketplace.client;

import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository {
    Client create(Client client);
    void removeById(Integer id);
    Client findById(Integer id);
    void buyProduct(String clientId, String productId);
    void addToFavorites(String clientId, String productId);
    void removeFromFavorites(String clientId, String productId);
}
