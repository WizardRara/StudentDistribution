package org.example.repository;

import org.example.entity.StudentCourse;

import java.sql.PreparedStatement;
import java.util.List;

public class StudentCourseWriteDAOIMPL implements StudentCourseWriteDAO {
    private final TransactionManager transactionManager;
    private static final String SQL_INSERT_STUDENT_COURSES =
            "INSERT INTO student_courses (student_id, course_id) VALUES (?, ?)";

    public StudentCourseWriteDAOIMPL(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void insertStudentCourses(List<StudentCourse> studentCourses) {
        transactionManager.doInTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STUDENT_COURSES)) {
                for (StudentCourse studentCourse : studentCourses) {
                    preparedStatement.setInt(1, studentCourse.studentId());
                    preparedStatement.setInt(2, studentCourse.courseId());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                return null;
            }
        }, Boolean.FALSE);
    }
}
