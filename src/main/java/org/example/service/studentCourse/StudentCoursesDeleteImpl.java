package org.example.service.studentCourse;

import org.example.repository.StudentCourseDAO;

import java.util.List;

public class StudentCoursesDeleteImpl implements StudentCoursesDelete {
    private final StudentCourseDAO studentCourseDAO;

    public StudentCoursesDeleteImpl(StudentCourseDAO studentCourseDAO) {
        this.studentCourseDAO = studentCourseDAO;
    }

    @Override
    public boolean deleteStudentCourses(int studentId, List<Integer> courseIds) {
        return studentCourseDAO.deleteStudentRelations(studentId, courseIds);
    }
}
