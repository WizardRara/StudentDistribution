package org.example.view.handler.addNewStudent.assignCourse;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.service.course.CourseService;
import org.example.view.component.CourseView;

public class AssignCourseAction {
    private final CourseView courseView;
    private final CourseService courseService;

    public AssignCourseAction(CourseView courseView, CourseService courseService) {
        this.courseView = courseView;
        this.courseService = courseService;
    }

    public void assignCourseToStudent(Student student, Course course) {
        if (course == null) {
            return;
        }
        boolean assigned = courseService.assign(student, course);
        if (assigned) {
            courseView.showAssignmentSuccess(student, course);
        } else {
            courseView.showMessage("Course could not be assigned (already assigned or max reached)");
        }
    }
}
