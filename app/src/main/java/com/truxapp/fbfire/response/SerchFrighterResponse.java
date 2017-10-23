package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.SearchFrighterModel;

import java.io.Serializable;

/**
 * Created by hp on 28/9/17.
 */

public class SerchFrighterResponse implements Serializable {
    private String errorCode;
    private String errorMessage;
    SearchFrighterModel[] result;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public SearchFrighterModel[] getResult() {
        return result;
    }

    public void setResult(SearchFrighterModel[] result) {
        this.result = result;
    }
}
