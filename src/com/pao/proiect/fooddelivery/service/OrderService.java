package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.exception.MenuItemUnavailableException;
import com.pao.proiect.fooddelivery.exception.NotFoundException;
import com.pao.proiect.fooddelivery.model.Client;
import com.pao.proiect.fooddelivery.model.Driver;
import com.pao.proiect.fooddelivery.model.MenuItem;
import com.pao.proiect.fooddelivery.model.Order;
import com.pao.proiect.fooddelivery.model.OrderItem;
import com.pao.proiect.fooddelivery.model.OrderStatus;
import com.pao.proiect.fooddelivery.model.Restaurant;
import com.pao.proiect.fooddelivery.repository.DriverRepository;
import com.pao.proiect.fooddelivery.repository.OrderRepository;

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

    private final OrderRepository orderRepository = OrderRepository.getInstance();
    private final DriverRepository driverRepository = DriverRepository.getInstance();
    private final AuditService auditService = AuditService.getInstance();

    private OrderService() {
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }

    public void addOrder(Order order) {
        auditService.logAction("add_order");

        if (order == null) {
            throw new IllegalArgumentException("Comanda nu poate fi null.");
        }

        orders.add(order);
        ordersById.put(order.getId(), order);
        orderRepository.save(order);
    }

    public Order findOrderById(int id) {
        auditService.logAction("find_order_by_id");

        Order order = ordersById.get(id);
        if (order == null) {
            throw new NotFoundException("Comanda cu id " + id + " nu exista.");
        }

        return order;
    }

    public List<Order> getAllOrders() {
        auditService.logAction("get_all_orders");
        return new ArrayList<>(orders);
    }

    public void deleteOrder(int id) {
        auditService.logAction("delete_order");

        Order order = findOrderById(id);
        orders.remove(order);
        ordersById.remove(id);
        orderRepository.delete(id);
    }

    public Order placeOrder(int orderId, Client client, Restaurant restaurant, List<OrderItem> items) {
        auditService.logAction("place_order");

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

        orders.add(order);
        ordersById.put(order.getId(), order);
        orderRepository.save(order);

        return order;
    }

    public void assignDriver(int orderId, Driver driver) {
        auditService.logAction("assign_driver");

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

        orderRepository.update(order);
        driverRepository.update(driver);
    }

    public List<Order> getOrdersByClient(int clientId) {
        auditService.logAction("get_orders_by_client");

        return orders.stream()
                .filter(order -> order.getClient().getId() == clientId)
                .collect(Collectors.toList());
    }

    public List<Order> getActiveOrdersSortedByTotal() {
        auditService.logAction("get_active_orders_sorted_by_total");

        return orders.stream()
                .filter(order -> order.getStatus() != OrderStatus.DELIVERED
                        && order.getStatus() != OrderStatus.CANCELLED)
                .sorted(Comparator.comparingDouble(Order::calculateTotal).reversed())
                .collect(Collectors.toList());
    }

    public void updateOrderStatus(int orderId, OrderStatus status) {
        auditService.logAction("update_order_status");

        if (status == null) {
            throw new IllegalArgumentException("Statusul nu poate fi null.");
        }

        Order order = findOrderById(orderId);
        order.setStatus(status);

        if ((status == OrderStatus.DELIVERED || status == OrderStatus.CANCELLED)
                && order.getDriver() != null) {
            order.getDriver().setAvailable(true);
            driverRepository.update(order.getDriver());
        }

        orderRepository.update(order);
    }

    public List<String> getOrdersWithClientAndRestaurantFromDb() {
        auditService.logAction("join_orders_clients_restaurants");
        return orderRepository.findOrdersWithClientAndRestaurant();
    }

    public List<String> getOrderItemsDetailsFromDb() {
        auditService.logAction("join_order_items_details");
        return orderRepository.findOrderItemsDetails();
    }

    public List<String> getActiveOrdersWithDriverFromDb() {
        auditService.logAction("join_active_orders_with_driver");
        return orderRepository.findActiveOrdersWithDriver();
    }
}