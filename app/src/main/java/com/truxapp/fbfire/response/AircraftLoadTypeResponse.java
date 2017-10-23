package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.AircraftLoadTypeModel;

/**
 * Created by hp on 5/10/17.
 */

public class AircraftLoadTypeResponse extends  BaseResponse {
    AircraftLoadTypeModel [] result;

    public AircraftLoadTypeModel[] getResult() {
        return result;
    }

    public void setResult(AircraftLoadTypeModel[] result) {
        this.result = result;
    }
}
