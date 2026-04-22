package com.pao.project.fooddelivery.model;

import java.util.Objects;

public class MenuItem implements Comparable<MenuItem>{
    private int id;
    private String name;
    private double price;
    private boolean available;
    private int restaurantId;

    public MenuItem(int id, String name, double price, boolean available, int restaurantId){
        this.id = id;
        this.name = name;
        this.price = price;
        this.available = available;
        this.restaurantId = restaurantId;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public boolean isAvailable(){
        return available;
    }

    public void setAvailable(boolean available){
        this.available = available;
    }

    public int getRestaurantId(){
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId){
        this.restaurantId = restaurantId;
    }

    @Override
    public int compareTo(MenuItem other){
        return this.name.compareToIgnoreCase(other.name);
    }

    @Override
    public String toString(){
        return "MenuItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", available=" + available +
                ", restaurantId=" + restaurantId +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return id == menuItem.id;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}