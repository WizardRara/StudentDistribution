package org.example.repository;

import org.example.entity.Course;

import java.util.List;

public interface CourseDAO {
    List<Course> insertCoursesReturningCourseIds(List<Course> courses);
    void updateCoursesStudentsCount(List<Course> courses);
}
