package org.example.repository;

import org.example.entity.Student;

import java.util.List;

public interface StudentCourseDAO {
    void insertStudentCourses(List<Student> students);
}
