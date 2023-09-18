package marketplace.product;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository {

    void create(Product product);
    String removeById(String id);
    Product findById(String id);
    List<Product> findByCategory(String category, String sorted, String price);
    List<Product> findByCategoryAndBrand(String category, String brand, String sorted, String price);
    List<Product> findByCategoryAndName(String category, String name, String sorted, String price);
    List<Product> findByCategoryAndNameAndBrand(String category, String name, String brand, String sorted, String price);
    List<Product> findByBrand(String brand, String sorted, String price);
    List<Product> findByBrandAndName(String brand, String name, String sorted, String price);
    List<Product> findByName(String name, String sorted, String price);
    List<Product> findByPriceInBetween(int price1, int price2);
}
