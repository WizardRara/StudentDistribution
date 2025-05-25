package org.example.view.handler.addStudentToCourse;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.service.course.CourseService;
import org.example.view.component.CourseView;
import org.example.view.selector.CourseSelector;

import java.util.List;

public class CourseAssignmentFlowHandler {
    private final CourseSelector courseSelector;
    private final CourseService courseService;
    private final CourseView courseView;

    public CourseAssignmentFlowHandler(CourseView courseView, CourseSelector courseSelector, CourseService courseService) {
        this.courseView = courseView;
        this.courseSelector = courseSelector;
        this.courseService = courseService;
    }

    public void assignCourses(Student student, List<Course> courses) {
        courseView.showMessage("Assigning up to 3 courses to student");
        while (student.getCourseList().size() < 3) {
            Course course = courseSelector.selectCourseByUser(courses);
            if (course == null) return;
            if (courseService.assign(student, course)) {
                courseView.showAssignmentSuccess(student, course);
            } else {
                courseView.showMessage("Already assigned. Choose another one");
            }
            if (student.getCourseList().size() < 3) {
                courseView.showMessage("You can assign " + (3 - student.getCourseList().size()) + " more course(s)");
            }
        }
        courseView.showMessage("Max courses reached");
    }
}
