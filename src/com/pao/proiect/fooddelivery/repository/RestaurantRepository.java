package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.model.Restaurant;
import com.pao.proiect.fooddelivery.model.RestaurantCategory;
import com.pao.proiect.fooddelivery.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantRepository implements Repository<Restaurant, Integer> {
    private static RestaurantRepository instance;
    private final Connection connection;

    private RestaurantRepository() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static RestaurantRepository getInstance() {
        if (instance == null) {
            instance = new RestaurantRepository();
        }
        return instance;
    }

    @Override
    public void save(Restaurant restaurant) {
        String sql = """
                INSERT INTO restaurants(id, name, address, category)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, restaurant.getId());
            statement.setString(2, restaurant.getName());
            statement.setString(3, restaurant.getAddress());
            statement.setString(4, restaurant.getCategory().name());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not save restaurant", e);
        }
    }

    @Override
    public Optional<Restaurant> findById(Integer id) {
        String sql = "SELECT * FROM restaurants WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToRestaurant(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not find restaurant", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Restaurant> findAll() {
        List<Restaurant> restaurants = new ArrayList<>();
        String sql = "SELECT * FROM restaurants";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                restaurants.add(mapToRestaurant(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not find restaurants", e);
        }

        return restaurants;
    }

    @Override
    public void update(Restaurant restaurant) {
        String sql = """
                UPDATE restaurants
                SET name = ?, address = ?, category = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, restaurant.getName());
            statement.setString(2, restaurant.getAddress());
            statement.setString(3, restaurant.getCategory().name());
            statement.setInt(4, restaurant.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not update restaurant", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM restaurants WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not delete restaurant", e);
        }
    }

    private Restaurant mapToRestaurant(ResultSet rs) throws SQLException {
        return new Restaurant(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("address"),
                RestaurantCategory.valueOf(rs.getString("category"))
        );
    }
}