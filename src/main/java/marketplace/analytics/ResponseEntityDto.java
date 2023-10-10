package marketplace.analytics;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResponseEntityDto {
    String name;
    Integer amount;
}