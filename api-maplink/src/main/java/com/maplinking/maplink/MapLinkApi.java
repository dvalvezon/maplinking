package com.maplinking.maplink;

import com.maplinking.maplink.entity.Position;
import com.maplinking.maplink.entity.RouteSummary;

import java.util.List;

public interface MapLinkApi {

    Position findPosition(String address, String number, String city, String state);


    RouteSummary getRouteSummary(List<Position> locationList);
}
