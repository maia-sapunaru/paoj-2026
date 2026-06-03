package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.exception.NotFoundException;
import com.pao.proiect.fooddelivery.model.MenuItem;
import com.pao.proiect.fooddelivery.model.Restaurant;
import com.pao.proiect.fooddelivery.repository.MenuItemRepository;
import com.pao.proiect.fooddelivery.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class RestaurantService {
    private static final RestaurantService INSTANCE = new RestaurantService();

    private final List<Restaurant> restaurants = new ArrayList<>();
    private final Map<Integer, Restaurant> restaurantsById = new HashMap<>();

    private final RestaurantRepository restaurantRepository = RestaurantRepository.getInstance();
    private final MenuItemRepository menuItemRepository = MenuItemRepository.getInstance();
    private final AuditService auditService = AuditService.getInstance();

    private RestaurantService() {
    }

    public static RestaurantService getInstance() {
        return INSTANCE;
    }

    public void addRestaurant(Restaurant restaurant) {
        auditService.logAction("add_restaurant");

        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurantul nu poate fi null.");
        }

        restaurants.add(restaurant);
        restaurantsById.put(restaurant.getId(), restaurant);
        restaurantRepository.save(restaurant);
    }

    public Restaurant findRestaurantById(int id) {
        auditService.logAction("find_restaurant_by_id");

        Restaurant restaurant = restaurantsById.get(id);
        if (restaurant == null) {
            throw new NotFoundException("Restaurantul cu id " + id + " nu exista.");
        }

        return restaurant;
    }

    public List<Restaurant> getAllRestaurants() {
        auditService.logAction("get_all_restaurants");
        return new ArrayList<>(restaurants);
    }

    public void deleteRestaurant(int id) {
        auditService.logAction("delete_restaurant");

        Restaurant restaurant = findRestaurantById(id);
        restaurants.remove(restaurant);
        restaurantsById.remove(id);
        restaurantRepository.delete(id);
    }

    public void addMenuItemToRestaurant(int restaurantId, MenuItem item) {
        auditService.logAction("add_menu_item_to_restaurant");

        if (item == null) {
            throw new IllegalArgumentException("Produsul nu poate fi null.");
        }

        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.addMenuItem(item);
        menuItemRepository.save(item);
    }

    public List<MenuItem> searchMenuItemsByName(String name) {
        auditService.logAction("search_menu_items_by_name");

        List<MenuItem> result = new ArrayList<>();

        if (name == null || name.trim().isEmpty()) {
            return result;
        }

        for (Restaurant restaurant : restaurants) {
            for (MenuItem item : restaurant.getMenu()) {
                if (item.getName().toLowerCase().contains(name.toLowerCase())) {
                    result.add(item);
                }
            }
        }

        return result;
    }

    public TreeSet<MenuItem> getSortedMenuItems() {
        auditService.logAction("get_sorted_menu_items");

        TreeSet<MenuItem> sortedItems = new TreeSet<>();

        for (Restaurant restaurant : restaurants) {
            sortedItems.addAll(restaurant.getMenu());
        }

        return sortedItems;
    }
}