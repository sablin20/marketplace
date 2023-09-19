package marketplace.client;

import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository {
    void create(Client client);
    void removeById(String id);
    Client findById(String id);
    void buyProduct(String clientId, String productId); // купить продукт
    void addToFavorites(String clientId, String productId); // добавить в избранное
    void removeFromFavorites(String clientId, String productId); // удалить из избранного
}
