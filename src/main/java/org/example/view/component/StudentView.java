package org.example.view.component;

import org.example.entity.Student;
import org.example.view.console.ConsoleView;

import java.util.List;

public interface StudentView extends ConsoleView {
    void showCannotSaveWithoutCourse();
    void showStudentSaved(Student student);
    void showStudentInfo(Student student);
    void showStudents(List<Student> students);
    void showDeleteSuccess(Student student);
}
