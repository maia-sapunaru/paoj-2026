package com.pao.proiect.fooddelivery.service;

import com.pao.proiect.fooddelivery.exception.NotFoundException;
import com.pao.proiect.fooddelivery.model.Client;
import com.pao.proiect.fooddelivery.model.Driver;
import com.pao.proiect.fooddelivery.repository.ClientRepository;
import com.pao.proiect.fooddelivery.repository.DriverRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private static final UserService INSTANCE = new UserService();

    private final List<Client> clients = new ArrayList<>();
    private final List<Driver> drivers = new ArrayList<>();
    private final Map<Integer, Client> clientsById = new HashMap<>();
    private final Map<Integer, Driver> driversById = new HashMap<>();

    private final ClientRepository clientRepository = ClientRepository.getInstance();
    private final DriverRepository driverRepository = DriverRepository.getInstance();
    private final AuditService auditService = AuditService.getInstance();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public void addClient(Client client) {
        auditService.logAction("add_client");

        if (client == null) {
            throw new IllegalArgumentException("Clientul nu poate fi null.");
        }

        clients.add(client);
        clientsById.put(client.getId(), client);
        clientRepository.save(client);
    }

    public void addDriver(Driver driver) {
        auditService.logAction("add_driver");

        if (driver == null) {
            throw new IllegalArgumentException("Soferul nu poate fi null.");
        }

        drivers.add(driver);
        driversById.put(driver.getId(), driver);
        driverRepository.save(driver);
    }

    public Client findClientById(int id) {
        auditService.logAction("find_client_by_id");

        Client client = clientsById.get(id);
        if (client == null) {
            throw new NotFoundException("Clientul cu id " + id + " nu exista.");
        }

        return client;
    }

    public Driver findDriverById(int id) {
        auditService.logAction("find_driver_by_id");

        Driver driver = driversById.get(id);
        if (driver == null) {
            throw new NotFoundException("Soferul cu id " + id + " nu exista.");
        }

        return driver;
    }

    public List<Client> getAllClients() {
        auditService.logAction("get_all_clients");
        return new ArrayList<>(clients);
    }

    public List<Driver> getAllDrivers() {
        auditService.logAction("get_all_drivers");
        return new ArrayList<>(drivers);
    }

    public void deleteClient(int id) {
        auditService.logAction("delete_client");

        Client client = findClientById(id);
        clients.remove(client);
        clientsById.remove(id);
        clientRepository.delete(id);
    }

    public void deleteDriver(int id) {
        auditService.logAction("delete_driver");

        Driver driver = findDriverById(id);
        drivers.remove(driver);
        driversById.remove(id);
        driverRepository.delete(id);
    }
}