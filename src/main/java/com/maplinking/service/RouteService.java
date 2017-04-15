package com.maplinking.service;

import com.maplinking.entity.LocationInfo;
import com.maplinking.entity.RouteInformation;
import com.maplinking.external.api.maplink.Location;
import com.maplinking.external.api.maplink.MapLinkAPI;
import com.maplinking.external.api.maplink.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private final MapLinkAPI api;

    @Autowired
    public RouteService(MapLinkAPI api) {
        this.api = api;
    }

    public RouteInformation getRouteInformation(List<LocationInfo> locationInfos, BigDecimal costPerKm) {
        List<Location> locations = new ArrayList<>();
        System.out.println(locationInfos);
        locationInfos.forEach(locInfo -> locations.add(api.findAddress(locInfo.getAddress(), locInfo.getNumber(),
                locInfo.getCity(), locInfo.getState()).getLocation()));
        Route route = api.getRoute(locations);
        BigDecimal fuelCost = costPerKm.multiply(BigDecimal.valueOf(route.getSummary().getDistance() / 1000));
        return new RouteInformation(
                route.getSummary().getDuration() + "s",
                route.getSummary().getDistance() / 1000 + "km",
                fuelCost.toString(),
                fuelCost.add(route.getSummary().getTotalTollFees()).toString());
    }
}
