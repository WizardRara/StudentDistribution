package org.example.view.handler.addNewStudent.saveStudent;

import org.example.entity.Student;
import org.example.service.student.StudentInsert;
import org.example.view.component.StudentView;

public class SaveStudentHandlerImpl implements SaveStudentHandler {
    private final StudentView studentView;
    private final StudentInsert studentInsert;

    public SaveStudentHandlerImpl(StudentView studentView, StudentInsert studentInsert) {
        this.studentView = studentView;
        this.studentInsert = studentInsert;
    }

    @Override
    public void saveStudent(Student student) {
        if (student.getCourseList().isEmpty()) {
            studentView.showCannotSaveWithoutCourse();
        } else {
            saveStudentToDatabase(student);
        }
    }

    private void saveStudentToDatabase(Student student) {
        boolean insert = studentInsert.insert(student);
        if (insert) {
            studentView.showStudentSaved(student);
        } else {
            studentView.showMessage("Error in saving student");
        }
    }
}
