package com.truxapp.fbfire.response;

import com.truxapp.fbfire.Model.FindLoadModel;

/**
 * Created by bc9vq1 on 8/9/17.
 */

public class FindLoadResponse extends BaseResponse {
    FindLoadModel[] result;

    public FindLoadModel[] getResult() {
        return result;
    }
    public void setResult(FindLoadModel[] result) {
        this.result = result;
    }
}
