package marketplace.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace.product.Product;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private String id;

    private String name;

    private String last_name;

    List<Product> favorites;

    List<Product> history;
}