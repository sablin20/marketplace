package marketplace.client;

import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository {
    String create(Client client);
    String removeById(String id);
    Client findById(String id);
    String buyProduct(String clientId, String productId); // купить продукт
    String addToFavorites(String clientId, String productId); // добавить в избранное
    String removeFromFavorites(String clientId, String productId); // удалить из избранного
}
