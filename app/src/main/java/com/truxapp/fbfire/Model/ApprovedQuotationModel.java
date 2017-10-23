package com.truxapp.fbfire.Model;

public class ApprovedQuotationModel  {
    private String loadavailabilityid;
    private String flightdetail;
    private Double payload;
    private Double volume;

    private String luggagetype;
    private boolean bidtype;
    private String sourceiata;
    private String destinationiata;
    private String availabilitydate;
    private Double loadprice;
    private String setSelection;
    PendingDimension [] dimesions;
    private String orderid;

    public String getChatcount() {
        return chatcount;
    }

    public void setChatcount(String chatcount) {
        this.chatcount = chatcount;
    }

    private String chatcount;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
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

    public String getLoadavailabilityid() {
        return loadavailabilityid;
    }

    public void setLoadavailabilityid(String loadavailabilityid) {
        this.loadavailabilityid = loadavailabilityid;
    }

    public String getFlightdetail() {
        return flightdetail;
    }

    public void setFlightdetail(String flightdetail) {
        this.flightdetail = flightdetail;
    }

    public Double getPayload() {
        return payload;
    }

    public void setPayload(Double payload) {
        this.payload = payload;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public String getLuggagetype() {
        return luggagetype;
    }

    public void setLuggagetype(String luggagetype) {
        this.luggagetype = luggagetype;
    }

    public boolean isBidtype() {
        return bidtype;
    }

    public void setBidtype(boolean bidtype) {
        this.bidtype = bidtype;
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

    public Double getLoadprice() {
        return loadprice;
    }

    public void setLoadprice(Double loadprice) {
        this.loadprice = loadprice;
    }
}
