package org.example.service;

import org.example.entity.Group;
import org.example.entity.Student;

import java.util.List;

public interface StudentGroupDistribution {
    void assignGroups(List<Student> students, List<Group> groups);
}
