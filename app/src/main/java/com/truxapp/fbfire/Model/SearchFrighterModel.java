package com.truxapp.fbfire.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchFrighterModel implements Serializable {
    private String manufacturername;
    private String modelname;
    private String freighterid;
    ArrayList<String> loadtype;
    SearchFrighterContainerModel [] containerdetail;
    SearchFrighterPalletModel [] palletdetail;
    private boolean alreadyRequest;
    private String targetprice;
    private String maxcargovol;
    private String recordid;
    private double availablepayload;

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getMaxcargovol() {
        return maxcargovol;
    }

    public void setMaxcargovol(String maxcargovol) {
        this.maxcargovol = maxcargovol;
    }

    public double getAvailablepayload() {
        return availablepayload;
    }

    public void setAvailablepayload(double availablepayload) {
        this.availablepayload = availablepayload;
    }

    public String getTargetprice() {
        return targetprice;
    }

    public void setTargetprice(String targetprice) {
        this.targetprice = targetprice;
    }

    public boolean isAlreadyRequest() {
        return alreadyRequest;
    }

    public void setAlreadyRequest(boolean alreadyRequest) {
        this.alreadyRequest = alreadyRequest;
    }

    public SearchFrighterContainerModel[] getContainerdetail() {
        return containerdetail;
    }

    public void setContainerdetail(SearchFrighterContainerModel[] containerdetail) {
        this.containerdetail = containerdetail;
    }

    public SearchFrighterPalletModel[] getPalletdetail() {
        return palletdetail;
    }

    public void setPalletdetail(SearchFrighterPalletModel[] palletdetail) {
        this.palletdetail = palletdetail;
    }

    public ArrayList<String> getLoadtype() {
        return loadtype;
    }

    public void setLoadtype(ArrayList<String> loadtype) {
        this.loadtype = loadtype;
    }

    public String getFreighterid() {
        return freighterid;
    }

    public void setFreighterid(String freighterid) {
        this.freighterid = freighterid;
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
}
