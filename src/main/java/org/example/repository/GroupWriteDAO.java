package org.example.repository;

import org.example.entity.Group;

import java.util.List;

public interface GroupWriteDAO {
    List<Group> insertGroupsReturningGroupId(List<Group> groups);
}
