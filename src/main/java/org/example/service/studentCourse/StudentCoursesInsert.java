package org.example.service.studentCourse;

import org.example.entity.Course;

import java.util.List;

public interface StudentCoursesInsert {
    boolean insertCourses(int studentId, List<Course> courses);
}
