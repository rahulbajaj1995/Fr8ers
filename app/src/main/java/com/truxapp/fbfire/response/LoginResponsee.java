package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.LoginModel;


public class LoginResponsee extends BaseResponse{
    private LoginModel result;

    public LoginModel getResult() {
        return result;
    }

    public void setResult(LoginModel result) {
        this.result = result;
    }
}
