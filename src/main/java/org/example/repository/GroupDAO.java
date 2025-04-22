package org.example.repository;

import org.example.entity.Group;

import java.util.List;

public interface GroupDAO {
    List<Group> insertGroupsReturningGroupId(List<Group> groups);
    void updateGroupsInDB(List<Group> groups);
}
