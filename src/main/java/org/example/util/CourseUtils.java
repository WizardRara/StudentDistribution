package org.example.util;

import org.example.entity.Course;
import org.example.entity.Student;

import java.util.List;

public final class CourseUtils {
    private CourseUtils() {}

    public static Course findCourseById(List<Course> courses, int courseId) {
        for (Course course : courses) {
            if (course.getCourseId() == courseId) {
                return course;
            }
        }
        return null;
    }

    public static boolean isCourseAssigned(Student student, Course course) {
        return student.getCourseList().stream()
                .anyMatch(c -> c.getCourseId() == course.getCourseId());
    }

    public static String getCoursesAsString(List<Course> courses) {
        StringBuilder sb = new StringBuilder();
        for (Course course : courses) {
            sb.append(String.format("%d.%s%n", course.getCourseId(), course.getCourseName()));
        }
        return sb.toString();
    }
}
