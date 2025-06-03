package org.example.view.handler.removeStudentFromCourse;

import org.example.entity.Student;
import org.example.service.studentCourse.StudentCoursesDelete;
import org.example.view.component.CourseView;

import java.util.List;

public class StudentCourseRemoverImpl implements StudentCourseRemover {
    private final StudentCoursesDelete studentCoursesDelete;
    private final CourseView courseView;

    public StudentCourseRemoverImpl(CourseView courseView, StudentCoursesDelete studentCoursesDelete) {
        this.courseView = courseView;
        this.studentCoursesDelete = studentCoursesDelete;
    }

    @Override
    public void removeStudentCourseRelation(Student student, List<Integer> courseIdList) {
        boolean success = studentCoursesDelete.deleteStudentCourses(student.getStudentId(), courseIdList);
        if (success) {
            courseView.showMessage("Student course relations successfully deleted");
        } else {
            courseView.showMessage("Failed to delete relations");
        }
    }
}
