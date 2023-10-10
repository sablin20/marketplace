package marketplace.analytics;

import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;

@Value
@Builder
public class ResponseEntityDtoSumCash {
    String name;
    BigDecimal sumCash;
}