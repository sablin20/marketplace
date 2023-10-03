package marketplace.product;

import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository {

    Product create(Product product);
    void removeById(Integer id);
    Product findById(Integer id);
    List<Product> findProducts(String category, String name, String brand, String sortedName, String sortedPrice, BigDecimal price1, BigDecimal price2);
}
