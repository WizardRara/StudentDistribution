package org.example.repository;

import org.example.exception.CourseInsertException;
import org.example.exception.CourseSelectException;
import org.example.entity.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAOIMPL implements CourseDAO {
    private final TransactionManager transactionManager;
    private static final String SQL_INSERT_COURSES =
            "INSERT INTO courses (course_name, course_description) VALUES (?, ?)";
    private static final String SQL_SELECT_ALL_COURSES = "SELECT * FROM courses";

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

    @Override
    public List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();
        return transactionManager.doInTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_COURSES)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        courses.add(new Course(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3)));
                    }
                } catch (CourseSelectException e) {
                    throw new CourseSelectException("Course selection operation failed" + e.getMessage());
                }
            }
            return courses;
        }, true);
    }
}
