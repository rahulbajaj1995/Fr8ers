package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.CountQuotationHistoryModel;

import java.io.Serializable;

/**
 * Created by hp on 14/10/17.
 */

public class CountQuotationHistoryResponse implements Serializable {
    private String errorCode;
    CountQuotationHistoryModel [] result;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public CountQuotationHistoryModel[] getResult() {
        return result;
    }

    public void setResult(CountQuotationHistoryModel[] result) {
        this.result = result;
    }
}
