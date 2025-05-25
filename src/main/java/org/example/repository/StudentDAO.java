package org.example.repository;

import org.example.entity.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface StudentDAO {
    List<Student> insertStudents(List<Student> students);
    List<Student> insertStudents(List<Student> students, Connection connection) throws SQLException;
    List<Student> getStudentsByCourseId(int courseId);
    List<Student> selectAllStudentsWithGroups();
    boolean deleteById(int studentId);
}
