package org.example.service;

import org.example.entity.Course;
import org.example.entity.Group;
import org.example.entity.Student;

import java.util.List;

public interface DBGenerator {
    List<Student> getStudentsList();
    List<Group> getGroupsList();
    List<Course> getCoursesList();
}
