package marketplace.product;

import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductRepository repository;

    @PostMapping("/create")
    public void create(@RequestBody Product product) {
        repository.create(product);
    }

    @DeleteMapping("/remove")
    public void removeById(@RequestParam("id") String id) {
        repository.removeById(id);
    }

    @GetMapping("/findById")
    public Product findById(@RequestParam("id") String id) {
        return repository.findById(id);
    }

    @GetMapping("/findProducts")
    public List<Product> findProducts(@RequestParam(required = false, value = "category") String category,
                                      @RequestParam(required = false, value = "name") String name,
                                      @RequestParam(required = false, value = "brand") String brand,
                                      @RequestParam(required = false, value = "sortedName") String sortedName,
                                      @RequestParam(required = false, value = "sortedPrice") String sortedPrice) {
        return repository.findProducts(category, name, brand, sortedName, sortedPrice);
    }

    @GetMapping("/priceBetween")
    public List<Product> findByPriceInBetween(@RequestParam("firstPrice") BigDecimal firstPrice,
                                              @RequestParam("lastPrice") BigDecimal lastPrice) {
        return repository.findByPriceInBetween(firstPrice, lastPrice);
    }
}