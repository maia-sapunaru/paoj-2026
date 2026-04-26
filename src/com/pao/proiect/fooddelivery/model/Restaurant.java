package com.pao.proiect.fooddelivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Restaurant{
    private int id;
    private String name;
    private String address;
    private RestaurantCategory category;
    private List<MenuItem> menu;

    public Restaurant(int id, String name, String address, RestaurantCategory category){
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = category;
        this.menu = new ArrayList<>();
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

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public RestaurantCategory getCategory(){
        return category;
    }

    public void setCategory(RestaurantCategory category){
        this.category = category;
    }

    public List<MenuItem> getMenu(){
        return menu;
    }

    public void setMenu(List<MenuItem> menu){
        this.menu = menu;
    }

    public void addMenuItem(MenuItem item){
        menu.add(item);
    }

    @Override
    public String toString(){
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", category=" + category +
                ", menuItems=" + menu.size() +
                '}';
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Restaurant)) return false;
        Restaurant that = (Restaurant) o;
        return id == that.id;
    }

    @Override
    public int hashCode(){
        return Objects.hash(id);
    }
}