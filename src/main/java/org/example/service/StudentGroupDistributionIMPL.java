package org.example.service;

import org.example.entity.Group;
import org.example.entity.Student;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class StudentGroupDistributionIMPL implements StudentGroupDistribution {
    private final Random random = new Random();

    @Override
    public void assignGroups(List<Student> students, List<Group> groups) {
        int remainingStudents = students.size();
        Iterator<Student> iterator = students.iterator();
        for (Group group : groups) {
            if (remainingStudents == 0) break;
            int currentGroupStudentsCount = random.nextInt(21) + 10;
            currentGroupStudentsCount = Math.min(currentGroupStudentsCount, remainingStudents);
            remainingStudents -= assignedStudentsForCurrentGroup(iterator, currentGroupStudentsCount, group);
        }
    }

    private int assignedStudentsForCurrentGroup(Iterator<Student> iterator, int currentGroupStudentsCount, Group group) {
        int studentsAssigned = 0;
        while (iterator.hasNext() && studentsAssigned < currentGroupStudentsCount) {
            Student student = iterator.next();
            student.setGroup(group);
            studentsAssigned++;
        }
        return studentsAssigned;
    }
}
