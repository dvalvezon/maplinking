package com.maplinking.maplink;

import java.util.List;

public interface MapLinkApi {

    Address findAddress(String address, String number, String city, String state);


    Route getRoute(List<Location> locationList);
}
