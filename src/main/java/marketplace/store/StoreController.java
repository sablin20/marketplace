package marketplace.store;

import lombok.RequiredArgsConstructor;
import marketplace.product.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreRepository storeRepository;

    @GetMapping("/find")
    public Store findById(@RequestParam("id") Integer id) {
        return storeRepository.findById(id);
    }

    @PostMapping("/create")
    public Store create(@RequestBody Store store) {
        return storeRepository.create(store);
    }

    @PostMapping("/addProducts")
    public void addProductForSale(@RequestParam("id") Integer id,
                                  @RequestParam("storeId") Integer storeId,
                                  @RequestParam("productId") Integer productId) {
        storeRepository.addProductForSale(id, storeId, productId);
    }

    @DeleteMapping("/removeProduct")
    public void deleteProductSales(@RequestParam("storeId") Integer storeId,
                                     @RequestParam("productId") Integer productId) {
        storeRepository.deleteProductSales(storeId, productId);
    }

    @PutMapping("/updateProduct")
    public void updateProducts(@RequestParam("id") Integer id,
                               @RequestParam("storeId") Integer storeId,
                               @RequestBody Product product) {
        storeRepository.updateProducts(id, storeId, product);
    }
}