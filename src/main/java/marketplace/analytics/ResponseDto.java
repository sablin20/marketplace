package marketplace.analytics;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ResponseDto {
    String name;
    Integer amount;
}