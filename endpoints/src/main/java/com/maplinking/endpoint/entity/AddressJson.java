package com.maplinking.endpoint.entity;

import com.maplinking.endpoint.Validable;
import com.maplinking.endpoint.ValidationException;
import com.maplinking.service.entity.LocationInfo;

public final class AddressJson implements Validable {

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
        return "AddressJson{" +
                "address='" + address + '\'' +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    public LocationInfo toLocationInfo() {
        return new LocationInfo(address, number, city, state);
    }


    @Override
    public void validate() throws ValidationException {
        isNullOrEmpty(address, "address must not be empty");
        isNullOrEmpty(number, "number must not be empty");
        isNullOrEmpty(city, "city must not be empty");
        isNullOrEmpty(state, "state must not be empty");
    }
}
