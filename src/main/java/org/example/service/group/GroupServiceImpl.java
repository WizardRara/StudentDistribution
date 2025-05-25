package org.example.service.group;

import org.example.dto.GroupWithCount;
import org.example.entity.Group;
import org.example.entity.Student;

public class GroupServiceImpl implements GroupService {
    @Override
    public boolean assign(Student student, GroupWithCount group) {
        if (student.getGroup() != null) return false;
        Group groupToAssign = new Group(group.groupId(), group.groupName());
        student.setGroup(groupToAssign);
        return true;
    }

    @Override
    public boolean reassign(Student student, GroupWithCount group) {
        if (student.getGroup().getGroupId() == group.groupId()) return false;
        Group groupToSet = new Group(group.groupId(), group.groupName());
        student.setGroup(groupToSet);
        return true;
    }

    @Override
    public boolean delete(Student student) {
        if (student.getGroup() == null) return false;
        student.setGroup(null);
        return true;
    }
}
