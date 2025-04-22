package org.example.repository;

import org.example.entity.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> insertStudentsReturningStudentId(List<Student> students);
}
