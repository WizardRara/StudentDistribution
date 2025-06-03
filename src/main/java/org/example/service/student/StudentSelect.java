package org.example.service.student;

import org.example.entity.Student;

import java.util.List;

public interface StudentSelect {
    List<Student> getStudentsByCourseId(int courseId);
    List<Student> getAllStudents();
}
