package marketplace.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientRepository clientRepository;

    @PostMapping("/create")
    public Client create(@RequestBody Client client) {
        return clientRepository.create(client);
    }

    @DeleteMapping("/delete")
    public void remove(@RequestParam("id") Integer id) {
        clientRepository.removeById(id);
    }

    @GetMapping("/find")
    public Client findById(@RequestParam("id") Integer id) {
        return clientRepository.findById(id);
    }

    @PostMapping("/buy")
    public void buyProduct(@RequestParam("id") Integer id,
                           @RequestParam("clientId") Integer clientId,
                           @RequestParam("productId") Integer productId) {
        clientRepository.buyProduct(id, clientId, productId);
    }

    @PostMapping("/addFavorites")
    public void addToFavorites(@RequestParam("id") Integer id,
                               @RequestParam("clientId") Integer clientId,
                               @RequestParam("productId") Integer productId) {
        clientRepository.addToFavorites(id, clientId, productId);
    }

    @DeleteMapping("/removeFavorites")
    public void removeFromFavorites(@RequestParam("clientId") Integer clientId,
                                      @RequestParam("productId") Integer productId) {
        clientRepository.removeFromFavorites(clientId, productId);
    }
}
