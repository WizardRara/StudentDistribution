package org.example.repository;

import org.example.dto.GroupWithCount;
import org.example.exception.GroupInsertException;
import org.example.exception.GroupSelectException;
import org.example.entity.Group;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDAOIMPL implements GroupDAO {
    private final TransactionManager transactionManager;
    private static final String SQL_INSERT_GROUP_NAMES = "INSERT INTO groups (group_name) VALUES (?)";
    private static final String SQL_SELECT_GROUPS_FOR_USER = """
            SELECT g.group_id, g.group_name, COUNT (s.student_id) AS student_count
            FROM groups g
            LEFT JOIN students s ON g.group_id = s.group_id
            GROUP BY g.group_id, g.group_name
            HAVING COUNT(s.student_id) <= ?
            ORDER BY g.group_id""";

    public GroupDAOIMPL(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Group> insertGroups(List<Group> groups) {
        return transactionManager.doInTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_GROUP_NAMES,
                    Statement.RETURN_GENERATED_KEYS)) {
                for (Group group : groups) {
                    preparedStatement.setString(1, group.getGroupName());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    for (Group group : groups) {
                        if (generatedKeys.next()) {
                            group.setGroupId(generatedKeys.getInt(1));
                        } else {
                            throw new GroupInsertException("Failed to retrieve generated key for group: " +
                                    group.getGroupName());
                        }
                    }
                }
            }
            return groups;
        }, false);
    }

    @Override
    public List<GroupWithCount> selectGroupsForUser(int maxStudents) {
        List<GroupWithCount> groupsWithCount = new ArrayList<>();
        return transactionManager.doInTransaction(connection -> {
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_GROUPS_FOR_USER)) {
                preparedStatement.setInt(1, maxStudents);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        groupsWithCount.add(new GroupWithCount(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getInt(3)));
                    }
                } catch (GroupSelectException e) {
                    throw new GroupSelectException("Group selection operation failed: " + e.getMessage());
                }
            }
            return groupsWithCount;
        }, true);
    }
}