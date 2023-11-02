package marketplace.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsDao analyticsDao;

    @GetMapping("/1")
    public List<ResponseDto> getNameStoreAndAmountCategory() {
        return analyticsDao.findNameStoreAndAmountCategoryForSales();
    }

    @GetMapping("/2")
    public List<ResponseDtoSumMoney> getNameClientAndMoneySpentOnBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findNameClientAndSumCashByBrand(brand);
    }

    @GetMapping("/3")
    public List<ResponseDto> getNameStoreByMaxSoldBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findNameStoreByMaxSalesByBrand(brand);
    }

    @GetMapping("/4/{clientId}")
    public ResponseDto getNameClientAndNumberOfPurchases(@PathVariable Integer clientId) {
        return analyticsDao.findNameClientAndCountIn3Category(clientId);
    }

    @GetMapping("/5")
    public List<ResponseDto> getBrandAndAmountCategory(@RequestParam("priceLimit") BigInteger priceLimit) {
        return analyticsDao.findNameBrandAndAmountCategoryByPrice(priceLimit);
    }

    @GetMapping("/6")
    public ResponseDtoAvgPrice getAvgPriceByCategory(@RequestParam("category") String category) {
        return analyticsDao.findAvgPriceByCategory(category);
    }

    @GetMapping("/7")
    public ResponseDtoMaxPrice getCategoryAndMaxPrice(@RequestParam("category") String category) {
        return analyticsDao.findCategoryAndMaxPrice(category);
    }

    @GetMapping("/8")
    public List<ResponseDtoMaxPrice> getProductByMaxPriceBrand() {
        return analyticsDao.findProductByMaxPriceBrand();
    }

    @GetMapping("/9")
    public List<ResponseDto> getStoreAndAmountProductsOneBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findStoreAndAmountProductsOneBrand(brand);
    }

    @GetMapping("/10/{storeId}")
    public List<ResponseDtoSumMoney> getClientAndAmountMoneyByStore(@PathVariable Integer storeId) {
        return analyticsDao.findClientAndCashByStore(storeId);
    }

    @GetMapping("/11")
    public List<ResponseDtoSumMoney> getStoreAndSumMoneyByBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findStoreAndSumCashByBrand(brand);
    }

}
