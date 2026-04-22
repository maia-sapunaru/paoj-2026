package com.pao.project.fooddelivery.model;

public class Driver extends User{
    private String vehicleNumber;
    private boolean available;
    private double rating;

    public Driver(int id, String name, String phone, String vehicleNumber, boolean available, double rating){
        super(id, name, phone);
        this.vehicleNumber = vehicleNumber;
        this.available = available;
        this.rating = rating;
    }

    @Override
    public String getRole(){
        return "DRIVER";
    }

    public String getVehicleNumber(){
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber){
        this.vehicleNumber = vehicleNumber;
    }

    public boolean isAvailable(){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

    public double getRating(){
        return rating;
    }

    public void setRating(double rating){
        this.rating = rating;
    }

    @Override
    public String toString(){
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", vehicleNumber='" + vehicleNumber + '\'' +
                ", available=" + available +
                ", rating=" + rating +
                '}';
    }
}