package marketplace.product;

import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;

@Value
@Builder
public class ProductDto {
    Integer id;
    String name;
    BigDecimal price;
    String category;
    String brand;
    String storeId;
    String amount;
}
