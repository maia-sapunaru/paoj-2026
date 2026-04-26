package com.pao.proiect.fooddelivery.model;

public final class DeliveryAddress{
    private final String city;
    private final String street;
    private final String building;
    private final String details;

    public DeliveryAddress(String city, String street, String building, String details){
        this.city = city;
        this.street = street;
        this.building = building;
        this.details = details;
    }

    public String getCity(){
        return city;
    }

    public String getStreet(){
        return street;
    }

    public String getBuilding(){
        return building;
    }

    public String getDetails(){
        return details;
    }

    @Override
    public String toString(){
        return city + ", " + street + ", nr. " + building +
                (details != null && !details.isEmpty() ? ", " + details : "");
    }
}