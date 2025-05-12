package org.example.repository;

import org.example.entity.Course;
import org.example.entity.Student;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

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
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STUDENT_COURSES)) {
                fillStudentCoursesBatch(preparedStatement, students);
                preparedStatement.executeBatch();
                return null;
            }
        }, false);
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
