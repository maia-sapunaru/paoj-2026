package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.model.MenuItem;
import com.pao.proiect.fooddelivery.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MenuItemRepository implements Repository<MenuItem, Integer> {
    private static MenuItemRepository instance;
    private final Connection connection;

    private MenuItemRepository() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static MenuItemRepository getInstance() {
        if (instance == null) {
            instance = new MenuItemRepository();
        }
        return instance;
    }

    @Override
    public void save(MenuItem item) {
        String sql = """
                INSERT INTO menu_items(id, name, price, available, restaurant_id)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, item.getId());
            statement.setString(2, item.getName());
            statement.setDouble(3, item.getPrice());
            statement.setInt(4, item.isAvailable() ? 1 : 0);
            statement.setInt(5, item.getRestaurantId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not save menu item", e);
        }
    }

    @Override
    public Optional<MenuItem> findById(Integer id) {
        String sql = "SELECT * FROM menu_items WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToMenuItem(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not find menu item", e);
        }

        return Optional.empty();
    }

    @Override
    public List<MenuItem> findAll() {
        List<MenuItem> items = new ArrayList<>();
        String sql = "SELECT * FROM menu_items";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                items.add(mapToMenuItem(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not find menu items", e);
        }

        return items;
    }

    @Override
    public void update(MenuItem item) {
        String sql = """
                UPDATE menu_items
                SET name = ?, price = ?, available = ?, restaurant_id = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getPrice());
            statement.setInt(3, item.isAvailable() ? 1 : 0);
            statement.setInt(4, item.getRestaurantId());
            statement.setInt(5, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not update menu item", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM menu_items WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not delete menu item", e);
        }
    }

    private MenuItem mapToMenuItem(ResultSet rs) throws SQLException {
        return new MenuItem(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getDouble("price"),
                rs.getInt("available") == 1,
                rs.getInt("restaurant_id")
        );
    }
}