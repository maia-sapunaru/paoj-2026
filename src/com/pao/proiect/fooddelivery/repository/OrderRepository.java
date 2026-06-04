package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.model.*;
import com.pao.proiect.fooddelivery.model.Driver;
import com.pao.proiect.fooddelivery.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository implements Repository<Order, Integer> {
    private static OrderRepository instance;
    private final Connection connection;

    private OrderRepository() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static OrderRepository getInstance() {
        if (instance == null) {
            instance = new OrderRepository();
        }
        return instance;
    }

    @Override
    public void save(Order order) {
        String insertOrderSql = """
                INSERT INTO orders(id, client_id, restaurant_id, driver_id, status, created_at)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        String insertOrderItemSql = """
                INSERT INTO order_items(order_id, menu_item_id, quantity)
                VALUES (?, ?, ?)
                """;

        try {
            connection.setAutoCommit(false);

            try (PreparedStatement orderStatement = connection.prepareStatement(insertOrderSql)) {
                orderStatement.setInt(1, order.getId());
                orderStatement.setInt(2, order.getClient().getId());
                orderStatement.setInt(3, order.getRestaurant().getId());

                if (order.getDriver() == null) {
                    orderStatement.setNull(4, Types.INTEGER);
                } else {
                    orderStatement.setInt(4, order.getDriver().getId());
                }

                orderStatement.setString(5, order.getStatus().name());
                orderStatement.setString(6, order.getCreatedAt().toString());
                orderStatement.executeUpdate();
            }

            try (PreparedStatement itemStatement = connection.prepareStatement(insertOrderItemSql)) {
                for (OrderItem item : order.getItems()) {
                    itemStatement.setInt(1, order.getId());
                    itemStatement.setInt(2, item.getMenuItem().getId());
                    itemStatement.setInt(3, item.getQuantity());
                    itemStatement.executeUpdate();
                }
            }

            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException rollbackException) {
                throw new RuntimeException("Rollback failed", rollbackException);
            }
            throw new RuntimeException("Could not save order transaction", e);
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Could not reset auto commit", e);
            }
        }
    }

    @Override
    public Optional<Order> findById(Integer id) {
        String sql = """
                SELECT o.id, o.status, o.created_at,
                       c.id AS client_id, c.name AS client_name, c.phone AS client_phone,
                       c.city AS client_city, c.street AS client_street,
                       c.building AS client_building, c.details AS client_details,
                       r.id AS restaurant_id, r.name AS restaurant_name,
                       r.address AS restaurant_address, r.category AS restaurant_category,
                       d.id AS driver_id, d.name AS driver_name,
                       d.phone AS driver_phone, d.vehicle_number AS driver_vehicle,
                       d.available AS driver_available, d.rating AS driver_rating
                FROM orders o
                JOIN clients c ON o.client_id = c.id
                JOIN restaurants r ON o.restaurant_id = r.id
                LEFT JOIN drivers d ON o.driver_id = d.id
                WHERE o.id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapOrderWithoutItems(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not find order by id", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        String sql = """
                SELECT o.id, o.status, o.created_at,
                       c.id AS client_id, c.name AS client_name, c.phone AS client_phone,
                       c.city AS client_city, c.street AS client_street,
                       c.building AS client_building, c.details AS client_details,
                       r.id AS restaurant_id, r.name AS restaurant_name,
                       r.address AS restaurant_address, r.category AS restaurant_category,
                       d.id AS driver_id, d.name AS driver_name,
                       d.phone AS driver_phone, d.vehicle_number AS driver_vehicle,
                       d.available AS driver_available, d.rating AS driver_rating
                FROM orders o
                JOIN clients c ON o.client_id = c.id
                JOIN restaurants r ON o.restaurant_id = r.id
                LEFT JOIN drivers d ON o.driver_id = d.id
                ORDER BY o.id
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                orders.add(mapOrderWithoutItems(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not find all orders", e);
        }

        return orders;
    }

    private Order mapOrderWithoutItems(ResultSet rs) throws SQLException {
        DeliveryAddress address = new DeliveryAddress(
                rs.getString("client_city"),
                rs.getString("client_street"),
                rs.getString("client_building"),
                rs.getString("client_details")
        );

        Client client = new Client(
                rs.getInt("client_id"),
                rs.getString("client_name"),
                rs.getString("client_phone"),
                address
        );

        Restaurant restaurant = new Restaurant(
                rs.getInt("restaurant_id"),
                rs.getString("restaurant_name"),
                rs.getString("restaurant_address"),
                RestaurantCategory.valueOf(rs.getString("restaurant_category"))
        );

        Driver driver = null;
        int driverId = rs.getInt("driver_id");

        if (!rs.wasNull()) {
            driver = new Driver(
                    driverId,
                    rs.getString("driver_name"),
                    rs.getString("driver_phone"),
                    rs.getString("driver_vehicle"),
                    rs.getInt("driver_available") == 1,
                    rs.getDouble("driver_rating")
            );
        }

        Order order = new Order(
                rs.getInt("id"),
                client,
                restaurant
        );

        order.setDriver(driver);
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));

        return order;
    }

    @Override
    public void update(Order order) {
        String sql = """
                UPDATE orders
                SET driver_id = ?, status = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (order.getDriver() == null) {
                statement.setNull(1, Types.INTEGER);
            } else {
                statement.setInt(1, order.getDriver().getId());
            }

            statement.setString(2, order.getStatus().name());
            statement.setInt(3, order.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not update order", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM orders WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not delete order", e);
        }
    }

    public List<String> findOrdersWithClientAndRestaurant() {
        List<String> result = new ArrayList<>();

        String sql = """
                SELECT o.id, c.name AS client_name, r.name AS restaurant_name, o.status
                FROM orders o
                JOIN clients c ON o.client_id = c.id
                JOIN restaurants r ON o.restaurant_id = r.id
                ORDER BY o.id
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                result.add(
                        "Order " + rs.getInt("id") +
                                " | client=" + rs.getString("client_name") +
                                " | restaurant=" + rs.getString("restaurant_name") +
                                " | status=" + rs.getString("status")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not execute join query 1", e);
        }

        return result;
    }

    public List<String> findOrderItemsDetails() {
        List<String> result = new ArrayList<>();

        String sql = """
                SELECT o.id AS order_id, mi.name AS item_name, oi.quantity, r.name AS restaurant_name
                FROM order_items oi
                JOIN orders o ON oi.order_id = o.id
                JOIN menu_items mi ON oi.menu_item_id = mi.id
                JOIN restaurants r ON mi.restaurant_id = r.id
                ORDER BY o.id
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                result.add(
                        "Order " + rs.getInt("order_id") +
                                " | item=" + rs.getString("item_name") +
                                " | quantity=" + rs.getInt("quantity") +
                                " | restaurant=" + rs.getString("restaurant_name")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not execute join query 2", e);
        }

        return result;
    }

    public List<String> findActiveOrdersWithDriver() {
        List<String> result = new ArrayList<>();

        String sql = """
                SELECT o.id, c.name AS client_name, r.name AS restaurant_name,
                       d.name AS driver_name, o.status
                FROM orders o
                JOIN clients c ON o.client_id = c.id
                JOIN restaurants r ON o.restaurant_id = r.id
                LEFT JOIN drivers d ON o.driver_id = d.id
                WHERE o.status NOT IN ('DELIVERED', 'CANCELLED')
                ORDER BY o.id
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                String driverName = rs.getString("driver_name");
                if (driverName == null) {
                    driverName = "neatribuit";
                }

                result.add(
                        "Order " + rs.getInt("id") +
                                " | client=" + rs.getString("client_name") +
                                " | restaurant=" + rs.getString("restaurant_name") +
                                " | driver=" + driverName +
                                " | status=" + rs.getString("status")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not execute join query 3", e);
        }

        return result;
    }
}