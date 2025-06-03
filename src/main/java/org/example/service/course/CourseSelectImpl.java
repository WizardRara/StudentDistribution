package org.example.service.course;

import org.example.entity.Course;
import org.example.repository.CourseDAO;

import java.util.List;

public class CourseSelectImpl implements CourseSelect {
    private final CourseDAO courseDAO;

    public CourseSelectImpl(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseDAO.getCourses();
    }
}
