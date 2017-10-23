package com.truxapp.fbfire.Model;

import java.io.Serializable;

public class AirportModel implements Serializable{

    private String city;
    private String id;
    private String airport_name;
    private String latitude;
    private String longitude;

    public String getDisplay_text() {
        return display_text;
    }

    public void setDisplay_text(String display_text) {
        this.display_text = display_text;
    }

    private String display_text;

    public String getCity() {
        return city;
    }

    public String getId() {
        return id;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
