package com.maplinking.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplinking.entity.AddressJson;
import com.maplinking.service.RouteService;
import com.maplinking.service.entity.LocationInfo;
import com.maplinking.service.entity.RouteInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Routes {

    // TODO - LOGS

    private final RouteService routeService;
    private final ObjectMapper objectMapper;

    @Autowired
    public Routes(RouteService routeService, ObjectMapper objectMapper) {
        this.routeService = routeService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "/routes", consumes = "application/json", produces = "application/json")
    public RouteInformation routeByPost(@RequestBody List<AddressJson> addresses,
                                        @RequestParam(defaultValue = "0.25") BigDecimal costPerKm) {
        return routeService.getRouteInformation(toLocationInfo(addresses), costPerKm);
    }

    @GetMapping(path = "/routes/{info}", produces = "application/json")
    public RouteInformation routeByGet(@PathVariable("info") String locationInfoJson,
                                       @RequestParam(defaultValue = "0.25") BigDecimal costPerKm) {
        System.out.println(locationInfoJson);
        try {
            List<AddressJson> addresses = objectMapper.readValue(locationInfoJson,
                    new TypeReference<List<AddressJson>>() {
                    });
            return routeService.getRouteInformation(toLocationInfo(addresses), costPerKm);
        } catch (IOException e) {
            // TODO - Exception Handling
            throw new RuntimeException(e);
        }
    }

    private List<LocationInfo> toLocationInfo(List<AddressJson> addressJsons) {
        return addressJsons.stream().map(AddressJson::toLocationInfo).collect(Collectors.toList());
    }
}
