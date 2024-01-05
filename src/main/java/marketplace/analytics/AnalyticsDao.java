package marketplace.analytics;

import org.springframework.stereotype.Repository;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@Repository
interface AnalyticsDao {

    //нужно получить всех продавцов(имя) и
    // количество категорий товаров которыми они барыжат
    // при учете, что категории PC и TV не должны учитываться в подсчете;
    List<AnalyticsDto> findNameStoreAndAmountCategoryForSales();

    //нужно получить пользователя(имя) и сколько он потратил на покупки какого-то бранда(бранд ты сам указываешь)
    List<AnalyticsDtoSumMoney> findNameClientAndSumCashByBrand(String brand);

    //нужно получить продавца(имя) который продал больше всего какого-то бранда
    List<AnalyticsDto> findNameStoreByMaxSalesByBrand(String brand);

    //нужно получить всех, покупателя(имя) и продавца(имя) у которого этот покупатель больше всего купил раз
    Map<String, String> findNameClientAndNameStoreByMax(Integer clientId);

    //нужно получить всех, покупателя(имя) и продавца(имя) у которого этот покупатель потратил больше всего
    Map<String, String> findNameClientAndNameStoreByMaxCash(Integer clientId);

    //показать покупателя(имя) и кол-во покупок, которые он сделал, если товары, которые он купил были минимум из 3х разных категорий
    AnalyticsDto findNameClientAndCountIn3Category(Integer clientId);

    //показать все бренды и колчичество категорий в котрых они представлены при учете что цена у товара должна быть не менее 5000
    List<AnalyticsDto> findNameBrandAndAmountCategoryByPrice(BigInteger priceLimit);

    //показать все показать среднюю цену товара для категории (категорию передать надо)
    AnalyticsDtoAvgPrice findAvgPriceByCategory(String category);

    //показать категорию и самую большую цену для этой категории
    AnalyticsDtoMaxPrice findCategoryAndMaxPrice(String category);

    //показать самый дорогой товар для каждого бренда
    List<AnalyticsDtoMaxPrice> findProductByMaxPriceBrand();

    //показать продавцов и количество товаров одного бренда (который будет задан)
    // но отображать нужно только тех у кого количество товара от 3 и больше
    List<AnalyticsDto> findStoreAndAmountProductsOneBrand(String brand);

    //показать продавца и денег на сколько он продал товара определенного бренда (передать бранд)
    List<AnalyticsDtoSumMoney> findStoreAndSumCashByBrand(String brand);

    //показать покупателей и сколько они потратили денег на покупки у продавца (продавца надо передать)
    List<AnalyticsDtoSumMoney> findClientAndCashByStore(Integer storeId);
}