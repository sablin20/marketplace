package marketplace.analytics;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AnalyticsDto {
    String name;
    Integer amount;
}