package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.PendingQuotationModel;

public class PendingQuotationResponse extends BaseResponse {
    PendingQuotationModel [] result;

    public PendingQuotationModel[] getResult() {
        return result;
    }

    public void setResult(PendingQuotationModel[] result) {
        this.result = result;
    }
}
