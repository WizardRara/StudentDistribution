package org.example.view.component;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.view.console.ConsoleView;

import java.util.List;

public interface CourseView extends ConsoleView {
    void showCourses(List<Course> courses);
    void showAssignmentSuccess(Student student, Course course);
}
