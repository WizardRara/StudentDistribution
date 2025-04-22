package org.example.service;

import org.example.entity.Course;
import org.example.entity.Group;
import org.example.entity.Student;
import org.example.entity.StudentCourse;
import org.example.repository.*;

import java.util.*;

public class DistributionServiceIMPL implements DistributionService {
    private final DBGeneratorIMPL dbGeneratorIMPL;
    private final CourseDAOIMPL courseDAOIMPL;
    private final GroupDAOIMPL groupDAOIMPL;
    private final StudentCourseDAOIMPL studentCourseDAOIMPL;
    private final StudentDAOIMPL studentDAOIMPL;
    private final Random random = new Random();


    public DistributionServiceIMPL(DBGeneratorIMPL dbGeneratorIMPL,
                                   CourseDAOIMPL courseDAOIMPL,
                                   GroupDAOIMPL groupDAOIMPL,
                                   StudentCourseDAOIMPL studentCourseDAOIMPL,
                                   StudentDAOIMPL studentDAOIMPL) {
        this.dbGeneratorIMPL = dbGeneratorIMPL;
        this.courseDAOIMPL = courseDAOIMPL;
        this.groupDAOIMPL = groupDAOIMPL;
        this.studentCourseDAOIMPL = studentCourseDAOIMPL;
        this.studentDAOIMPL = studentDAOIMPL;
    }

    @Override
    public void distributeAll() {
        List<Group> groups = dbGeneratorIMPL.getGroupsList();
        List<Student> students = dbGeneratorIMPL.getStudentsList();
        List<Course> courses = dbGeneratorIMPL.getCoursesList();
        groups = returnGroupsWithIds(groups);
        groups = calculateGroupStudentCount(groups, students);
        groupDAOIMPL.updateGroupsInDB(groups);
        students = studentDAOIMPL.insertStudentsReturningStudentId(assignStudentsToGroups(students, groups));
        courses = courseDAOIMPL.insertCoursesReturningCourseIds(courses);
        List<StudentCourse> studentCourses = getStudentCoursesList(students, courses);
        courseDAOIMPL.updateCoursesStudentsCount(courses);
        studentCourseDAOIMPL.insertStudentCourses(studentCourses);
    }

    private List<Group> returnGroupsWithIds(List<Group> groups) {
        return groupDAOIMPL.insertGroupsReturningGroupId(groups);
    }

    private List<Group> calculateGroupStudentCount(List<Group> groups, List<Student> students) {
        int remainingStudents = students.size();
        for (int i = 0; i < groups.size(); i++) {
            int groupsLeft = groups.size() - i - 1;
            int minNeeded = 10 * groupsLeft;
            int maxStudentsForCurrentGroup = Math.min(30, remainingStudents - minNeeded);
            if (maxStudentsForCurrentGroup < 10) {
                return calculateGroupStudentCount(groups, students);
            }
            int studentsCount = random.nextInt(10, maxStudentsForCurrentGroup + 1);
            groups.get(i).setStudentCount(studentsCount);
            remainingStudents -= studentsCount;
        }
        return groups;
    }

    private List<Student> assignStudentsToGroups(List<Student> students, List<Group> groups) {
        Collections.shuffle(students);
        int studentIndex = 0;
        for (Group group : groups) {
            studentIndex = assignStudentsToGroup(students, group, studentIndex);
            if (studentIndex >= students.size()) break;
        }
        return students;
    }

    private int assignStudentsToGroup(List<Student> students, Group group, int studentIndex) {
        for (int i = 0; i < group.getStudentCount(); i++) {
            if (studentIndex >= students.size()) break;
            students.get(studentIndex).setGroupId(group.getGroupId());
            studentIndex++;
        }
        return studentIndex;
    }

    private List<StudentCourse> getStudentCoursesList(List<Student> students, List<Course> courses) {
        List<StudentCourse> studentCourses = new ArrayList<>();
        for (Student student : students) {
            Set<Integer> assignedCourseIds = new HashSet<>();
            int courseCount = random.nextInt(3) + 1;
            addStudentCourseEntityToList(studentCourses, courses, assignedCourseIds, student, courseCount);
        }
        return studentCourses;
    }

    private void addStudentCourseEntityToList(List<StudentCourse> studentCourses, List<Course> courses,
                                              Set<Integer> assignedCourseId, Student student, int courseCount) {
        while (assignedCourseId.size() < courseCount) {
            Course randomCourse = courses.get(random.nextInt(courses.size()));
            if (assignedCourseId.add(randomCourse.getCourseId())) {
                studentCourses.add(new StudentCourse(student.getStudentId(), randomCourse.getCourseId()));
                increaseStudentsCount(courses, randomCourse);
            }
        }
    }

    private void increaseStudentsCount(List<Course> courses, Course randomCourse) {
        for (Course course : courses) {
            if (course.getCourseId() == randomCourse.getCourseId()) {
                course.setStudentCount(course.getStudentCount() + 1);
            }
        }
    }
}
