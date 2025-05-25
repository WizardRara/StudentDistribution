package org.example.service.course;

import org.example.entity.Course;
import org.example.entity.Student;

public interface CourseService {
    boolean assign(Student student, Course course);
    boolean reassign(Student student, Course from, Course to);
    boolean delete(Student student, int courseId);
}
