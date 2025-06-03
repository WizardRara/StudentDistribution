package org.example.view.handler.addStudentToCourse;

import org.example.entity.Student;
import org.example.service.studentCourse.StudentCoursesInsert;
import org.example.view.component.CourseView;

public class StudentCourseSaverImpl implements StudentCourseSaver {
    private final StudentCoursesInsert studentCoursesInsert;
    private final CourseView courseView;

    public StudentCourseSaverImpl(CourseView courseView, StudentCoursesInsert studentCoursesInsert) {
        this.courseView = courseView;
        this.studentCoursesInsert = studentCoursesInsert;
    }

    @Override
    public void saveStudentCourses(Student student) {
        boolean success = studentCoursesInsert.insertCourses(student.getStudentId(), student.getCourseList());
        if (success) {
            courseView.showMessage("Courses successfully assigned to student");
        } else {
            courseView.showMessage("Failed to assign courses to student");
        }
    }
}
