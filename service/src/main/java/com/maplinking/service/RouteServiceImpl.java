package com.maplinking.service;

import com.maplinking.maplink.MapLinkApi;
import com.maplinking.maplink.entity.Position;
import com.maplinking.maplink.entity.RouteSummary;
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
    RouteServiceImpl(MapLinkApi api) {
        this.api = api;
    }

    public RouteInformation getRouteInformation(List<LocationInfo> locationInfoList, BigDecimal costPerKm) {
        List<Position> positions = new ArrayList<>();

        System.out.println(locationInfoList); // TODO

        locationInfoList.forEach(locInfo -> positions.add(api.findPosition(locInfo.getAddress(), locInfo.getNumber(),
                locInfo.getCity(), locInfo.getState())));

        RouteSummary route = api.getRouteSummary(positions);

        BigDecimal fuelCost = costPerKm.multiply(BigDecimal.valueOf(route.getDistance() / 1000));

        return new RouteInformation(
                route.getDuration(),
                route.getDistance(),
                fuelCost,
                fuelCost.add(route.getTotalTollFeeAmount()));
    }
}
