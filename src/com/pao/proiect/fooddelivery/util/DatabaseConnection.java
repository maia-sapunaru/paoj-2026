package com.pao.proiect.fooddelivery.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        try {
            Properties properties = new Properties();

            InputStream input =
                    getClass().getClassLoader()
                            .getResourceAsStream("db.properties");

            if (input == null) {
                throw new RuntimeException("db.properties not found in resources");
            }

            properties.load(input);

            String url = properties.getProperty("db.url");

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);

        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new RuntimeException("Database connection error", e);
        }
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}