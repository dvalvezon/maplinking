package com.maplinking.maplink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplinking.maplink.entity.Position;
import com.maplinking.maplink.entity.RouteSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
final class MapLinkApiImpl implements MapLinkApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(MapLinkApiImpl.class);

    private static final String GEOLOCATION_API_ADDRESS = "https://api.maplink.com.br/v0/search?types=address,street" +
            "&token=z0vmywzpbCSLdJYl5mUk5m2jNGytNGt6NJu6NGU=&limit=1&q=%s, %s - %s - %s";

    private static final String ROUTES_API_ADDRESS = "https://api.maplink.com.br/v1/route?" +
            "token=z0vmywzpbCSLdJYl5mUk5m2jNGytNGt6NJu6NGU=&result=summary.distance,summary.duration,summary.tolls" +
            "&travel.vehicle=Car";

    private static final String WAYPOINT_PATTERN = "&waypoint.%s.latlng=%s,%s";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    MapLinkApiImpl(RestTemplateBuilder restTemplateBuilder, ObjectMapper objectMapper) {
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
    }

    @Override
    public Position findPosition(String address, String number, String city, String state) throws MapLinkException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("findPosition called. address={} number={} city={} state={}", address, number, city, state);
        String response = null;
        try {
            String url = String.format(GEOLOCATION_API_ADDRESS, address, number, city, state);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("url={}", url);

            response = restTemplate.getForObject(url, String.class);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("response={}", response);

            GeolocationResponse geolocation = objectMapper.readValue(response, GeolocationResponse.class);

            if (geolocation.getAddresses() == null || geolocation.getAddresses().size() < 1)
                throw new MapLinkException(String.format("No addresses found for provided information. address=%s " +
                        "number=%s city=%s state=%s", address, number, city, state));

            Address geoAddress = geolocation.getAddresses().get(0);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("findPosition call completed. geoAddress={}", geoAddress);

            return new Position(geoAddress.getLocation().getLatitude(), geoAddress.getLocation().getLongitude());
        } catch (IOException e) {
            throw new RuntimeException("Unable to parse response. response=" + response, e);
        }
    }

    @Override
    public RouteSummary getRouteSummary(List<Position> positions) throws MapLinkException {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("getRouteSummary called. positions={}", positions);

        if (positions == null || positions.size() < 2)
            throw new MapLinkException("At least 2 positions are necessary to calculate a Route.");
        else if (areAllPositionsTheSame(positions))
            throw new MapLinkException("At least 1 different position is necessary to calculate a Route.");

        String response = null;
        try {
            String url = buildUrl(positions);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("url={}", url);

            response = restTemplate.getForObject(url, String.class);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("response={}", response);

            RouteResponse routeResponse = objectMapper.readValue(response, RouteResponse.class);

            if (routeResponse.getRoutes() == null || routeResponse.getRoutes().size() < 1)
                throw new MapLinkException(String.format("No routes found for provided positions. positions=%s",
                        positions));

            Route route = routeResponse.getRoutes().get(0);
            if (LOGGER.isDebugEnabled())
                LOGGER.debug("getRouteSummary call completed. route={}", route);

            return new RouteSummary(route.getSummary().getDuration(), route.getSummary().getDistance(),
                    route.getSummary().getTotalTollFees());
        } catch (IOException e) {
            throw new RuntimeException("Unable to parse response. response=" + response, e);
        }
    }

    private String buildUrl(List<Position> locationList) {
        StringBuilder urlBuilder = new StringBuilder(ROUTES_API_ADDRESS);
        int i = 0;
        for (Position loc : locationList)
            urlBuilder.append(String.format(WAYPOINT_PATTERN, i++, loc.getLatitude(), loc.getLongitude()));
        return urlBuilder.toString();
    }

    private boolean areAllPositionsTheSame(List<Position> positions) {
        return positions.stream().distinct().count() == 1;
    }
}
