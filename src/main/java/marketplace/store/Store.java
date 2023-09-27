package marketplace.store;

import lombok.Data;
import marketplace.product.Product;
import java.util.List;

@Data
public class Store {

    private String id;
    private String name;
    private List<Product> products;
}