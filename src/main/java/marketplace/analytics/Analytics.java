package marketplace.analytics;

import marketplace.client.Client;
import marketplace.product.Product;
import marketplace.store.Store;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Repository
public interface Analytics {

    //нужно получить всех продавцов(имя) и
    // количество категорий товаров которыми они барыжат
    // при учете, что категории PC и TV не должны учитываться в подсчете;
    List<Store> findStoreNameAndAmountProductForSales();

    //нужно получить пользователя(имя) и сколько он потратил на покупки какого-то бранда(бранд ты сам указываешь)
    List<Client> findClientNameAndSumCashByBrand(String brand);

    //нужно получить продавца(имя) который продал больше всего какого-то бранда
    List<Store> findNameStoreByMaxSalesByBrand(String brand);

    //нужно получить всех, покупателя(имя) и продавца(имя) у которого этот покупатель больше всего купил раз
    Map<String, String> findNameClientAndNameStoreByMax(String clientId);

    //нужно получить всех, покупателя(имя) и продавца(имя) у которого этот покупатель потратил больше всего
    Map<String, String> findNameClientAndNameStoreByMaxCash(String clientId);

    //показать покупателя(имя) и кол-во покупок которые он сделал, если товары, которые он купил были минимум из 3х разных категорий
    Map<String, Integer> findNameClientAndCountIn3Category(String clientId);

    //показать все бренды и колчичество категорий в котрых они представлены при учете что цена у товара должна быть не менее 5000
    Map<String, Integer> findNameBrandAndCountCategoryByPrice();

    //показать все показать среднюю цену товара для категории (категорию передать надо)
    Product findAvgPriceByCategory(String category);

    //показать категорию и самую большую цену для этой категории
    Product findCategoryAndMaxPrice(String category);

    //показать самый дорогой товар для каждого бренда
    Product findProductByMaxPriceBrand();

    //показать продавцов и количество товаров одного бренда (который будет задан)
    // но отображать нужно только тех у кого количество товара от 3 и больше
    Store findStoreAndAmountProductsOneBrand(String brand);

    //показать продавца и денег на сколько он продал товара определеного бренда (передать бранд)
    Map<Store, BigDecimal> findStoreAndSumCashByBrand(String brand);

    //показать покупателей и сколько они потратили денег на покупки у продавца (продавца надо передать)
    List<Client> findClientAndCashByStore(String storeId);
}
