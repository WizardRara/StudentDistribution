package org.example.repository;

import org.example.entity.Group;

import java.util.List;

public interface GroupDAO {
    List<Group> insertGroups(List<Group> groups);
}
