package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.model.Driver;
import com.pao.proiect.fooddelivery.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverRepository implements Repository<Driver, Integer> {
    private static DriverRepository instance;
    private final Connection connection;

    private DriverRepository() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static DriverRepository getInstance() {
        if (instance == null) {
            instance = new DriverRepository();
        }
        return instance;
    }

    @Override
    public void save(Driver driver) {
        String sql = """
                INSERT INTO drivers(id, name, phone, vehicle_number, available, rating)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, driver.getId());
            statement.setString(2, driver.getName());
            statement.setString(3, driver.getPhone());
            statement.setString(4, driver.getVehicleNumber());
            statement.setInt(5, driver.isAvailable() ? 1 : 0);
            statement.setDouble(6, driver.getRating());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not save driver", e);
        }
    }

    @Override
    public Optional<Driver> findById(Integer id) {
        String sql = "SELECT * FROM drivers WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapToDriver(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not find driver", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Driver> findAll() {
        List<Driver> drivers = new ArrayList<>();
        String sql = "SELECT * FROM drivers";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                drivers.add(mapToDriver(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not find drivers", e);
        }

        return drivers;
    }

    @Override
    public void update(Driver driver) {
        String sql = """
                UPDATE drivers
                SET name = ?, phone = ?, vehicle_number = ?, available = ?, rating = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, driver.getName());
            statement.setString(2, driver.getPhone());
            statement.setString(3, driver.getVehicleNumber());
            statement.setInt(4, driver.isAvailable() ? 1 : 0);
            statement.setDouble(5, driver.getRating());
            statement.setInt(6, driver.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not update driver", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM drivers WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not delete driver", e);
        }
    }

    private Driver mapToDriver(ResultSet rs) throws SQLException {
        return new Driver(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("phone"),
                rs.getString("vehicle_number"),
                rs.getInt("available") == 1,
                rs.getDouble("rating")
        );
    }
}