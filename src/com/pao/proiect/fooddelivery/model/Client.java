package com.pao.proiect.fooddelivery.model;

public class Client extends User{
    private DeliveryAddress address;

    public Client(int id, String name, String phone, DeliveryAddress address){
        super(id, name, phone);
        this.address = address;
    }

    @Override
    public String getRole(){
        return "CLIENT";
    }

    public DeliveryAddress getAddress(){
        return address;
    }

    public void setAddress(DeliveryAddress address){
        this.address = address;
    }

    @Override
    public String toString(){
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address=" + address +
                '}';
    }
}