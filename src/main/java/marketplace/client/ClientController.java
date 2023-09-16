package marketplace.client;

import lombok.Data;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@Data
@RestController
@RequestMapping("/client")
public class ClientController {

    private JdbcTemplate jdbcTemplate;

    @PostMapping("/create")
    public String create(@RequestBody Client client) {
        jdbcTemplate.update("INSERT INTO Client VALUES (?,?,?)",
                client.getId(), client.getName(), client.getLast_name());
        return "Client " + client + " create! Good job!";
    }

    @DeleteMapping("/delete")
    public String remove(@RequestParam("id") String id) {
        jdbcTemplate.update("DELETE FROM Client WHERE id = ?",  id);
        return "Client id: " + id + " remove! Good job!";
    }

    @GetMapping("/find")
    public Client findById(@RequestParam("id") String id) {
        var client = jdbcTemplate.queryForObject("SELECT * FROM Client WHERE id = ?",
                Client.class, id);
        return client;
    }

}
