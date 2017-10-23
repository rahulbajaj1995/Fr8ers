package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.AircraftModel;

/**
 * Created by bc9vq1 on 8/17/17.
 */

public class AvailableAircraftResponse extends BaseResponse {
    AircraftModel [] result;
    public AircraftModel[] getResult() {
        return result;
    }

    public void setResult(AircraftModel[] result) {
        this.result = result;
    }



}
