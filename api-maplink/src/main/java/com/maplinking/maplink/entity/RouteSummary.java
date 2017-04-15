package com.maplinking.maplink.entity;

import java.math.BigDecimal;

public final class RouteSummary {

    private final Long duration;
    private final Double distance;
    private final BigDecimal totalTollFeeAmount;

    public RouteSummary(Long duration, Double distance, BigDecimal totalTollFeeAmount) {
        this.duration = duration;
        this.distance = distance;
        this.totalTollFeeAmount = totalTollFeeAmount;
    }

    public Long getDuration() {
        return duration;
    }

    public Double getDistance() {
        return distance;
    }

    public BigDecimal getTotalTollFeeAmount() {
        return totalTollFeeAmount;
    }

    @Override
    public String toString() {
        return "RouteSummary{" +
                "duration=" + duration +
                ", distance=" + distance +
                ", totalTollFeeAmount=" + totalTollFeeAmount +
                '}';
    }
}
