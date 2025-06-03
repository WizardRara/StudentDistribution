package org.example.service.student;

import org.example.entity.Student;
import org.example.repository.StudentCourseDAO;
import org.example.repository.StudentDAO;
import org.example.repository.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StudentInsertImpl implements StudentInsert {
    private final StudentDAO studentDAO;
    private final StudentCourseDAO studentCourseDAO;
    private final TransactionManager transactionManager;

    public StudentInsertImpl(StudentDAO studentDAO, StudentCourseDAO studentCourseDAO,
                             TransactionManager transactionManager) {
        this.studentDAO = studentDAO;
        this.studentCourseDAO = studentCourseDAO;
        this.transactionManager = transactionManager;
    }

    @Override
    public boolean insert(Student student) {
        if (student == null) return false;
        return transactionManager.doInTransaction(connection ->
                insertStudentWithCourses(student, connection), false);
    }

    private boolean insertStudentWithCourses(Student student, Connection connection) throws SQLException {
        List<Student> inserted = studentDAO.insertStudents(List.of(student), connection);
        studentCourseDAO.insertStudentCourses(inserted, connection);
        return !inserted.isEmpty();
    }
}
