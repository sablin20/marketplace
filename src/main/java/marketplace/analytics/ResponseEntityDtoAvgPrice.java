package marketplace.analytics;

import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;

@Value
@Builder
public class ResponseEntityDtoAvgPrice {
    String name;
    BigDecimal avgPrice;
}