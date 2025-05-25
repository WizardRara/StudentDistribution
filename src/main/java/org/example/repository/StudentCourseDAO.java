package org.example.repository;

import org.example.entity.Course;
import org.example.entity.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface StudentCourseDAO {
    void insertStudentCourses(List<Student> students);
    void insertStudentCourses(List<Student> students, Connection connection) throws SQLException;
    void fillCoursesForStudents(List<Student> students);
    boolean insertNewStudentRelations(int studentId, List<Course> courses);
    boolean deleteStudentRelations(int studentId, List<Integer> courseIds);
}
