package com.maplinking.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplinking.endpoint.entity.AddressJson;
import com.maplinking.endpoint.entity.RouteJson;
import com.maplinking.service.RouteService;
import com.maplinking.service.ServiceException;
import com.maplinking.service.entity.LocationInfo;
import com.maplinking.service.entity.RouteInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class Routes {

    private static final Logger LOGGER = LoggerFactory.getLogger(Routes.class);

    private final RouteService routeService;
    private final ObjectMapper objectMapper;
    private final Validator validator;

    @Autowired
    public Routes(RouteService routeService, ObjectMapper objectMapper, Validator validator) {
        this.routeService = routeService;
        this.objectMapper = objectMapper;
        this.validator = validator;
    }

    @PostMapping(path = "/routes", consumes = "application/json", produces = "application/json")
    public RouteJson routeByPost(@Valid @RequestBody List<AddressJson> addresses,
                                 @RequestParam(defaultValue = "0.25") BigDecimal costPerKm)
            throws ServiceException, ValidationException {
        LOGGER.info("Received POST /routes addresses={} costPerKm={}", addresses, costPerKm);
        validator.validate(addresses);

        RouteInformation routeInformation = routeService.getRouteInformation(toLocationInfo(addresses), costPerKm);

        RouteJson response = new RouteJson(routeInformation);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Completed POST /routes response={}", response);
        }

        return response;
    }

    @GetMapping(path = "/routes/{addresses}", produces = "application/json")
    public RouteJson routeByGet(@PathVariable("addresses") String addressesJson,
                                @RequestParam(defaultValue = "0.25") BigDecimal costPerKm)
            throws ServiceException, ValidationException {
        LOGGER.info("Received GET /routes addressesJson={} costPerKm={}", addressesJson, costPerKm);
        try {
            List<AddressJson> addresses = objectMapper.readValue(addressesJson,
                    new TypeReference<List<AddressJson>>() {
                    });

            validator.validate(addresses);

            RouteInformation routeInformation = routeService.getRouteInformation(toLocationInfo(addresses), costPerKm);

            RouteJson response = new RouteJson(routeInformation);

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Completed GET /routes response={}", response);
            }

            return response;
        } catch (IOException e) {
            throw new ValidationException("Invalid addresses Json format.", e);
        }
    }

    private List<LocationInfo> toLocationInfo(List<AddressJson> addressJsons) {
        return addressJsons.stream().map(AddressJson::toLocationInfo).collect(Collectors.toList());
    }
}
