package com.truxapp.fbfire.Model;

import java.io.Serializable;

public class AircraftModel  implements Serializable{

    private Double latitude;
    private Double longitude;
    private String airportdetail;

    public String getAirportdetail() {
        return airportdetail;
    }

    public void setAirportdetail(String airportdetail) {
        this.airportdetail = airportdetail;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
