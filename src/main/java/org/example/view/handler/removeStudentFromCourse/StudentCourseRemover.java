package org.example.view.handler.removeStudentFromCourse;

import org.example.entity.Student;

import java.util.List;

public interface StudentCourseRemover {
    void removeStudentCourseRelation(Student student, List<Integer> courseIdList);
}
