package com.pao.project.fooddelivery.service;

import com.pao.project.fooddelivery.exception.EntityNotFoundException;
import com.pao.project.fooddelivery.model.Client;
import com.pao.project.fooddelivery.model.Driver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService{
    private static final UserService INSTANCE = new UserService();

    private final List<Client> clients = new ArrayList<>();
    private final List<Driver> drivers = new ArrayList<>();
    private final Map<Integer, Client> clientsById = new HashMap<>();
    private final Map<Integer, Driver> driversById = new HashMap<>();

    private UserService(){
    }

    public static UserService getInstance(){
        return INSTANCE;
    }

    public void addClient(Client client){
        if (client == null) {
            throw new IllegalArgumentException("Clientul nu poate fi null.");
        }
        clients.add(client);
        clientsById.put(client.getId(), client);
    }

    public void addDriver(Driver driver){
        if (driver == null) {
            throw new IllegalArgumentException("Soferul nu poate fi null.");
        }
        drivers.add(driver);
        driversById.put(driver.getId(), driver);
    }

    public Client findClientById(int id){
        Client client = clientsById.get(id);
        if (client == null) {
            throw new EntityNotFoundException("Clientul cu id " + id + " nu exista.");
        }
        return client;
    }

    public Driver findDriverById(int id){
        Driver driver = driversById.get(id);
        if (driver == null) {
            throw new EntityNotFoundException("Soferul cu id " + id + " nu exista.");
        }
        return driver;
    }

    public List<Client> getAllClients(){
        return new ArrayList<>(clients);
    }

    public List<Driver> getAllDrivers(){
        return new ArrayList<>(drivers);
    }

    public void deleteClient(int id){
        Client client = findClientById(id);
        clients.remove(client);
        clientsById.remove(id);
    }

    public void deleteDriver(int id){
        Driver driver = findDriverById(id);
        drivers.remove(driver);
        driversById.remove(id);
    }
}