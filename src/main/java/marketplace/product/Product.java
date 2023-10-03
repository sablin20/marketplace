package marketplace.product;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class Product {

    private Integer id;
    private String name;
    private BigDecimal price;
    private String category;
    private String brand;
    private int amount;

}
