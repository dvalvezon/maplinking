package com.maplinking.service;

import com.maplinking.service.entity.LocationInfo;
import com.maplinking.service.entity.RouteInformation;

import java.math.BigDecimal;
import java.util.List;

public interface RouteService {

    RouteInformation getRouteInformation(List<LocationInfo> locationInfos, BigDecimal costPerKm);
}
