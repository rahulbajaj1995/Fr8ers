package com.truxapp.fbfire.Model;

/**
 * Created by bc9vq1 on 8/17/17.
 */

public class RequestAvailabilityModel {
    private String id;
    private String destination;
    private String payload;
    private String volume;
    private String availability_date;
    private String created_on;
    private Boolean is_active;
    private int dimension;
    private String sourceairport;
    private String destinationairport;
    private String clientregistration;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getAvailability_date() {
        return availability_date;
    }

    public void setAvailability_date(String availability_date) {
        this.availability_date = availability_date;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public Boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(Boolean is_active) {
        this.is_active = is_active;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public String getSourceairport() {
        return sourceairport;
    }

    public void setSourceairport(String sourceairport) {
        this.sourceairport = sourceairport;
    }

    public String getDestinationairport() {
        return destinationairport;
    }

    public void setDestinationairport(String destinationairport) {
        this.destinationairport = destinationairport;
    }

    public String getClientregistration() {
        return clientregistration;
    }

    public void setClientregistration(String clientregistration) {
        this.clientregistration = clientregistration;
    }
}
