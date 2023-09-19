package marketplace.client;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    private ClientRepository clientRepository;

    @PostMapping("/create")
    public Client create(@RequestBody Client client) {
        return clientRepository.create(client);
    }

    @DeleteMapping("/delete")
    public void remove(@RequestParam("id") String id) {
        clientRepository.removeById(id);
    }

    @GetMapping("/find")
    public Client findById(@RequestParam("id") String id) {
        return clientRepository.findById(id);
    }

    @PostMapping("/buy")
    public void buyProduct(@RequestParam("clientId") String clientId,
                             @RequestParam("productId") String productId) {
        clientRepository.buyProduct(clientId, productId);
    }

    @PostMapping("/addFavorites")
    public void addToFavorites(@RequestParam("clientId") String clientId,
                                 @RequestParam("productId") String productId) {
        clientRepository.addToFavorites(clientId, productId);
    }

    @DeleteMapping("/removeFavorites")
    public void removeFromFavorites(@RequestParam("clientId") String clientId,
                                      @RequestParam("productId") String productId) {
        clientRepository.removeFromFavorites(clientId, productId);
    }

}
