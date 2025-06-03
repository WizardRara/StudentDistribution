package org.example.service.group;

import org.example.dto.GroupWithCount;

import java.util.List;

public interface GroupSelect {
    List<GroupWithCount> groupSelectByUser(int maxStudents);
}
