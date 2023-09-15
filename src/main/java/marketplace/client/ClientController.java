package marketplace.client;

import lombok.Data;
import marketplace.customexception.CustomException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/client")
public class ClientController {

    private JdbcTemplate jdbcTemplate;
    private ClientRepository clientRepository;

    @PostMapping("/create")
    public void create(@RequestBody Client client) {
        clientRepository.save(client);
    }

    @DeleteMapping("/delete")
    public void remove(@RequestParam("id") String id) {
        clientRepository.deleteById(id);
    }

    @PostMapping("/update")
    public Client update(@RequestBody Client client) {
        clientRepository.save(client);
        return client;
    }

    @GetMapping("/")
    public Client findById(@RequestParam("id") String id) {
        return clientRepository.find(id).orElseThrow(() -> new CustomException(String.format("Client by id: %s not found", id)));
    }
}
