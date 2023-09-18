package marketplace.store;

import marketplace.product.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/store")
public class StoreController {

    private StoreRepositorySqlImpl repositorySql;

    @GetMapping("/find")
    public Store findById(@RequestParam("id") String id) {
        return repositorySql.findById(id);
    }

    @PostMapping("/create")
    public String create(@RequestBody Store store) {
        return repositorySql.create(store);
    }

    @PostMapping("/addProducts")
    public String addProductForSale(@RequestParam("storeId") String storeId,
                                    @RequestParam("productId") String productId) {
        return repositorySql.addProductForSale(storeId, productId);
    }

    @DeleteMapping("/removeProduct")
    public String deleteProductSales(@RequestParam("storeId") String storeId,
                                     @RequestParam("productId") String productId) {
        return repositorySql.deleteProductSales(storeId, productId);
    }

    @PutMapping("/updateProduct")
    public String updateProducts(@RequestParam("storeId") String storeId,
                                 @RequestBody Product product) {
        return repositorySql.updateProducts(storeId, product);
    }

}
