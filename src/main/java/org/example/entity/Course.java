package org.example.entity;

import java.util.Objects;

public class Course {
    private final String courseName;
    private final String courseDescription;
    private int studentCount;
    private int courseId;

    public Course(String courseName, String courseDescription) {
        this.courseName = courseName;
        this.courseDescription = courseDescription;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public int getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", courseDescription='" + courseDescription + '\'' +
                ", studentCount=" + studentCount +
                ", courseId=" + courseId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseName, course.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(courseName);
    }
}
