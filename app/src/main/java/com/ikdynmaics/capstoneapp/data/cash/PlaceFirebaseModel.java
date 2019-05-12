package com.ikdynmaics.capstoneapp.data.cash;

public class PlaceFirebaseModel {

    public String placeName;
    public String placeAddress;
    private String icon;
    private Double rate;

    public PlaceFirebaseModel() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public PlaceFirebaseModel(String name, String address, String icon, Double rate) {
        this.placeName = name;
        this.placeAddress = address;
        this.icon = icon;
        this.rate = rate;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public String getIcon() {
        return icon;
    }

    public Double getRate() {
        return rate;
    }
}
