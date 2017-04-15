package com.maplinking.entity;

import com.maplinking.service.entity.LocationInfo;

public final class AddressJson {

    private String address;
    private String number;
    private String city;
    private String state;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Location{" +
                "address='" + address + '\'' +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public LocationInfo toLocationInfo() {
        return new LocationInfo(address, number, city, state);
    }
}
