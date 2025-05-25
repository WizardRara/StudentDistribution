package org.example.view.handler.addNewStudent.assignCourse;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.service.course.CourseService;
import org.example.view.component.CourseView;

public class DeleteCourseAction {
    private final CourseService courseService;
    private final CourseView courseView;
    private static final String DELETE_SUCCESS = "Course deleted successfully";
    private static final String DELETE_FAILED = "Could not delete the course";

    public DeleteCourseAction(CourseView courseView, CourseService courseService) {
        this.courseView = courseView;
        this.courseService = courseService;
    }

    public void deleteCourseFromStudent(Student student, Course course) {
        boolean deleted = courseService.delete(student, course.getCourseId());
        if (deleted) {
            courseView.showMessage(DELETE_SUCCESS);
        } else {
            courseView.showMessage(DELETE_FAILED);
        }
    }
}
