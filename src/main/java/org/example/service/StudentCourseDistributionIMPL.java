package org.example.service;

import org.example.entity.Course;
import org.example.entity.Student;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class StudentCourseDistributionIMPL implements StudentCourseDistribution {
    private final Random random = new Random();

    @Override
    public void assignCourses(List<Student> students, List<Course> courses) {
        for (Student student : students) {
            assignCoursesForCurrentStudent(courses, student);
        }
    }

    private void assignCoursesForCurrentStudent(List<Course> courses, Student student) {
        int amountCoursesForCurrentStudent = random.nextInt(3) + 1;
        Set<Course> coursesForCurrentStudent = new HashSet<>();
        while (coursesForCurrentStudent.size() < amountCoursesForCurrentStudent) {
            Course randomCourse = courses.get(random.nextInt(courses.size()));
            if (coursesForCurrentStudent.add(randomCourse)) {
                student.getCourseList().add(randomCourse);
                randomCourse.getStudentList().add(student);
            }
        }
    }
}
