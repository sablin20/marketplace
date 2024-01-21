package ru.sablin.app.marketplace.product;

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
    public void create(@RequestParam("amount") Integer amount,
                       @RequestBody Product product) {
        repository.create(amount, product);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable("id") Integer id) {
        repository.removeById(id);
    }

    @GetMapping("/")
    public Product getById(@RequestParam("id") Integer id) {
        return repository.findById(id);
    }

    @GetMapping("/findProducts")
    public List<ProductDto> getProducts(@RequestParam(required = false, value = "category") String category,
                                        @RequestParam(required = false, value = "name") String name,
                                        @RequestParam(required = false, value = "brand") String brand,
                                        @RequestParam(required = false, value = "sortedName") String sortedName,
                                        @RequestParam(required = false, value = "sortedPrice") String sortedPrice,
                                        @RequestParam(required = false, value = "priceFirst") BigDecimal priceFirst,
                                        @RequestParam(required = false, value = "priceLast") BigDecimal priceLast) {
        return repository.findProducts(category, name, brand, sortedName, sortedPrice, priceFirst, priceLast);
    }

    @PutMapping("/")
    public void updateProducts(@RequestParam("storeId") Integer storeId,
                               @RequestBody Product product) {
        repository.updateProducts(storeId, product);
    }

    @PostMapping("/buy")
    public ProductDto buyProduct(@RequestParam("id") Integer id,
                                 @RequestParam("clientId") Integer clientId,
                                 @RequestParam("productId") Integer productId,
                                 @RequestParam("amount") Integer amount) {
        return repository.buyProduct(id, clientId, productId, amount);
    }
}