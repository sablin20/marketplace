package marketplace.store;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreRepository repository;

    @GetMapping("/")
    public Store getById(@RequestParam("id") Integer id) {
        return repository.findById(id);
    }

    @PostMapping("/create")
    public Store create(@RequestBody Store store) {
        return repository.create(store);
    }
}