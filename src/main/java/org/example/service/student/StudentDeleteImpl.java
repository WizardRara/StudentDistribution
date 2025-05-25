package org.example.service.student;

import org.example.entity.Student;
import org.example.repository.StudentDAO;

public class StudentDeleteImpl implements StudentDelete {
    private final StudentDAO studentDAO;

    public StudentDeleteImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public boolean deleteStudent(Student student) {
        if (student == null) return false;
        return studentDAO.deleteById(student.getStudentId());
    }
}
