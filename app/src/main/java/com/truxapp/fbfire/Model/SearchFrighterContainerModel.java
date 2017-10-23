package com.truxapp.fbfire.Model;

import java.io.Serializable;

/**
 * Created by hp on 5/10/17.
 */

public class SearchFrighterContainerModel implements Serializable {
    public String getContrainerType() {
        return contrainerType;
    }

    public void setContrainerType(String contrainerType) {
        this.contrainerType = contrainerType;
    }

    public String getContrainerValue() {
        return contrainerValue;
    }

    public void setContrainerValue(String contrainerValue) {
        this.contrainerValue = contrainerValue;
    }

    private String contrainerType;
    private String contrainerValue;
}
