package org.example.view.handler.deleteStudent;

import org.example.entity.Student;
import org.example.service.student.StudentDelete;
import org.example.service.student.StudentSelect;
import org.example.view.component.StudentView;
import org.example.view.selector.StudentSelector;

import java.util.List;

public class DeleteStudentHandlerImpl implements DeleteStudentHandler {
    private final StudentView studentView;
    private final StudentSelect studentSelect;
    private final StudentSelector studentSelector;
    private final StudentDelete studentDelete;

    public DeleteStudentHandlerImpl(StudentView studentView, StudentSelect studentSelect,
                                    StudentSelector studentSelector, StudentDelete studentDelete) {
        this.studentView = studentView;
        this.studentSelect = studentSelect;
        this.studentSelector = studentSelector;
        this.studentDelete = studentDelete;
    }

    @Override
    public void deleteStudentById() {
        List<Student> students = studentSelect.getAllStudents();
        studentView.showStudents(students);
        Student selected = studentSelector.selectStudentByUser(students);
        if (selected == null) {
            return;
        }
        boolean deleted = studentDelete.deleteStudent(selected);
        if (deleted) {
            studentView.showDeleteSuccess(selected);
        } else {
            studentView.showMessage("Failed to delete student");
        }
    }
}
