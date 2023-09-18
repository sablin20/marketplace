package marketplace.product;

import lombok.Data;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Data
@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductRepositorySqlImpl repositorySql;

    @PostMapping("/create")
    public void create(@RequestBody Product product) {
        repositorySql.create(product);
    }

    @DeleteMapping("/remove")
    public String removeById(@RequestParam("id") String id) {
        return repositorySql.removeById(id);
    }

    @GetMapping("/find")
    public Product findById(@RequestParam("/id") String id) {
        return repositorySql.findById(id);
    }

    @GetMapping("/category")
    public List<Product> findByCategory(@RequestParam("category") String category,
                                        @RequestParam(required = false, value = "sorted") String sorted,
                                        @RequestParam(required = false, value = "price") String price) {
        return repositorySql.findByCategory(category, sorted, price);
    }

    @GetMapping("/categoryAndBrand")
    public List<Product> findByCategoryAndBrand(@RequestParam("category") String category,
                                          @RequestParam("brand") String brand,
                                          @RequestParam(required = false, value = "sorted") String sorted,
                                          @RequestParam(required = false, value = "price") String price) {
        return repositorySql.findByCategoryAndBrand(category, brand, sorted, price);
    }

    @GetMapping("/categoryAndName")
    public List<Product> findByCategoryAndName(@RequestParam("category") String category,
                                         @RequestParam("name") String name,
                                         @RequestParam(required = false, value = "sorted") String sorted,
                                         @RequestParam(required = false, value = "price") String price) {
        return repositorySql.findByCategoryAndName(category, name, sorted, price);
    }

    @GetMapping("/categoryAndNameAndBrand")
    public List<Product> findByCategoryAndNameAndBrand(@RequestParam("category") String category,
                                                 @RequestParam("name") String name,
                                                 @RequestParam("brand") String brand,
                                                 @RequestParam(required = false, value = "sorted") String sorted,
                                                 @RequestParam(required = false, value = "price") String price) {
        return repositorySql.findByCategoryAndNameAndBrand(category, name, brand, sorted, price);
    }

    @GetMapping("/brand")
    public List<Product> findByBrand(@RequestParam("brand") String brand,
                               @RequestParam(required = false, value = "sorted") String sorted,
                               @RequestParam(required = false, value = "price") String price) {
        return repositorySql.findByBrand(brand, sorted, price);
    }

    @GetMapping("/brandAndName")
    public List<Product> findByBrandAndName(@RequestParam("brand") String brand,
                                      @RequestParam("name") String name,
                                      @RequestParam(required = false, value = "sorted") String sorted,
                                      @RequestParam(required = false, value = "price") String price) {
        return repositorySql.findByBrandAndName(brand, name, sorted, price);
    }

    @GetMapping("/name")
    public List<Product> findByName(@RequestParam("name") String name,
                              @RequestParam(required = false, value = "sorted") String sorted,
                              @RequestParam(required = false, value = "price") String price) {
        return repositorySql.findByName(name, sorted, price);
    }

    @GetMapping("/between")
    public List<Product> findByPriceInBetween(@RequestParam("price1") int price1,
                                        @RequestParam("price2") int price2) {
        return repositorySql.findByPriceInBetween(price1, price2);
    }
}