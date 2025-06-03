package org.example.service.group;

import org.example.dto.GroupWithCount;
import org.example.repository.GroupDAO;

import java.util.List;

public class GroupSelectImpl implements GroupSelect {
    private final GroupDAO groupDAO;

    public GroupSelectImpl(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    @Override
    public List<GroupWithCount> groupSelectByUser(int maxStudents) {
        if (maxStudents > 30) {
            throw new IllegalArgumentException("maxStudents must be between 10 and 30");
        }
        return groupDAO.selectGroupsForUser(maxStudents);
    }
}
