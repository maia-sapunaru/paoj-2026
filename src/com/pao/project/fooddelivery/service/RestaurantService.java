package com.pao.project.fooddelivery.service;

import com.pao.project.fooddelivery.exception.EntityNotFoundException;
import com.pao.project.fooddelivery.model.MenuItem;
import com.pao.project.fooddelivery.model.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class RestaurantService{
    private static final RestaurantService INSTANCE = new RestaurantService();

    private final List<Restaurant> restaurants = new ArrayList<>();
    private final Map<Integer, Restaurant> restaurantsById = new HashMap<>();

    private RestaurantService(){
    }

    public static RestaurantService getInstance(){
        return INSTANCE;
    }

    public void addRestaurant(Restaurant restaurant){
        if (restaurant == null){
            throw new IllegalArgumentException("Restaurantul nu poate fi null.");
        }
        restaurants.add(restaurant);
        restaurantsById.put(restaurant.getId(), restaurant);
    }

    public Restaurant findRestaurantById(int id){
        Restaurant restaurant = restaurantsById.get(id);
        if (restaurant == null){
            throw new EntityNotFoundException("Restaurantul cu id " + id + " nu exista.");
        }
        return restaurant;
    }

    public List<Restaurant> getAllRestaurants(){
        return new ArrayList<>(restaurants);
    }

    public void deleteRestaurant(int id){
        Restaurant restaurant = findRestaurantById(id);
        restaurants.remove(restaurant);
        restaurantsById.remove(id);
    }

    public void addMenuItemToRestaurant(int restaurantId, MenuItem item){
        if (item == null){
            throw new IllegalArgumentException("Produsul nu poate fi null.");
        }
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.addMenuItem(item);
    }

    public List<MenuItem> searchMenuItemsByName(String name){
        List<MenuItem> result = new ArrayList<>();
        if (name == null || name.trim().isEmpty()){
            return result;
        }

        for (Restaurant restaurant : restaurants){
            for (MenuItem item : restaurant.getMenu()){
                if (item.getName().toLowerCase().contains(name.toLowerCase())){
                    result.add(item);
                }
            }
        }
        return result;
    }

    public TreeSet<MenuItem> getSortedMenuItems(){
        TreeSet<MenuItem> sortedItems = new TreeSet<>();
        for (Restaurant restaurant : restaurants){
            sortedItems.addAll(restaurant.getMenu());
        }
        return sortedItems;
    }
}