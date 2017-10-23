package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.ChatHistoryModel;

import java.io.Serializable;

public class ChatHistoryResponse implements Serializable{
    private String errorCode;
    private ChatHistoryModel[] result;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public ChatHistoryModel[] getResult() {
        return result;
    }

    public void setResult(ChatHistoryModel[] result) {
        this.result = result;
    }
}
