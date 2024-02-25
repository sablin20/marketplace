package ru.sablin.app.marketplace.analytics;

import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
interface AnalyticsDao {

    //нужно получить всех продавцов(имя) и
    //количество категорий товаров которыми они торгуют
    //при учете, что категории PC и TV не должны учитываться в подсчете
    List<AnalyticsDto> findNameStoreAndAmountCategoryForSales();

    //нужно получить пользователя(имя) и сколько он потратил на покупки какого-то бранда(бранд ты сам указываешь)
    List<AnalyticsSumMoneyDto> findNameClientAndSumCashByBrand(String brand);

    //нужно получить продавца(имя) который продал больше всего какого-то бранда
    List<AnalyticsDto> findNameStoreByMaxSalesByBrand(String brand);

    //показать покупателя(имя) и кол-во покупок, которые он сделал, если товары,
    //были минимум из 3-х разных категорий
    AnalyticsDto findNameClientAndCountIn3Category(Integer clientId);

    //показать все бренды и количество категорий в которых они представлены при учете,
    // что цена у товара должна быть не менее 5000
    List<AnalyticsDto> findNameBrandAndAmountCategoryByPrice(BigDecimal priceLimit);

    //показать все показать среднюю цену товара для категории (категорию передать надо)
    AnalyticsAvgPriceDto findAvgPriceByCategory(String category);

    //показать категорию и самую большую цену для этой категории
    AnalyticsMaxPriceDto findCategoryAndMaxPrice(String category);

    //показать самый дорогой товар для каждого бренда
    List<AnalyticsMaxPriceDto> findProductByMaxPriceBrand();

    //показать продавцов и количество товаров одного бренда (который будет задан)
    // но отображать нужно только тех у кого количество товара от 3 и больше
    List<AnalyticsDto> findStoreAndAmountProductsOneBrand(String brand);

    //показать продавца и денег на сколько он продал товара определенного бренда (передать бранд)
    List<AnalyticsSumMoneyDto> findStoreAndSumCashByBrand(String brand);

    //показать покупателей и сколько они потратили денег на покупки у продавца (продавца надо передать)
    List<AnalyticsSumMoneyDto> findClientAndCashByStore(Integer storeId);
}