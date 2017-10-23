package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.SharedQuotationModel;

public class SharedQuotationResponse extends BaseResponse {
    SharedQuotationModel[] result;

    public SharedQuotationModel[] getResult() {
        return result;
    }

    public void setResult(SharedQuotationModel[] result) {
        this.result = result;
    }

}
