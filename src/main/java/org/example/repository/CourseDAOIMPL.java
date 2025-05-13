package org.example.repository;

import org.example.Exeption.CourseInsertException;
import org.example.entity.Course;

import java.sql.*;
import java.util.List;

public class CourseDAOIMPL implements CourseDAO {
    private final TransactionManager transactionManager;
    private static final String SQL_INSERT_COURSES =
            "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";

    public CourseDAOIMPL(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Course> insertCourses(List<Course> courses) {
        return transactionManager.doInTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_COURSES,
                    Statement.RETURN_GENERATED_KEYS)) {
                for (Course course : courses) {
                    preparedStatement.setString(1, course.getCourseName());
                    preparedStatement.setString(2, course.getCourseDescription());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    for (Course course : courses) {
                        if (generatedKeys.next()) {
                            course.setCourseId(generatedKeys.getInt(1));
                        } else {
                            throw new CourseInsertException("Failed to retrieve generated key for student: " +
                                    course.getCourseName());
                        }
                    }
                }
                return courses;
            }
        }, false);
    }
}
