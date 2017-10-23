package com.truxapp.fbfire.Model;

import java.io.Serializable;

/**
 * Created by bc9vq1 on 8/9/17.
 */

public class FindLoadModel implements Serializable {
    private  String manufacturername;
    private  String modelname;
    private  String manufactureyear;
    private  int maxcargokg;
    private  int maxcargovol;
    private  int maxloadwt;
    private  String dooruppersize;
    private  String cargodeparture;
    private  String sourceairport;
    private  Double sourceairportlatitude;
    private  Double sourceairportlongitude;
    private  String sourceairportcity;
    private  String destinationairportname;
    private  Double destinationairportlatitude;
    private  Double destinationairportlongitude;
    private  String destinationairportcity;
    private  String planeavailablefrom;
    private  String planeavailableto;
    private  int availablepayload;
    private  String freighterid;

    public int getAvailablepayload() {
        return availablepayload;
    }

    public void setAvailablepayload(int availablepayload) {
        this.availablepayload = availablepayload;
    }

    public String getFreighterid() {
        return freighterid;
    }

    public void setFreighterid(String freighterid) {
        this.freighterid = freighterid;
    }


    public Double getDestinationairportlongitude() {
        return destinationairportlongitude;
    }
    public void setDestinationairportlongitude(Double destinationairportlongitude) {
        this.destinationairportlongitude = destinationairportlongitude;
    }

    public String getDestinationairportname() {
        return destinationairportname;
    }

    public void setDestinationairportname(String destinationairportname) {
        this.destinationairportname = destinationairportname;
    }

    public Double getSourceairportlatitude() {
        return sourceairportlatitude;
    }

    public void setSourceairportlatitude(Double sourceairportlatitude) {
        this.sourceairportlatitude = sourceairportlatitude;
    }

    public Double getSourceairportlongitude() {
        return sourceairportlongitude;
    }

    public void setSourceairportlongitude(Double sourceairportlongitude) {
        this.sourceairportlongitude = sourceairportlongitude;
    }

    public Double getDestinationairportlatitude() {
        return destinationairportlatitude;
    }

    public void setDestinationairportlatitude(Double destinationairportlatitude) {
        this.destinationairportlatitude = destinationairportlatitude;
    }

    public String getSourceairportcity() {
        return sourceairportcity;
    }

    public void setSourceairportcity(String sourceairportcity) {
        this.sourceairportcity = sourceairportcity;
    }

    public String getDestinationairportcity() {
        return destinationairportcity;
    }

    public void setDestinationairportcity(String destinationairportcity) {
        this.destinationairportcity = destinationairportcity;
    }

    public String getManufacturername() {
        return manufacturername;
    }

    public void setManufacturername(String manufacturername) {
        this.manufacturername = manufacturername;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getManufactureyear() {
        return manufactureyear;
    }

    public void setManufactureyear(String manufactureyear) {
        this.manufactureyear = manufactureyear;
    }

    public int getMaxcargokg() {
        return maxcargokg;
    }

    public void setMaxcargokg(int maxcargokg) {
        this.maxcargokg = maxcargokg;
    }

    public int getMaxcargovol() {
        return maxcargovol;
    }

    public void setMaxcargovol(int maxcargovol) {
        this.maxcargovol = maxcargovol;
    }

    public int getMaxloadwt() {
        return maxloadwt;
    }

    public void setMaxloadwt(int maxloadwt) {
        this.maxloadwt = maxloadwt;
    }

    public String getDooruppersize() {
        return dooruppersize;
    }

    public void setDooruppersize(String dooruppersize) {
        this.dooruppersize = dooruppersize;
    }

    public String getCargodeparture() {
        return cargodeparture;
    }

    public void setCargodeparture(String cargodeparture) {
        this.cargodeparture = cargodeparture;
    }

    public String getSourceairport() {
        return sourceairport;
    }

    public void setSourceairport(String sourceairport) {
        this.sourceairport = sourceairport;
    }

    public String getPlaneavailablefrom() {
        return planeavailablefrom;
    }

    public void setPlaneavailablefrom(String planeavailablefrom) {
        this.planeavailablefrom = planeavailablefrom;
    }

    public String getPlaneavailableto() {
        return planeavailableto;
    }

    public void setPlaneavailableto(String planeavailableto) {
        this.planeavailableto = planeavailableto;
    }
}
