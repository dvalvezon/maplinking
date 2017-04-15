package com.maplinking.service;

import com.maplinking.maplink.MapLinkApi;
import com.maplinking.maplink.MapLinkException;
import com.maplinking.maplink.entity.Position;
import com.maplinking.maplink.entity.RouteSummary;
import com.maplinking.service.entity.LocationInfo;
import com.maplinking.service.entity.RouteInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
final class RouteServiceImpl implements RouteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteServiceImpl.class);

    private final MapLinkApi api;

    @Autowired
    RouteServiceImpl(MapLinkApi api) {
        this.api = api;
    }

    public RouteInformation getRouteInformation(List<LocationInfo> locationInfoList, BigDecimal costPerKm)
            throws ServiceException {
        LOGGER.info("getRouteInformation called. locationInfoList={} costPerKm={}", locationInfoList, costPerKm);
        try {
            List<Position> positions = new ArrayList<>();

            for (LocationInfo locInfo : locationInfoList)
                positions.add(api.findPosition(locInfo.getAddress(), locInfo.getNumber(),
                        locInfo.getCity(), locInfo.getState()));

            RouteSummary route = api.getRouteSummary(positions);

            BigDecimal fuelCost = costPerKm.multiply(BigDecimal.valueOf(route.getDistance() / 1000));

            RouteInformation routeInformation = new RouteInformation(
                    route.getDuration(),
                    route.getDistance(),
                    fuelCost,
                    fuelCost.add(route.getTotalTollFeeAmount()));

            LOGGER.info("getRouteInformation call completed. routeInformation={}", routeInformation);

            return routeInformation;
        } catch (MapLinkException e) {
            LOGGER.info("getRouteInformation call not completed. errorMessage={}", e.getMessage());
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
