package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.ApprovedQuotationModel;

/**
 * Created by bc9vq1 on 9/7/17.
 */

public class ApprovedQuotationResponse extends BaseResponse {
    ApprovedQuotationModel[] result;

    public ApprovedQuotationModel[] getResult() {
        return result;
    }

    public void setResult(ApprovedQuotationModel[] result) {
        this.result = result;
    }

}
