package org.example.service.studentCourse;

import java.util.List;

public interface StudentCoursesDelete {
    boolean deleteStudentCourses(int studentId, List<Integer> courseIds);
}
