package org.example.service;

import org.example.entity.Course;
import org.example.entity.Group;
import org.example.entity.Student;
import org.example.entity.StudentCourse;
import org.example.repository.*;

import java.util.*;
import java.util.stream.Collectors;

public class DistributionServiceIMPL implements DistributionService {
    private final DBGenerator dbGenerator;
    private final CourseWriteDAO courseWriteDAO;
    private final GroupWriteDAO groupWriteDAO;
    private final StudentCourseWriteDAO studentCourseWriteDAO;
    private final StudentWriteDAO studentWriteDAO;
    private final Random random = new Random();


    public DistributionServiceIMPL(DBGenerator dbGenerator,
                                   CourseWriteDAO courseWriteDAO,
                                   GroupWriteDAO groupWriteDAO,
                                   StudentCourseWriteDAO studentCourseWriteDAO,
                                   StudentWriteDAO studentWriteDAO) {
        this.dbGenerator = dbGenerator;
        this.courseWriteDAO = courseWriteDAO;
        this.groupWriteDAO = groupWriteDAO;
        this.studentCourseWriteDAO = studentCourseWriteDAO;
        this.studentWriteDAO = studentWriteDAO;
    }

    @Override
    public void distributeAll() {
        List<Group> groups = dbGenerator.getGroupsList();
        List<Student> students = dbGenerator.getStudentsList();
        List<Course> courses = dbGenerator.getCoursesList();
        groups = returnGroupsWithIds(groups);
        assignGroupsIdsToStudents(groups, students);
        students = returnStudentsWithIds(students);
        courses = returnCoursesWithIds(courses);
        insertStudentCourse(assignCoursesToStudents(courses, students));
    }

    private List<Group> returnGroupsWithIds(List<Group> groups) {
        return groupWriteDAO.insertGroupsReturningGroupId(groups);
    }

    private void assignGroupsIdsToStudents(List<Group> groups, List<Student> students) {
        int remainingStudents = students.size();
        Iterator<Student> iterator = students.iterator();
        for (Group group : groups) {
            if (remainingStudents == 0) break;
            int currentGroupStudentsCount = random.nextInt(21) + 10;
            currentGroupStudentsCount = Math.min(currentGroupStudentsCount, remainingStudents);
            remainingStudents -= assignGroupIdToStudents(iterator, currentGroupStudentsCount, group);
        }
    }

    private int assignGroupIdToStudents(Iterator<Student> iterator, int currentGroupStudentsCount, Group group) {
        int studentsAssigned = 0;
        while (iterator.hasNext() && studentsAssigned < currentGroupStudentsCount) {
            Student student = iterator.next();
            student.setGroupId(group.getGroupId());
            studentsAssigned++;
        }
        return studentsAssigned;
    }

    private List<Student> returnStudentsWithIds(List<Student> students) {
        return studentWriteDAO.insertStudentsReturningStudentId(students);
    }

    private List<Course> returnCoursesWithIds(List<Course> courses) {
        return courseWriteDAO.insertCoursesReturningCourseIds(courses);
    }

    private List<StudentCourse> assignCoursesToStudents(List<Course> courses, List<Student> students) {
        return students.stream()
                .flatMap(student -> generateCoursesForStudents(courses, student).stream())
                .collect(Collectors.toList());
    }

    private List<StudentCourse> generateCoursesForStudents(List<Course> courses, Student student) {
        int amountCoursesForCurrentStudent = random.nextInt(3) + 1;
        Set<Integer> coursesForCurrentStudent = new HashSet<>();
        List<StudentCourse> listOfStudentCourseForCurrentStudent = new ArrayList<>();
        while (coursesForCurrentStudent.size() < amountCoursesForCurrentStudent) {
            Course randomCourse = courses.get(random.nextInt(courses.size()));
            if (coursesForCurrentStudent.add(randomCourse.getCourseId())) {
                listOfStudentCourseForCurrentStudent.add(createStudentCourse(student, randomCourse));
            }
        }
        return listOfStudentCourseForCurrentStudent;
    }

    private StudentCourse createStudentCourse(Student student, Course randomCourse) {
        return new StudentCourse(student.getStudentId(), randomCourse.getCourseId());
    }

    private void insertStudentCourse(List<StudentCourse> studentCourses) {
        studentCourseWriteDAO.insertStudentCourses(studentCourses);
    }
}
