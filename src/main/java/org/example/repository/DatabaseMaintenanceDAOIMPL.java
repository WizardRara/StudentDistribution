package org.example.repository;

import org.example.config.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseMaintenanceDAOIMPL implements DatabaseMaintenanceDAO {
    private final DBConnection dbConnection;
    private static final String SQL_TRUNCATE_TABLES =
            """
                    TRUNCATE TABLE
                        student_courses,
                        students,
                        groups,
                        courses
                    RESTART IDENTITY CASCADE""";

    public DatabaseMaintenanceDAOIMPL(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void clearDatabase() {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(SQL_TRUNCATE_TABLES);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка очистки базы данных" + e.getMessage(), e);
        }
    }
}
