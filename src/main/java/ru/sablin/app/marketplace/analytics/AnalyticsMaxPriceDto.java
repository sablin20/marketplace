package ru.sablin.app.marketplace.analytics;

import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;

@Value
@Builder
public class AnalyticsMaxPriceDto {
    String name;
    BigDecimal maxPrice;
}