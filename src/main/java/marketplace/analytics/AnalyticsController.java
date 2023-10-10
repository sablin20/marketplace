package marketplace.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsRepository repository;

    @GetMapping("/1")
    public List<ResponseEntityDto> findNameStoreAndAmountCategoryForSales() {
        return repository.findNameStoreAndAmountCategoryForSales();
    }

    @GetMapping("/2")
    public List<ResponseEntityDtoSumCash> findNameClientAndSumCashByBrand(@RequestParam("brand") String brand) {
        return repository.findNameClientAndSumCashByBrand(brand);
    }

    @GetMapping("/3")
    public List<ResponseEntityDto> findNameStoreByMaxSalesByBrand(@RequestParam("brand") String brand) {
        return repository.findNameStoreByMaxSalesByBrand(brand);
    }

    @GetMapping("/4/{clientId}")
    public ResponseEntityDto findNameClientAndCountIn3Category(@PathVariable Integer clientId) {
        return repository.findNameClientAndCountIn3Category(clientId);
    }

    @GetMapping("/5")
    public List<ResponseEntityDto> findNameBrandAndAmountCategoryByPrice() {
        return repository.findNameBrandAndAmountCategoryByPrice();
    }

    @GetMapping("/6")
    public ResponseEntityDtoAvgPrice findAvgPriceByCategory(@RequestParam("category") String category) {
        return repository.findAvgPriceByCategory(category);
    }

    @GetMapping("/7")
    public ResponseEntityDtoMaxPrice findCategoryAndMaxPrice(@RequestParam("category") String category) {
        return repository.findCategoryAndMaxPrice(category);
    }

    @GetMapping("/8")
    public List<ResponseEntityDtoMaxPrice> findProductByMaxPriceBrand() {
        return repository.findProductByMaxPriceBrand();
    }

    @GetMapping("/9")
    public List<ResponseEntityDto> findStoreAndAmountProductsOneBrand(@RequestParam("brand") String brand) {
        return repository.findStoreAndAmountProductsOneBrand(brand);
    }

    @GetMapping("/10/{storeId}")
    public List<ResponseEntityDtoSumCash> findClientAndCashByStore(@PathVariable Integer storeId) {
        return repository.findClientAndCashByStore(storeId);
    }

    @GetMapping("/11")
    public List<ResponseEntityDtoSumCash> findStoreAndSumCashByBrand(@RequestParam("brand") String brand) {
        return repository.findStoreAndSumCashByBrand(brand);
    }

}
