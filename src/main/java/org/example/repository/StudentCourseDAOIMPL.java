package org.example.repository;

import org.example.entity.Course;
import org.example.entity.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentCourseDAOIMPL implements StudentCourseDAO {
    private final TransactionManager transactionManager;
    private static final String SQL_INSERT_STUDENT_COURSES =
            "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";

    public StudentCourseDAOIMPL(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void insertStudentCourses(List<Student> students) {
        transactionManager.doInTransaction(connection -> {
            insertStudentCourses(students, connection);
            return null;
        }, false);
    }

    @Override
    public void fillCoursesForStudents(List<Student> students) {
        List<Integer> ids = students.stream()
                .map(Student::getStudentId)
                .toList();
        String inSql = ids.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));
        String sql = """
                SELECT sc.student_id, c.course_id, c.course_name
                FROM student_courses sc
                JOIN courses c ON sc.course_id = c.course_id
                WHERE student_id IN (%s)""".formatted(inSql);
        Map<Integer, List<Course>> studentCourses = new HashMap<>();
        transactionManager.doInTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (int i = 0; i < ids.size(); i++) {
                    preparedStatement.setInt(i + 1, ids.get(i));
                }
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int studentId = resultSet.getInt(1);
                        int courseId = resultSet.getInt(2);
                        String courseName = resultSet.getString(3);
                        studentCourses
                                .computeIfAbsent(studentId, k -> new ArrayList<>())
                                .add(new Course(courseId, courseName));
                    }
                }
            }
            return null;
        }, true);
        fillStudentCourses(students, studentCourses);
    }

    @Override
    public boolean insertNewStudentRelations(int studentId, List<Course> courses) {
        return transactionManager.doInTransaction(connection -> {
            String sql = """
            INSERT INTO student_courses (student_id, course_id)
            VALUES (?, ?)
            ON CONFLICT (student_id, course_id) DO NOTHING""";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                for (Course course : courses) {
                    preparedStatement.setInt(1, studentId);
                    preparedStatement.setInt(2, course.getCourseId());
                    preparedStatement.addBatch();
                }
                int[] result = preparedStatement.executeBatch();
                return result.length == courses.size();
            }
        }, false);
    }

    @Override
    public boolean deleteStudentRelations(int studentId, List<Integer> courseIds) {
        return transactionManager.doInTransaction(connection -> {
            String ids = courseIds.stream()
                    .map(id -> "?")
                    .collect(Collectors.joining(", "));
            String sql = "DELETE FROM student_courses WHERE student_id = ? AND course_id IN (" + ids + ")";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, studentId);
                for (int i = 0; i < courseIds.size(); i++) {
                    preparedStatement.setInt(i + 2, courseIds.get(i));
                }
                int affectedRows = preparedStatement.executeUpdate();
                return affectedRows > 0;
            }
        }, false);
    }

    private void fillStudentCourses(List<Student> students, Map<Integer, List<Course>> studentCourses) {
        for (Student student : students) {
            List<Course> courses = studentCourses.getOrDefault(student.getStudentId(), List.of());
            student.setCourseList(courses);
        }
    }

    public void insertStudentCourses(List<Student> students, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STUDENT_COURSES)) {
            fillStudentCoursesBatch(preparedStatement, students);
            preparedStatement.executeBatch();
        }
    }

    private void fillStudentCoursesBatch(PreparedStatement ps, List<Student> students) throws SQLException {
        for (Student student : students) {
            for (Course course : student.getCourseList()) {
                ps.setInt(1, student.getStudentId());
                ps.setInt(2, course.getCourseId());
                ps.addBatch();
            }
        }
    }
}
