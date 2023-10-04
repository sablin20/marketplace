package marketplace.store;

import lombok.RequiredArgsConstructor;
import marketplace.product.Product;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/store")
public class StoreController {

    private final StoreRepository storeRepository;

    @GetMapping("/")
    public Store getById(@RequestParam("id") Integer id) {
        return storeRepository.findById(id);
    }

    @PostMapping("/create")
    public Store create(@RequestBody Store store) {
        return storeRepository.create(store);
    }

//    @DeleteMapping("/removeProduct")
//    public void deleteProductSales(@RequestParam("storeId") Integer storeId,
//                                     @RequestParam("productId") Integer productId) {
//        storeRepository.deleteProductSales(storeId, productId);
//    }

    @PutMapping("/")
    public void updateProducts(@RequestParam("storeId") Integer storeId,
                               @RequestBody Product product) {
        storeRepository.updateProducts(storeId, product);
    }
}