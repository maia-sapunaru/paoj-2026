package com.pao.proiect.fooddelivery.service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class AuditService {
    private static final AuditService INSTANCE = new AuditService();

    private AuditService() {
    }

    public static AuditService getInstance() {
        return INSTANCE;
    }

    public synchronized void logAction(String actionName) {
        try (FileWriter writer = new FileWriter("audit.csv", true)) {
            writer.write(actionName + "," + LocalDateTime.now() + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Could not write audit log", e);
        }
    }
}