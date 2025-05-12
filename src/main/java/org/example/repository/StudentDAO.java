package org.example.repository;

import org.example.entity.Student;

import java.util.List;

public interface StudentDAO {
    List<Student> insertStudents(List<Student> students);
}
