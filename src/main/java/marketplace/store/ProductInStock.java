package marketplace.store;

import lombok.Data;

@Data
public class ProductInStock {

    private Integer id;
    private Integer storeId;
    private Integer productId;

}
