package com.maplinking.endpoint.entity;

import com.maplinking.service.entity.RouteInformation;

import java.math.BigDecimal;

public class PrettyRouteJson {

    private final String totalTime;
    private final String totalDistance;
    private final String fuelCost;
    private final String totalCost;

    public PrettyRouteJson(RouteInformation routeInformation) {
        this.totalTime = getPrettyTime(routeInformation.getTotalTime());
        this.totalDistance = getPrettyDistance(routeInformation.getTotalDistance());
        this.fuelCost = "$" + routeInformation.getFuelCost().setScale(2, BigDecimal.ROUND_HALF_UP);
        this.totalCost = "$" + routeInformation.getTotalCost().setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public PrettyRouteJson(Long totalTime, Double totalDistance, BigDecimal fuelCost, BigDecimal totalCost) {
        this.totalTime = getPrettyTime(totalTime);
        this.totalDistance = getPrettyDistance(totalDistance);
        this.fuelCost = "$" + fuelCost.setScale(2, BigDecimal.ROUND_HALF_UP);
        this.totalCost = "$" + totalCost.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    private String getPrettyDistance(Double totalDistance) {
        String[] distance = new BigDecimal(totalDistance / 1000).setScale(3, BigDecimal.ROUND_HALF_UP).toString()
                .split("\\.");
        return distance[0] + "km " + distance[1] + "m";
    }

    private String getPrettyTime(long seconds) {
        int hours = (int) seconds / 3600;
        int remainder = (int) seconds - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;
        return hours + "h" + mins + "m" + secs + "s";
    }

    public String getTotalTime() {
        return totalTime;
    }

    public String getTotalDistance() {
        return totalDistance;
    }

    public String getFuelCost() {
        return fuelCost;
    }

    public String getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "PrettyRouteJson{" +
                "totalTime='" + totalTime + '\'' +
                ", totalDistance='" + totalDistance + '\'' +
                ", fuelCost='" + fuelCost + '\'' +
                ", totalCost='" + totalCost + '\'' +
                '}';
    }
}
