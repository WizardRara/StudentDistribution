package org.example.service;

import org.example.entity.Course;
import org.example.entity.Group;
import org.example.entity.Student;
import org.example.repository.*;

import java.util.*;

public class DistributionServiceIMPL implements DistributionService {
    private final DBGenerator dbGenerator;
    private final CourseDAO courseDAO;
    private final GroupDAO groupDAO;
    private final StudentCourseDAO studentCourseDAO;
    private final StudentDAO studentDAO;
    private final StudentGroupDistribution studentGroupDistribution;
    private final StudentCourseDistribution studentCourseDistribution;

    public DistributionServiceIMPL(DBGenerator dbGenerator,
                                   CourseDAO courseDAO,
                                   GroupDAO groupDAO,
                                   StudentCourseDAO studentCourseDAO,
                                   StudentDAO studentDAO,
                                   StudentGroupDistribution studentGroupDistribution,
                                   StudentCourseDistribution studentCourseDistribution) {
        this.dbGenerator = dbGenerator;
        this.courseDAO = courseDAO;
        this.groupDAO = groupDAO;
        this.studentCourseDAO = studentCourseDAO;
        this.studentDAO = studentDAO;
        this.studentGroupDistribution = studentGroupDistribution;
        this.studentCourseDistribution = studentCourseDistribution;
    }

    @Override
    public void distributeAll() {
        List<Group> groups = dbGenerator.getGroupsList();
        List<Student> students = dbGenerator.getStudentsList();
        List<Course> courses = dbGenerator.getCoursesList();
        groups = groupDAO.insertGroups(groups);
        studentGroupDistribution.assignGroups(students, groups);
        students = studentDAO.insertStudents(students);
        courses = courseDAO.insertCourses(courses);
        studentCourseDistribution.assignCourses(students, courses);
        studentCourseDAO.insertStudentCourses(students);
    }
}
