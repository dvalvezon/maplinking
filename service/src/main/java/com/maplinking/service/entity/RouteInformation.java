package com.maplinking.service.entity;

import java.math.BigDecimal;

public class RouteInformation {

    private final Long totalTime;
    private final Double totalDistance;
    private final BigDecimal fuelCost;
    private final BigDecimal totalCost;

    public RouteInformation(Long totalTime, Double totalDistance, BigDecimal fuelCost, BigDecimal totalCost) {
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.fuelCost = fuelCost;
        this.totalCost = totalCost;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public BigDecimal getFuelCost() {
        return fuelCost;
    }

    public BigDecimal getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "RouteInformation{" +
                "totalTime=" + totalTime +
                ", totalDistance=" + totalDistance +
                ", fuelCost=" + fuelCost +
                ", totalCost=" + totalCost +
                '}';
    }
}
