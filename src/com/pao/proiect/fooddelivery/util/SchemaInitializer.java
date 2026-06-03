package com.pao.proiect.fooddelivery.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer {
    private SchemaInitializer() {
    }

    public static void initializeSchema() {
        try {
            Connection connection = DatabaseConnection.getInstance().getConnection();

            InputStream input = SchemaInitializer.class
                    .getClassLoader()
                    .getResourceAsStream("schema.sql");

            if (input == null) {
                throw new RuntimeException("schema.sql not found in resources");
            }

            String sql = new String(input.readAllBytes(), StandardCharsets.UTF_8);
            String[] commands = sql.split(";");

            try (Statement statement = connection.createStatement()) {
                for (String command : commands) {
                    if (!command.trim().isEmpty()) {
                        statement.execute(command.trim());
                    }
                }
            }

        } catch (IOException | SQLException e) {
            throw new RuntimeException("Could not initialize schema", e);
        }
    }
}