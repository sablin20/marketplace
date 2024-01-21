package ru.sablin.app.marketplace.analytics;

import lombok.Builder;
import lombok.Value;
import java.math.BigDecimal;

@Value
@Builder
public class AnalyticsDtoSumMoney {
    String name;
    BigDecimal sumMoney;
}