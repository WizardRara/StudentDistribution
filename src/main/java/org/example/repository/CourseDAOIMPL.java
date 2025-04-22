package org.example.repository;

import org.example.config.DBConnection;
import org.example.entity.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CourseDAOIMPL implements CourseDAO {
    private final DBConnection dbConnection;
    private static final String SQL_INSERT_COURSES_RETURNING_COURSE_ID =
            "INSERT INTO courses (course_name, course_description) VALUES (?, ?) RETURNING courses.course_id";
    private static final String COURSE_ID_COLUMN_NAME = "course_id";
    private static final String SQL_UPDATE_STUDENTS_COUNT =
            "UPDATE courses SET student_count = ? WHERE course_id = ?";

    public CourseDAOIMPL(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Course> insertCoursesReturningCourseIds(List<Course> courses) {
        try (Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_COURSES_RETURNING_COURSE_ID)) {
            for (Course course : courses) {
                preparedStatement.setString(1, course.getCourseName());
                preparedStatement.setString(2, course.getCourseDescription());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        course.setCourseId(resultSet.getInt(COURSE_ID_COLUMN_NAME));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при вставке курсов: " + e.getMessage(), e);
        }
        return courses;
    }

    @Override
    public void updateCoursesStudentsCount(List<Course> courses) {
        try (Connection connection = dbConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_STUDENTS_COUNT)) {
            for (Course course : courses) {
                preparedStatement.setInt(1, course.getStudentCount());
                preparedStatement.setInt(2, course.getCourseId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при вставке счетчика студентов: " + e.getMessage(), e);
        }
    }
}
