package marketplace.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace.product.Product;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {

    private String id;
    private String name;
    private List<Product> products;
}