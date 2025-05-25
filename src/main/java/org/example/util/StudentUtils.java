package org.example.util;

import org.example.entity.Course;
import org.example.entity.Student;

import java.util.List;
import java.util.stream.Collectors;

public final class StudentUtils {
    private StudentUtils() {}

    public static Student getStudentById(List<Student> students, int studentId) {
        return students.stream()
                .filter(student -> student.getStudentId() == studentId)
                .findFirst()
                .orElse(null);
    }

    public static String getStudentAsString(Student student) {
        String id = student.getStudentId() == 0 ? "none" : String.valueOf(student.getStudentId());
        String group = student.getGroup() == null ? "none" : student.getGroup().getGroupName();
        String courses = student.getCourseList().isEmpty()
                ? "none" :
                student.getCourseList().stream()
                        .map(Course::getCourseName)
                        .collect(Collectors.joining(", "));
        return String.format("ID: %-3s | Name: %-10s %-10s | Group: %-5s | Courses: %s%n",
                id, student.getFirstName(), student.getLastName(), group, courses);
    }

    public static String getStudentsAsString(List<Student> students) {
        StringBuilder sb = new StringBuilder();
        for (Student student : students) {
            sb.append(getStudentAsString(student));
        }
        return sb.toString();
    }
}
