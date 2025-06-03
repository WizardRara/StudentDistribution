package org.example.view.handler.addNewStudent.assignCourse;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.service.course.CourseService;
import org.example.view.component.CourseView;

public class ReassignCourseAction {
    private final CourseService courseService;
    private final CourseView courseView;
    private static final String ALREADY_ASSIGNED = "Student is already assigned to this course";

    public ReassignCourseAction(CourseView courseView, CourseService courseService) {
        this.courseView = courseView;
        this.courseService = courseService;
    }

    public void reassignCourseToStudent(Student student, Course from, Course to) {
        boolean reassigned = courseService.reassign(student, from, to);
        if (reassigned) {
            courseView.showAssignmentSuccess(student, to);
        } else {
            courseView.showMessage(ALREADY_ASSIGNED);
        }
    }
}
