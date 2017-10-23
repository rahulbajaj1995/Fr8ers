package com.truxapp.fbfire.Model;

import io.realm.RealmObject;

/**
 * Created by bc9vq1 on 8/17/17.
 */

public class MapModel extends RealmObject {
    private  String sourceairportlatitude;
    private  String sourceairportlongitude;

    public String getSourceairportlatitude() {
        return sourceairportlatitude;
    }

    public void setSourceairportlatitude(String sourceairportlatitude) {
        this.sourceairportlatitude = sourceairportlatitude;
    }

    public String getSourceairportlongitude() {
        return sourceairportlongitude;
    }

    public void setSourceairportlongitude(String sourceairportlongitude) {
        this.sourceairportlongitude = sourceairportlongitude;
    }
}
