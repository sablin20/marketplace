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
    public List<AnalyticsDto> getNameStoreAndAmountCategory() {
        return analyticsDao.findNameStoreAndAmountCategoryForSales();
    }

    @GetMapping("/2")
    public List<AnalyticsDtoSumMoney> getNameClientAndMoneySpentOnBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findNameClientAndSumCashByBrand(brand);
    }

    @GetMapping("/3")
    public List<AnalyticsDto> getNameStoreByMaxSoldBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findNameStoreByMaxSalesByBrand(brand);
    }

    @GetMapping("/4/{clientId}")
    public AnalyticsDto getNameClientAndNumberOfPurchases(@PathVariable Integer clientId) {
        return analyticsDao.findNameClientAndCountIn3Category(clientId);
    }

    @GetMapping("/5")
    public List<AnalyticsDto> getBrandAndAmountCategory(@RequestParam("priceLimit") BigInteger priceLimit) {
        return analyticsDao.findNameBrandAndAmountCategoryByPrice(priceLimit);
    }

    @GetMapping("/6")
    public AnalyticsDtoAvgPrice getAvgPriceByCategory(@RequestParam("category") String category) {
        return analyticsDao.findAvgPriceByCategory(category);
    }

    @GetMapping("/7")
    public AnalyticsDtoMaxPrice getCategoryAndMaxPrice(@RequestParam("category") String category) {
        return analyticsDao.findCategoryAndMaxPrice(category);
    }

    @GetMapping("/8")
    public List<AnalyticsDtoMaxPrice> getProductByMaxPriceBrand() {
        return analyticsDao.findProductByMaxPriceBrand();
    }

    @GetMapping("/9")
    public List<AnalyticsDto> getStoreAndAmountProductsOneBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findStoreAndAmountProductsOneBrand(brand);
    }

    @GetMapping("/10/{storeId}")
    public List<AnalyticsDtoSumMoney> getClientAndAmountMoneyByStore(@PathVariable Integer storeId) {
        return analyticsDao.findClientAndCashByStore(storeId);
    }

    @GetMapping("/11")
    public List<AnalyticsDtoSumMoney> getStoreAndSumMoneyByBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findStoreAndSumCashByBrand(brand);
    }

}
