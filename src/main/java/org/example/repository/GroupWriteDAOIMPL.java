package org.example.repository;

import org.example.Exeption.GroupInsertException;
import org.example.entity.Group;

import java.sql.*;
import java.util.List;

public class GroupWriteDAOIMPL implements GroupWriteDAO {
    private final TransactionManager transactionManager;
    private static final String SQL_INSERT_GROUP_NAMES = "INSERT INTO groups (group_name) VALUES (?)";

    public GroupWriteDAOIMPL(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Group> insertGroupsReturningGroupId(List<Group> groups) {
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
        }, Boolean.FALSE);
    }
}
