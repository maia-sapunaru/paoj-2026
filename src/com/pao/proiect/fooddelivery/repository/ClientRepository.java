package com.pao.proiect.fooddelivery.repository;

import com.pao.proiect.fooddelivery.model.Client;
import com.pao.proiect.fooddelivery.model.DeliveryAddress;
import com.pao.proiect.fooddelivery.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClientRepository implements Repository<Client, Integer> {
    private static ClientRepository instance;
    private final Connection connection;

    private ClientRepository() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ClientRepository getInstance() {
        if (instance == null) {
            instance = new ClientRepository();
        }
        return instance;
    }

    @Override
    public void save(Client client) {
        String sql = """
                INSERT INTO clients(id, name, phone, city, street, building, details)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, client.getId());
            statement.setString(2, client.getName());
            statement.setString(3, client.getPhone());

            DeliveryAddress address = client.getAddress();
            statement.setString(4, address.getCity());
            statement.setString(5, address.getStreet());
            statement.setString(6, address.getBuilding());
            statement.setString(7, address.getDetails());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not save client", e);
        }
    }

    @Override
    public Optional<Client> findById(Integer id) {
        String sql = "SELECT * FROM clients WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapToClient(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not find client by id", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                clients.add(mapToClient(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Could not find all clients", e);
        }

        return clients;
    }

    @Override
    public void update(Client client) {
        String sql = """
                UPDATE clients
                SET name = ?, phone = ?, city = ?, street = ?, building = ?, details = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getPhone());

            DeliveryAddress address = client.getAddress();
            statement.setString(3, address.getCity());
            statement.setString(4, address.getStreet());
            statement.setString(5, address.getBuilding());
            statement.setString(6, address.getDetails());

            statement.setInt(7, client.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not update client", e);
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM clients WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not delete client", e);
        }
    }

    private Client mapToClient(ResultSet resultSet) throws SQLException {
        DeliveryAddress address = new DeliveryAddress(
                resultSet.getString("city"),
                resultSet.getString("street"),
                resultSet.getString("building"),
                resultSet.getString("details")
        );

        return new Client(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("phone"),
                address
        );
    }
}