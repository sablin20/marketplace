package marketplace.product;

import lombok.Data;
import marketplace.store.Store;
import java.math.BigDecimal;
import java.util.List;

@Data
public class Product {

    private String id;
    private String name;
    private BigDecimal price;
    private String category;
    private String brand;
    private int amount;
    private List<Store> stores;
}
