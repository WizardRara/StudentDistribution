package org.example.view.handler.removeStudentFromCourse;

import org.example.entity.Student;
import org.example.service.student.StudentSelect;
import org.example.view.component.CourseView;
import org.example.view.component.StudentView;
import org.example.view.handler.Command;
import org.example.view.selector.StudentSelector;

import java.util.List;

public class RemoveStudentFromCourseCommand implements Command {
    private final StudentView studentView;
    private final CourseView courseView;
    private final StudentSelect studentSelect;
    private final StudentSelector studentSelector;
    private final CourseRemovalFlowHandler courseRemovalFlowHandler;
    private final StudentCourseRemover studentCourseRemover;
    private static final String SAVE_OPTION = """
            Would you like to save this student?
            Choose a number
            1.Yes
            0.No, return to the main menu""";

    public RemoveStudentFromCourseCommand(StudentView studentView, CourseView courseView,
                                          StudentSelect studentSelect, StudentSelector studentSelector,
                                          CourseRemovalFlowHandler courseRemovalFlowHandler,
                                          StudentCourseRemover studentCourseRemover) {
        this.studentView = studentView;
        this.courseView = courseView;
        this.studentSelect = studentSelect;
        this.studentSelector = studentSelector;
        this.courseRemovalFlowHandler = courseRemovalFlowHandler;
        this.studentCourseRemover = studentCourseRemover;
    }

    @Override
    public void execute() {
        List<Student> students = studentSelect.getAllStudents();
        studentView.showStudents(students);
        Student student = chooseStudentWithMoreThen1Course(students);
        if (student == null) return;
        courseView.showCourses(student.getCourseList());
        List<Integer> courseIdsToRemove = courseRemovalFlowHandler.removeCourse(student);
        if (courseIdsToRemove.isEmpty()) return;
        saveStudent(student, courseIdsToRemove);
    }

    private Student chooseStudentWithMoreThen1Course(List<Student> students) {
        while (true) {
            Student selected = studentSelector.selectStudentByUser(students);
            if (selected == null) {
                return null;
            }
            if (selected.getCourseList().size() <= 1) {
                studentView.showMessage("Student already student must have at least 2 courses, please choose another one");
            } else {
                return selected;
            }
        }
    }

    private void saveStudent(Student student, List<Integer> courseIdsToRemove) {
        studentView.showStudentInfo(student);
        studentView.showMessage(SAVE_OPTION);
        while (true) {
            String option = studentView.getUserInput("Enter a number:");
            if (option.equals("0")) {
                return;
            } else if (option.equals("1")) {
                studentCourseRemover.removeStudentCourseRelation(student, courseIdsToRemove);
                return;
            } else {
                studentView.showMessage("Wrong option, please try again");
            }
        }
    }
}
