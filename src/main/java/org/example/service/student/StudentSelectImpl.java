package org.example.service.student;

import org.example.entity.Student;
import org.example.repository.StudentCourseDAO;
import org.example.repository.StudentDAO;

import java.util.List;

public class StudentSelectImpl implements StudentSelect {
    private final StudentDAO studentDAO;
    private final StudentCourseDAO studentCourseDAO;

    public StudentSelectImpl(StudentDAO studentDAO, StudentCourseDAO studentCourseDAO) {
        this.studentDAO = studentDAO;
        this.studentCourseDAO = studentCourseDAO;
    }

    @Override
    public List<Student> getStudentsByCourseId(int courseId) {
        return studentDAO.getStudentsByCourseId(courseId);
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> students = studentDAO.selectAllStudentsWithGroups();
        studentCourseDAO.fillCoursesForStudents(students);
        if (!students.isEmpty()) {
            return students;
        }
        return null;
    }
}
