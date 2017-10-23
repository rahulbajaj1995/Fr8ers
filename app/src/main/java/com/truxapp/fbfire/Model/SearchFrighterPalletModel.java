package com.truxapp.fbfire.Model;

import java.io.Serializable;

/**
 * Created by hp on 5/10/17.
 */

public class SearchFrighterPalletModel implements Serializable {
    private String palletType;
    private String palletValue;

    public String getPalletType() {
        return palletType;
    }

    public void setPalletType(String palletType) {
        this.palletType = palletType;
    }

    public String getPalletValue() {
        return palletValue;
    }

    public void setPalletValue(String palletValue) {
        this.palletValue = palletValue;
    }
}
