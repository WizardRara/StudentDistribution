package org.example.view.component;

import org.example.entity.Student;
import org.example.util.StudentUtils;
import org.example.view.console.ConsoleView;

import java.util.List;

public class StudentViewImpl implements StudentView {
    private final ConsoleView consoleView;

    public StudentViewImpl(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    @Override
    public void showMessage(String message) {
        consoleView.showMessage(message);
    }

    @Override
    public String getUserInput(String prompt) {
        return consoleView.getUserInput(prompt);
    }

    @Override
    public void showCannotSaveWithoutCourse() {
        consoleView.showMessage("Student cannot be saved without at least one course");
    }

    @Override
    public void showStudentSaved(Student student) {
        consoleView.showMessage("Student saved successfully:");
        consoleView.showMessage(StudentUtils.getStudentAsString(student));
    }

    @Override
    public void showStudentInfo(Student student) {
        consoleView.showMessage("Current student state:");
        consoleView.showMessage(StudentUtils.getStudentAsString(student));
    }

    @Override
    public void showStudents(List<Student> students) {
        if (students.isEmpty()) {
            consoleView.showMessage("Students not found");
            return;
        }
        String formatedStudents = StudentUtils.getStudentsAsString(students);
        consoleView.showMessage(formatedStudents);
    }

    @Override
    public void showDeleteSuccess(Student student) {
        consoleView.showMessage("Student deleted successfully:");
        consoleView.showMessage(StudentUtils.getStudentAsString(student));
    }
}
