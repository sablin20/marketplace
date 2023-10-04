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

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Integer id) {
        clientRepository.removeById(id);
    }

    @GetMapping("/")
    public Client getById(@RequestParam("id") Integer id) {
        return clientRepository.findById(id);
    }

    @PostMapping("/buy")
    public void buyProduct(@RequestParam("id") Integer id,
                           @RequestParam("clientId") Integer clientId,
                           @RequestParam("productId") Integer productId,
                           @RequestParam("amount") Integer amount) {
        clientRepository.buyProduct(id, clientId, productId, amount);
    }
}
