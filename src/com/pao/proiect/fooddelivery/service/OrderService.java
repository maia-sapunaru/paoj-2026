package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.exception.NotFoundException;
import com.pao.proiect.fooddelivery.exception.MenuItemUnavailableException;
import com.pao.proiect.fooddelivery.model.Client;
import com.pao.proiect.fooddelivery.model.Driver;
import com.pao.proiect.fooddelivery.model.MenuItem;
import com.pao.proiect.fooddelivery.model.Order;
import com.pao.proiect.fooddelivery.model.OrderItem;
import com.pao.proiect.fooddelivery.model.OrderStatus;
import com.pao.proiect.fooddelivery.model.Restaurant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderService {
    private static final OrderService INSTANCE = new OrderService();

    private final List<Order> orders = new ArrayList<>();
    private final Map<Integer, Order> ordersById = new HashMap<>();

    private OrderService() {
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Comanda nu poate fi null.");
        }
        orders.add(order);
        ordersById.put(order.getId(), order);
    }

    public Order findOrderById(int id) {
        Order order = ordersById.get(id);
        if (order == null) {
            throw new NotFoundException("Comanda cu id " + id + " nu exista.");
        }
        return order;
    }

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public void deleteOrder(int id) {
        Order order = findOrderById(id);
        orders.remove(order);
        ordersById.remove(id);
    }

    public Order placeOrder(int orderId, Client client, Restaurant restaurant, List<OrderItem> items) {
        if (client == null) {
            throw new IllegalArgumentException("Clientul nu poate fi null.");
        }
        if (restaurant == null) {
            throw new IllegalArgumentException("Restaurantul nu poate fi null.");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Lista de produse nu poate fi goala.");
        }

        for (OrderItem item : items) {
            if (item == null || item.getMenuItem() == null) {
                throw new IllegalArgumentException("Item invalid in comanda.");
            }

            MenuItem menuItem = item.getMenuItem();

            if (!menuItem.isAvailable()) {
                throw new MenuItemUnavailableException(
                        "Produsul " + menuItem.getName() + " nu este disponibil."
                );
            }

            if (item.getQuantity() <= 0) {
                throw new IllegalArgumentException(
                        "Cantitatea pentru produsul " + menuItem.getName() + " trebuie sa fie pozitiva."
                );
            }

            if (menuItem.getRestaurantId() != restaurant.getId()) {
                throw new IllegalArgumentException(
                        "Produsul " + menuItem.getName() + " nu apartine restaurantului " + restaurant.getName() + "."
                );
            }
        }

        Order order = new Order(orderId, client, restaurant);
        for (OrderItem item : items) {
            order.addItem(item);
        }

        addOrder(order);
        return order;
    }

    public void assignDriver(int orderId, Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Soferul nu poate fi null.");
        }

        Order order = findOrderById(orderId);

        if (!driver.isAvailable()) {
            throw new IllegalArgumentException("Soferul " + driver.getName() + " nu este disponibil.");
        }

        order.setDriver(driver);
        order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
        driver.setAvailable(false);
    }

    public List<Order> getOrdersByClient(int clientId) {
        return orders.stream()
                .filter(order -> order.getClient().getId() == clientId)
                .collect(Collectors.toList());
    }

    public List<Order> getActiveOrdersSortedByTotal() {
        return orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.DELIVERED
                        && order.getStatus() != OrderStatus.CANCELLED)
                .sorted(Comparator.comparingDouble(Order::calculateTotal).reversed())
                .collect(Collectors.toList());
    }

    public void updateOrderStatus(int orderId, OrderStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("Statusul nu poate fi null.");
        }

        Order order = findOrderById(orderId);
        order.setStatus(status);

        if ((status == OrderStatus.DELIVERED || status == OrderStatus.CANCELLED)
                && order.getDriver() != null) {
            order.getDriver().setAvailable(true);
        }
    }
}