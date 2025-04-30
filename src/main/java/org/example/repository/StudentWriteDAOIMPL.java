package org.example.repository;

import org.example.Exeption.StudentInsertException;
import org.example.entity.Student;

import java.sql.*;
import java.util.List;

public class StudentWriteDAOIMPL implements StudentWriteDAO {
    private final TransactionManager transactionManager;
    private static final String SQL_INSERT_STUDENTS =
            "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";

    public StudentWriteDAOIMPL(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Student> insertStudentsReturningStudentId(List<Student> students) {
        return transactionManager.doInTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STUDENTS,
                    Statement.RETURN_GENERATED_KEYS)) {
                for (Student student : students) {
                    preparedStatement.setString(1, student.getFirstName());
                    preparedStatement.setString(2, student.getLastName());
                    if (student.getGroupId() != null) {
                        preparedStatement.setInt(3, student.getGroupId());
                    } else {
                        preparedStatement.setNull(3, Types.INTEGER);
                    }
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    for (Student student : students) {
                        if (generatedKeys.next()) {
                            student.setStudentId(generatedKeys.getInt(1));
                        } else {
                            throw new StudentInsertException("Failed to retrieve generated key for student: %s %s"
                                    .formatted(student.getFirstName(), student.getLastName()));
                        }
                    }
                }
                return students;
            }
        }, Boolean.FALSE);
    }
}
