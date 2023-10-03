package marketplace.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository repository;

    @PostMapping("/create")
    public void create(@RequestBody Product product) {
        repository.create(product);
    }

    @DeleteMapping("/remove")
    public void removeById(@RequestParam("id") Integer id) {
        repository.removeById(id);
    }

    @GetMapping("/findById")
    public Product findById(@RequestParam("id") Integer id) {
        return repository.findById(id);
    }

    @GetMapping("/findProducts")
    public List<Product> findProducts(@RequestParam(required = false, value = "category") String category,
                                      @RequestParam(required = false, value = "name") String name,
                                      @RequestParam(required = false, value = "brand") String brand,
                                      @RequestParam(required = false, value = "sortedName") String sortedName,
                                      @RequestParam(required = false, value = "sortedPrice") String sortedPrice,
                                      @RequestParam(required = false, value = "priceFirst") BigDecimal priceFirst,
                                      @RequestParam(required = false, value = "priceLast") BigDecimal priceLast) {
        return repository.findProducts(category, name, brand, sortedName, sortedPrice, priceFirst, priceLast);
    }
}