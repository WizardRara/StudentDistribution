package org.example.service.group;

import org.example.dto.GroupWithCount;
import org.example.entity.Student;

public interface GroupService {
    boolean assign(Student student, GroupWithCount group);
    boolean reassign(Student student, GroupWithCount group);
    boolean delete(Student student);
}
