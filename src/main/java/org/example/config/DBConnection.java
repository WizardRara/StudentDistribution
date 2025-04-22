package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private final PropertiesLoader propertiesLoader;

    public DBConnection(PropertiesLoader propertiesLoader) {
        this.propertiesLoader = propertiesLoader;
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(
                    propertiesLoader.getProperty("db.url"),
                    propertiesLoader.getProperty("db.user"),
                    propertiesLoader.getProperty("db.password"));
        } catch (SQLException e) {
            throw new RuntimeException("Connection Error", e);
        }
    }
}
