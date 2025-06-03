package org.example.view.selector;

import org.example.entity.Course;
import org.example.util.CourseUtils;
import org.example.view.component.CourseView;

import java.util.List;

public class CourseSelector {
    private final CourseView courseView;
    private static final String CHOOSE_COURSE = "Enter course id, or 0 to return to the previous menu";
    private static final String RETURN = "Returning to previous menu";
    private static final String INVALID_COURSE_ID = "Invalid course id. Please try again";
    private static final String INVALID_INPUT = "Invalid input. Please enter a number";

    public CourseSelector(CourseView courseView) {
        this.courseView = courseView;
    }

    public Course selectCourseByUser(List<Course> courses) {
        while (true) {
            String courseIdInput = courseView.getUserInput(CHOOSE_COURSE);
            try {
                int courseId = Integer.parseInt(courseIdInput);
                if (courseId == 0) {
                    courseView.showMessage(RETURN);
                    return null;
                }
                Course course = CourseUtils.findCourseById(courses, courseId);
                if (course != null) return course;
                courseView.showMessage(INVALID_COURSE_ID);
            } catch (NumberFormatException e) {
                courseView.showMessage(INVALID_INPUT);
            }
        }
    }
}
