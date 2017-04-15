package com.maplinking.external.api.maplink;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TollFee {

    private List<Price> prices;

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "TollFee{" +
                "prices=" + prices +
                '}';
    }

    public BigDecimal getDetaultPrice() {
        return this.prices.size() > 0 ? this.prices.get(0).getDefaultPrice() : BigDecimal.ZERO;
    }
}
