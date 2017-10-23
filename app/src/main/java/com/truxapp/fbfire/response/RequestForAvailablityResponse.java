package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.RequestAvailabilityModel;

/**
 * Created by bc9vq1 on 8/17/17.
 */

public class RequestForAvailablityResponse extends BaseResponse {
    RequestAvailabilityModel [] result;

    public RequestAvailabilityModel[] getResult() {
        return result;
    }

    public void setResult(RequestAvailabilityModel[] result) {
        this.result = result;
    }


}
