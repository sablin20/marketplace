package marketplace.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String id;
    private String name;
    private int price;
    private String category;
    private String brand;
    private int amount;
}
