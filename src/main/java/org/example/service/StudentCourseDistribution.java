package org.example.service;

import org.example.entity.Course;
import org.example.entity.Student;

import java.util.List;

public interface StudentCourseDistribution {
    void assignCourses(List<Student> students, List<Course> courses);
}
