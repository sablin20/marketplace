package marketplace.client;

import lombok.Data;
import marketplace.product.Product;
import java.util.List;

@Data
public class Client {

    private String id;

    private String name;

    private String last_name;

    List<Product> favorites;

    List<Product> history;
}