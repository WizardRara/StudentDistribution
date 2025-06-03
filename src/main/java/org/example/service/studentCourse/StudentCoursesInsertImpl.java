package org.example.service.studentCourse;

import org.example.entity.Course;
import org.example.repository.StudentCourseDAO;

import java.util.List;

public class StudentCoursesInsertImpl implements StudentCoursesInsert {
    private final StudentCourseDAO studentCourseDAO;

    public StudentCoursesInsertImpl(StudentCourseDAO studentCourseDAO) {
        this.studentCourseDAO = studentCourseDAO;
    }

    @Override
    public boolean insertCourses(int studentId, List<Course> courses) {
        return studentCourseDAO.insertNewStudentRelations(studentId, courses);
    }
}
