package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.AirportModel;

import java.io.Serializable;

public class AirportResponse extends BaseResponse implements Serializable{
    AirportModel[] result;

    public AirportModel[] getAirports() {
        return result;
    }
}
