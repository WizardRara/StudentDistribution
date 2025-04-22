package org.example.repository;

import org.example.config.DBConnection;
import org.example.entity.Student;

import java.sql.*;
import java.util.List;

public class StudentDAOIMPL implements StudentDAO {
    private final DBConnection dbConnection;
    private static final String SQL_INSERT_STUDENTS =
            "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
    private static final String STUDENT_ID_COLUMN = "student_id";

    public StudentDAOIMPL(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Student> insertStudentsReturningStudentId(List<Student> students) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STUDENTS,
                     Statement.RETURN_GENERATED_KEYS)) {
            for (Student student : students) {
                preparedStatement.setString(1, student.getFirstName());
                preparedStatement.setString(2, student.getLastName());
                if (student.getGroupId() == null) {
                    preparedStatement.setNull(3, Types.INTEGER);
                } else {
                    preparedStatement.setInt(3, student.getGroupId());
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            setStudentIdColumn(students, preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при вставке студентов: " + e.getMessage(), e);
        }
        return students;
    }

    private void setStudentIdColumn(List<Student> students, PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
            int id = 0;
            while (resultSet.next()) {
                students.get(id++).setStudentId(resultSet.getInt(STUDENT_ID_COLUMN));
            }
        }
    }
}
