package org.example.view.handler.removeStudentFromCourse;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.service.course.CourseService;
import org.example.view.component.CourseView;
import org.example.view.selector.CourseSelector;

import java.util.ArrayList;
import java.util.List;

public class CourseRemovalFlowHandler {
    private final CourseSelector courseSelector;
    private final CourseService courseService;
    private final CourseView courseView;

    public CourseRemovalFlowHandler(CourseSelector courseSelector, CourseService courseService, CourseView courseView) {
        this.courseSelector = courseSelector;
        this.courseService = courseService;
        this.courseView = courseView;
    }

    public List<Integer> removeCourse(Student student) {
        List<Integer> toRemove = new ArrayList<>();
        courseView.showMessage("Remove up to 1 course from student");
        while (student.getCourseList().size() <= 1) {
            Course course = courseSelector.selectCourseByUser(student.getCourseList());
            if (course == null) break;
            if (courseService.delete(student, course.getCourseId())) {
                toRemove.add(course.getCourseId());
                courseView.showMessage("Course deleted successfully");
            } else {
                courseView.showMessage("Could not delete the course");
            }
            if (student.getCourseList().size() <= 1) {
                courseView.showMessage("You can remove " + (student.getCourseList().size() - 1) + "course(s)");
            }
        }
        courseView.showMessage("Minimum courses reached");
        return toRemove;
    }
}
