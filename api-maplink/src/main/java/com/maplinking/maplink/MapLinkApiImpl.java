package com.maplinking.maplink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplinking.maplink.entity.Position;
import com.maplinking.maplink.entity.RouteSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Service
final class MapLinkApiImpl implements MapLinkApi {

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


    // TODO - Exception - Don't find address?
    @Override
    public Position findPosition(String address, String number, String city, String state) {
        String url = String.format(GEOLOCATION_API_ADDRESS, address, number, city, state);
        System.out.println(url);
        try {
            GeolocationResponse geolocation = objectMapper.readValue(restTemplate.getForObject(url, String.class),
                    GeolocationResponse.class);
            Address geoAddress = geolocation.getAddresses().get(0);
            return new Position(geoAddress.getLocation().getLatitude(), geoAddress.getLocation().getLongitude());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO - At least two waypoints
    @Override
    public RouteSummary getRouteSummary(List<Position> locationList) {
        String url = buildUrl(locationList);
        System.out.println(url);
        try {
            Route route = objectMapper.readValue(restTemplate.getForObject(url, String.class), RouteResponse.class)
                    .getRoutes().get(0);
            return new RouteSummary(route.getSummary().getDuration(), route.getSummary().getDistance(),
                    route.getSummary().getTotalTollFees());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildUrl(List<Position> locationList) {
        StringBuilder urlBuilder = new StringBuilder(ROUTES_API_ADDRESS);
        int i = 0;
        for (Position loc : locationList)
            urlBuilder.append(String.format(WAYPOINT_PATTERN, i++, loc.getLatitude(), loc.getLongitude()));
        return urlBuilder.toString();
    }
}
