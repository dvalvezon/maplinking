package com.maplinking.service;

import com.maplinking.maplink.Location;
import com.maplinking.maplink.MapLinkApi;
import com.maplinking.maplink.Route;
import com.maplinking.service.entity.LocationInfo;
import com.maplinking.service.entity.RouteInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
final class RouteServiceImpl implements RouteService {

    private final MapLinkApi api;

    @Autowired
    public RouteServiceImpl(MapLinkApi api) {
        this.api = api;
    }

    public RouteInformation getRouteInformation(List<LocationInfo> locationInfoList, BigDecimal costPerKm) {
        List<Location> locations = new ArrayList<>();

        System.out.println(locationInfoList); // TODO

        locationInfoList.forEach(locInfo -> locations.add(api.findAddress(locInfo.getAddress(), locInfo.getNumber(),
                locInfo.getCity(), locInfo.getState()).getLocation()));

        Route route = api.getRoute(locations);

        BigDecimal fuelCost = costPerKm.multiply(BigDecimal.valueOf(route.getSummary().getDistance() / 1000));

        return new RouteInformation(
                route.getSummary().getDuration(),
                route.getSummary().getDistance(),
                fuelCost,
                fuelCost.add(route.getSummary().getTotalTollFees()));
    }
}
