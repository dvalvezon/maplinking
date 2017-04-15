package com.maplinking.service.entity;

import java.util.Objects;

public final class LocationInfo {

    private final String address;
    private final String number;
    private final String city;
    private final String state;

    /**
     *
     * @param address address name
     * @param number address number
     * @param city address city
     * @param state address state
     * @throws NullPointerException if any of the parameters are null
     */
    public LocationInfo(String address, String number, String city, String state) throws NullPointerException {
        this.address = Objects.requireNonNull(address, "address must not be null");
        this.number = Objects.requireNonNull(number, "number must not be null");
        this.city = Objects.requireNonNull(city, "city must not be null");
        this.state = Objects.requireNonNull(state, "state must not be null");
    }

    public String getAddress() {
        return address;
    }

    public String getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return "LocationInfo{" +
                "address='" + address + '\'' +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
