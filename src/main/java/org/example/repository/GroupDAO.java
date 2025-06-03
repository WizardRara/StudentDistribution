package org.example.repository;

import org.example.dto.GroupWithCount;
import org.example.entity.Group;

import java.util.List;

public interface GroupDAO {
    List<Group> insertGroups(List<Group> groups);
    List<GroupWithCount> selectGroupsForUser(int maxStudents);
}
