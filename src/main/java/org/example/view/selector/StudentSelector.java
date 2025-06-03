package org.example.view.selector;

import org.example.entity.Student;
import org.example.util.StudentUtils;
import org.example.view.component.StudentView;

import java.util.List;

public class StudentSelector {
    private final StudentView studentView;
    private static final String CHOOSE_STUDENT = "Enter student id, or 0 to return to the previous menu";
    private static final String RETURN = "Returning to previous menu";
    private static final String INVALID_STUDENT_ID = "Invalid student id. Please try again";
    private static final String INVALID_INPUT = "Invalid input. Please enter a number";

    public StudentSelector(final StudentView studentView) {
        this.studentView = studentView;
    }

    public Student selectStudentByUser(List<Student> students) {
        while (true) {
            String studentIdInput = studentView.getUserInput(CHOOSE_STUDENT);
            try {
                int studentId = Integer.parseInt(studentIdInput);
                if (studentId == 0) {
                    studentView.showMessage(RETURN);
                    return null;
                }
                Student student = StudentUtils.getStudentById(students, studentId);
                if (student != null) return student;
                studentView.showMessage(INVALID_STUDENT_ID);
            } catch (NumberFormatException e) {
                studentView.showMessage(INVALID_INPUT);
            }
        }
    }
}
