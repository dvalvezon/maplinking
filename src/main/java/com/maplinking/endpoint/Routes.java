package com.maplinking.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplinking.entity.LocationInfo;
import com.maplinking.entity.RouteInformation;
import com.maplinking.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class Routes {

    private final RouteService routeService;
    private final ObjectMapper objectMapper;

    @Autowired
    public Routes(RouteService routeService, ObjectMapper objectMapper) {
        this.routeService = routeService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "/routes", consumes = "application/json", produces = "application/json")
    public RouteInformation getRoute(@RequestBody List<LocationInfo> locationInfos,
                                     @RequestParam(defaultValue = "0.25") BigDecimal costPerKm) {
        return routeService.getRouteInformation(locationInfos, costPerKm);
    }

    @GetMapping(path = "/routes/{info}", produces = "application/json")
    public RouteInformation getRoute2(@PathVariable("info") String locationInfoJson,
                                      @RequestParam(defaultValue = "0.25") BigDecimal costPerKm) {
        System.out.println(locationInfoJson);
        try {
            List<LocationInfo> locationInfoList = objectMapper.readValue(locationInfoJson,
                    new TypeReference<List<LocationInfo>>() {
                    });
            return routeService.getRouteInformation(locationInfoList, costPerKm);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
