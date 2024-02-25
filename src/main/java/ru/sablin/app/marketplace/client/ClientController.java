package ru.sablin.app.marketplace.client;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/clients")
public class ClientController {

    private final ClientRepository clientRepository;

    @PostMapping("/")
    public Client create(@RequestBody Client client) {
        return clientRepository.create(client);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") Integer id) {
        clientRepository.removeById(id);
    }

    @GetMapping("/")
    public Client getById(@RequestParam("id") Integer id) {
        return clientRepository.findById(id);
    }
}