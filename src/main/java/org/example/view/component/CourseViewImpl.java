package org.example.view.component;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.util.CourseUtils;
import org.example.view.console.ConsoleView;

import java.util.List;

public class CourseViewImpl implements CourseView {
    private final ConsoleView consoleView;

    public CourseViewImpl(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    @Override
    public void showMessage(String message) {
        consoleView.showMessage(message);
    }

    @Override
    public String getUserInput(String prompt) {
        return consoleView.getUserInput(prompt);
    }

    @Override
    public void showCourses(List<Course> courses) {
        String formattedCourses = CourseUtils.getCoursesAsString(courses);
        consoleView.showMessage(formattedCourses);
    }

    @Override
    public void showAssignmentSuccess(Student student, Course course) {
        consoleView.showMessage(String.format("Student %s %s successfully assigned to course %s%n",
                student.getFirstName(), student.getLastName(), course.getCourseName()));
    }
}
