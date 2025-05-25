package org.example.view.handler.addStudentToCourse;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.service.course.CourseSelect;
import org.example.service.student.StudentSelect;
import org.example.view.component.CourseView;
import org.example.view.component.StudentView;
import org.example.view.selector.StudentSelector;

import java.util.List;

public class AddStudentToCourseHandlerImpl implements AddStudentToCourseHandler {
    private final StudentView studentView;
    private final CourseView courseView;
    private final StudentSelect studentSelect;
    private final StudentSelector studentSelector;
    private final CourseSelect courseSelect;
    private final StudentCourseSaver studentCourseSaver;
    private final CourseAssignmentFlowHandler courseAssignmentFlowHandler;
    private static final String SAVE_OPTION = """
            Would you like to save this student?
            Choose a number
            1.Yes
            0.No, return to the main menu""";

    public AddStudentToCourseHandlerImpl(StudentView studentView, CourseView courseView, StudentSelect studentSelect,
                                         StudentSelector studentSelector, CourseSelect courseSelect,
                                         CourseAssignmentFlowHandler courseAssignmentFlowHandler,
                                         StudentCourseSaver studentCourseSaver) {
        this.studentView = studentView;
        this.courseView = courseView;
        this.studentSelect = studentSelect;
        this.studentSelector = studentSelector;
        this.courseSelect = courseSelect;
        this.courseAssignmentFlowHandler = courseAssignmentFlowHandler;
        this.studentCourseSaver = studentCourseSaver;
    }

    @Override
    public void addStudentToCourse() {
        List<Student> students = studentSelect.getAllStudents();
        studentView.showStudents(students);
        Student student = chooseStudentWithLessThen3Courses(students);
        if (student == null) return;
        List<Course> courses = courseSelect.getAllCourses();
        courseView.showCourses(courses);
        courseAssignmentFlowHandler.assignCourses(student, courses);
        saveStudent(student);
    }

    private Student chooseStudentWithLessThen3Courses(List<Student> students) {
        while (true) {
            Student selected = studentSelector.selectStudentByUser(students);
            if (selected == null) {
                return null;
            }
            if (selected.getCourseList().size() >= 3) {
                studentView.showMessage("This student already has 3 courses, please choose another one");
            } else {
                return selected;
            }
        }
    }

    private void saveStudent(Student student) {
        studentView.showStudentInfo(student);
        studentView.showMessage(SAVE_OPTION);
        while (true) {
            String option = studentView.getUserInput("Enter a number:");
            if (option.equals("0")) {
                return;
            } else if (option.equals("1")) {
                studentCourseSaver.saveStudentCourses(student);
                return;
            } else {
                studentView.showMessage("Wrong option, please try again");
            }
        }
    }
}
