package org.example.repository;

import org.example.entity.StudentCourse;

import java.util.List;

public interface StudentCourseWriteDAO {
    void insertStudentCourses(List<StudentCourse> studentCourses);
}
