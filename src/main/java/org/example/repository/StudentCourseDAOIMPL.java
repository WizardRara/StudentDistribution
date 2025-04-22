package org.example.repository;

import org.example.config.DBConnection;
import org.example.entity.StudentCourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StudentCourseDAOIMPL implements StudentCourseDAO {
    private final DBConnection dbConnection;
    private static final String SQL_INSERT_STUDENT_COURSES =
            "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";

    public StudentCourseDAOIMPL(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void insertStudentCourses(List<StudentCourse> studentCourses) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STUDENT_COURSES)) {
            for (StudentCourse studentCourse : studentCourses) {
                preparedStatement.setInt(1, studentCourse.studentId());
                preparedStatement.setInt(2, studentCourse.courseId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при вставке в связывающую таблицу: " + e.getMessage(), e);
        }
    }
}
