package org.example.view.handler.findStudents;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.service.course.CourseSelect;
import org.example.service.student.StudentSelect;
import org.example.view.component.CourseView;
import org.example.view.component.StudentView;
import org.example.view.handler.Command;
import org.example.view.selector.CourseSelector;

import java.util.List;

public class StudentSearchCommand implements Command {
    private final CourseView courseView;
    private final StudentView studentView;
    private final CourseSelect courseSelect;
    private final CourseSelector courseSelector;
    private final StudentSelect studentSelect;
    private final static String SELECT_COURSE = """
            Choose one course from a list
            Receive all students related to it
            Enter 0 to return to the main menu""";

    public StudentSearchCommand(CourseView courseView, StudentView studentView, CourseSelect courseSelect,
                                CourseSelector courseSelector, StudentSelect studentSelect) {
        this.courseView = courseView;
        this.studentView = studentView;
        this.courseSelect = courseSelect;
        this.courseSelector = courseSelector;
        this.studentSelect = studentSelect;
    }

    @Override
    public void execute() {
        List<Course> courses = courseSelect.getAllCourses();
        courseView.showMessage(SELECT_COURSE);
        courseView.showCourses(courses);
        Course selected = courseSelector.selectCourseByUser(courses);
        List<Student> students = studentSelect.getStudentsByCourseId(selected.getCourseId());
        studentView.showStudents(students);
    }
}
