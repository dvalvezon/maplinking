package com.maplinking.entity;

public class RouteInformation {

    private final String totalTime;
    private final String totalDistance;
    private final String fuelCost;
    private final String totalCost;

    public RouteInformation(String totalTime, String totalDistance, String fuelCost, String totalCost) {
        this.totalTime = totalTime;
        this.totalDistance = totalDistance;
        this.fuelCost = fuelCost;
        this.totalCost = totalCost;
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
        return "RouteInformation{" +
                "totalTime='" + totalTime + '\'' +
                ", totalDistance='" + totalDistance + '\'' +
                ", fuelCost='" + fuelCost + '\'' +
                ", totalCost='" + totalCost + '\'' +
                '}';
    }
}
