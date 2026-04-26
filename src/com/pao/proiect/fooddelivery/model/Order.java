package com.pao.proiect.fooddelivery.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order{
    private int id;
    private Client client;
    private Restaurant restaurant;
    private List<OrderItem> items;
    private Driver driver;
    private OrderStatus status;
    private LocalDateTime createdAt;

    public Order(int id, Client client, Restaurant restaurant){
        this.id = id;
        this.client = client;
        this.restaurant = restaurant;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PLACED;
        this.createdAt = LocalDateTime.now();
    }

    public int getId(){
        return id;
    }

    public Client getClient(){
        return client;
    }

    public Restaurant getRestaurant(){
        return restaurant;
    }

    public List<OrderItem> getItems(){
        return items;
    }

    public Driver getDriver(){
        return driver;
    }

    public OrderStatus getStatus(){
        return status;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setDriver(Driver driver){
        this.driver = driver;
    }

    public void setStatus(OrderStatus status){
        this.status = status;
    }

    public void addItem(OrderItem item){
        items.add(item);
    }

    public double calculateTotal(){
        double total = 0.0;
        for (OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    @Override
    public String toString(){
        return "Order{" +
                "id=" + id +
                ", client=" + client.getName() +
                ", restaurant=" + restaurant.getName() +
                ", items=" + items +
                ", driver=" + (driver != null ? driver.getName() : "neatribuit") +
                ", status=" + status +
                ", total=" + calculateTotal() +
                ", createdAt=" + createdAt +
                '}';
    }
}