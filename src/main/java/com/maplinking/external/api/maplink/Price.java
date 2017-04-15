package com.maplinking.external.api.maplink;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {

    private List<PriceAtHourRange> pricesAtHourRange;

    public List<PriceAtHourRange> getPricesAtHourRange() {
        return pricesAtHourRange;
    }

    public void setPricesAtHourRange(List<PriceAtHourRange> pricesAtHourRange) {
        this.pricesAtHourRange = pricesAtHourRange;
    }

    @Override
    public String toString() {
        return "Price{" +
                "pricesAtHourRange=" + pricesAtHourRange +
                '}';
    }

    public BigDecimal getDefaultPrice() {
        return this.pricesAtHourRange.size() > 0 ? this.pricesAtHourRange.get(0).getPrice() : BigDecimal.ZERO;
    }
}
