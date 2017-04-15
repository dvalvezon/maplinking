package com.maplinking.endpoint.entity;

import com.maplinking.service.entity.RouteInformation;

import java.math.BigDecimal;

public class RouteJson {

    private final Long totalTime;
    private final Double totalDistance;
    private final BigDecimal fuelCost;
    private final BigDecimal totalCost;
    private final PrettyRouteJson pretty;

    public RouteJson(RouteInformation routeInformation) {
        this.totalTime = routeInformation.getTotalTime();
        this.totalDistance = routeInformation.getTotalDistance();
        this.fuelCost = routeInformation.getFuelCost();
        this.totalCost = routeInformation.getTotalCost();
        this.pretty = new PrettyRouteJson(routeInformation);
    }

    public RouteJson(Long totalTime, Double totalDistance, BigDecimal fuelCost, BigDecimal totalCost) {
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.fuelCost = fuelCost;
        this.totalCost = totalCost;
        this.pretty = new PrettyRouteJson(totalTime, totalDistance, fuelCost, totalCost);
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

    public PrettyRouteJson getPretty() {
        return pretty;
    }

    @Override
    public String toString() {
        return "RouteJson{" +
                "totalTime=" + totalTime +
                ", totalDistance=" + totalDistance +
                ", fuelCost=" + fuelCost +
                ", totalCost=" + totalCost +
                ", pretty=" + pretty +
                '}';
    }
}
