package marketplace.client;

import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository {
    Client create(Client client);
    void removeById(Integer id);
    Client findById(Integer id);
    void buyProduct(Integer id, Integer clientId, Integer productId);
    void addToFavorites(Integer id, Integer clientId, Integer productId);
    void removeFromFavorites(Integer clientId, Integer productId);
}
