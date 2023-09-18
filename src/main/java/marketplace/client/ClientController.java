package marketplace.client;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientRepositorySqlImpl repositorySql;

    @PostMapping("/create")
    public String create(@RequestBody Client client) {
        return repositorySql.create(client);
    }

    @DeleteMapping("/delete")
    public String remove(@RequestParam("id") String id) {
        return repositorySql.removeById(id);
    }

    @GetMapping("/find")
    public Client findById(@RequestParam("id") String id) {
        return repositorySql.findById(id);
    }

    @PostMapping("/buy")
    public String buyProduct(@RequestParam("clientId") String clientId,
                             @RequestParam("productId") String productId) {
        return repositorySql.buyProduct(clientId, productId);
    }

    @PostMapping("/addFavorites")
    public String addToFavorites(@RequestParam("clientId") String clientId,
                                 @RequestParam("productId") String productId) {
        return repositorySql.addToFavorites(clientId, productId);
    }

    @DeleteMapping("/removeFavorites")
    public String removeFromFavorites(@RequestParam("clientId") String clientId,
                                      @RequestParam("productId") String productId) {
        return repositorySql.removeFromFavorites(clientId, productId);
    }

}
