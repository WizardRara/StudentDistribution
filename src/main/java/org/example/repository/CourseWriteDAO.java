package org.example.repository;

import org.example.entity.Course;

import java.util.List;

public interface CourseWriteDAO {
    List<Course> insertCoursesReturningCourseIds(List<Course> courses);
}
