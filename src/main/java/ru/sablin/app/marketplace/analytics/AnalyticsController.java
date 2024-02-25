package ru.sablin.app.marketplace.analytics;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsDao analyticsDao;

    @GetMapping("/nameStoreAndAmountCategory")
    public List<AnalyticsDto> getNameStoreAndAmountCategory() {
        return analyticsDao.findNameStoreAndAmountCategoryForSales();
    }

    @GetMapping("/nameClientAndMoneySpentOnBrand")
    public List<AnalyticsSumMoneyDto> getNameClientAndMoneySpentOnBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findNameClientAndSumCashByBrand(brand);
    }

    @GetMapping("/storeByMaxSoldBrand")
    public List<AnalyticsDto> getNameStoreByMaxSoldBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findNameStoreByMaxSalesByBrand(brand);
    }

    @GetMapping("/nameClientAndNumberOfPurchases/{clientId}")
    public AnalyticsDto getNameClientAndNumberOfPurchases(@PathVariable("clientId") Integer clientId) {
        return analyticsDao.findNameClientAndCountIn3Category(clientId);
    }

    @GetMapping("/brandAndAmountCategory")
    public List<AnalyticsDto> getBrandAndAmountCategory(@RequestParam("priceLimit") BigDecimal priceLimit) {
        return analyticsDao.findNameBrandAndAmountCategoryByPrice(priceLimit);
    }

    @GetMapping("/avgPriceByCategory")
    public AnalyticsAvgPriceDto getAvgPriceByCategory(@RequestParam("category") String category) {
        return analyticsDao.findAvgPriceByCategory(category);
    }

    @GetMapping("/categoryAndMaxPrice")
    public AnalyticsMaxPriceDto getCategoryAndMaxPrice(@RequestParam("category") String category) {
        return analyticsDao.findCategoryAndMaxPrice(category);
    }

    @GetMapping("/productMaxPriceBrand")
    public List<AnalyticsMaxPriceDto> getProductByMaxPriceBrand() {
        return analyticsDao.findProductByMaxPriceBrand();
    }

    @GetMapping("/storeAndAmountProductsOneBrand")
    public List<AnalyticsDto> getStoreAndAmountProductsOneBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findStoreAndAmountProductsOneBrand(brand);
    }

    @GetMapping("/clientAndAmountMoneyByStore/{storeId}")
    public List<AnalyticsSumMoneyDto> getClientAndAmountMoneyByStore(@PathVariable("storeId") Integer storeId) {
        return analyticsDao.findClientAndCashByStore(storeId);
    }

    @GetMapping("/storeAndSumMoneyByBrand")
    public List<AnalyticsSumMoneyDto> getStoreAndSumMoneyByBrand(@RequestParam("brand") String brand) {
        return analyticsDao.findStoreAndSumCashByBrand(brand);
    }
}