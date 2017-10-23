package com.truxapp.fbfire.Model;


public class PendingQuotationModel {
    private String loadavailabilityid;
    private String flightdetail;
    private double payload;
    private double volume;
    private String luggagetype;
    private String sourceiata;
    private String destinationiata;
    private String availabilitydate;
    private String setSelection;
    PendingDimension [] dimesions;
    private int target_price;
    private String orderid;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public int getTarget_price() {
        return target_price;
    }

    public void setTarget_price(int target_price) {
        this.target_price = target_price;
    }

    public String getLoadavailabilityid() {
        return loadavailabilityid;
    }

    public void setLoadavailabilityid(String loadavailabilityid) {
        this.loadavailabilityid = loadavailabilityid;
    }

    public PendingDimension[] getDimesions() {
        return dimesions;
    }

    public void setDimesions(PendingDimension[] dimesions) {
        this.dimesions = dimesions;
    }

    public String getSetSelection() {
        return setSelection;
    }

    public void setSetSelection(String setSelection) {
        this.setSelection = setSelection;
    }

    public String getSourceiata() {
        return sourceiata;
    }

    public void setSourceiata(String sourceiata) {
        this.sourceiata = sourceiata;
    }

    public String getDestinationiata() {
        return destinationiata;
    }

    public void setDestinationiata(String destinationiata) {
        this.destinationiata = destinationiata;
    }

    public String getAvailabilitydate() {
        return availabilitydate;
    }

    public void setAvailabilitydate(String availabilitydate) {
        this.availabilitydate = availabilitydate;
    }

    public double getPayload() {
        return payload;
    }

    public void setPayload(double payload) {
        this.payload = payload;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getLuggagetype() {
        return luggagetype;
    }

    public void setLuggagetype(String luggagetype) {
        this.luggagetype = luggagetype;
    }

    public String getFlightdetail() {
        return flightdetail;
    }

    public void setFlightdetail(String flightdetail) {
        this.flightdetail = flightdetail;
    }
}
