package org.example.view.handler.addNewStudent.assignCourse;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.service.course.CourseSelect;
import org.example.view.component.CourseView;
import org.example.view.selector.CourseSelector;

import java.util.List;

public class AssignCourseHandlerImpl implements AssignCourseHandler {
    private final CourseSelect courseSelect;
    private final CourseSelector courseSelector;
    private final AssignCourseAction assignCourseAction;
    private final CourseView courseView;
    private final ReassignCourseAction reassignCourseAction;
    private final DeleteCourseAction deleteCourseAction;
    private static final String OPTIONS1 = """
            What do you want to do, choose an option and enter it's number:
            1.Assign student to another course (maximum 3)
            2.Reassign course for a student
            3.Delete course from a student
            0.Return to previous menu""";
    private static final String OPTIONS2 = """
            What do you want to do, choose an option and enter it's number:
            1.Assign student to another course (maximum 3)
            2.Reassign course for a student
            0.Return to previous menu""";
    private static final String WRONG_OPTION = "Invalid option. Please try again";
    private static final String DB_COURSES = "Please select course you want to assign";

    public AssignCourseHandlerImpl(CourseView courseView, CourseSelect courseSelect,
                                   CourseSelector courseSelector, AssignCourseAction assignCourseAction,
                                   ReassignCourseAction reassignCourseAction, DeleteCourseAction deleteCourseAction) {
        this.courseView = courseView;
        this.courseSelect = courseSelect;
        this.courseSelector = courseSelector;
        this.assignCourseAction = assignCourseAction;
        this.reassignCourseAction = reassignCourseAction;
        this.deleteCourseAction = deleteCourseAction;
    }

    @Override
    public void assignCourse(Student student) {
        List<Course> courses = courseSelect.getAllCourses();
        if (!student.getCourseList().isEmpty()) {
            subMenuOption(student, courses);
        } else {
            assignCourse(student, courses);
        }
    }

    private void subMenuOption(Student student, List<Course> courses) {
        while (true) {
            String menu = getMenuText(student);
            String option = courseView.getUserInput(menu);
            switch (option) {
                case "1" -> assignCourse(student, courses);
                case "2" -> reassignCourse(student, courses);
                case "3" -> {
                    if (student.getCourseList().size() == 1) {
                        courseView.showMessage(WRONG_OPTION);
                    } else {
                        deleteCourse(student);
                    }
                }
                case "0" -> {
                    courseView.showMessage("Returning to previous menu");
                    return;
                }
                default -> courseView.showMessage(WRONG_OPTION);
            }
        }
    }

    private String getMenuText(Student student) {
        if (student.getCourseList().size() == 1) {
            return OPTIONS2;
        }
        return OPTIONS1;
    }

    private void assignCourse(Student student, List<Course> courses) {
        Course selected = courseSelection(courses, DB_COURSES);
        assignCourseAction.assignCourseToStudent(student, selected);
    }

    private void reassignCourse(Student student, List<Course> courses) {
        Course courseToReassign = courseSelection(student.getCourseList(),
                "Please select course you want to reassign");
        if (courseToReassign == null) {
            return;
        }
        Course replasingCourse = courseSelection(courses, DB_COURSES);
        if (replasingCourse == null) {
            return;
        }
        reassignCourseAction.reassignCourseToStudent(student, courseToReassign, replasingCourse);
    }

    private void deleteCourse(Student student) {
        Course courseToDelete = courseSelection(student.getCourseList(),
                "Please choose course you want to delete");
        if (courseToDelete == null) {
            return;
        }
        deleteCourseAction.deleteCourseFromStudent(student, courseToDelete);
    }

    private Course courseSelection(List<Course> courses, String message) {
        courseView.showMessage(message);
        courseView.showCourses(courses);
        return courseSelector.selectCourseByUser(courses);
    }
}
