package org.example.repository;

import org.example.config.DBConnection;
import org.example.entity.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GroupDAOIMPL implements GroupDAO {
    private final DBConnection dbConnection;
    private static final String SQL_INSERT_GROUP_NAMES_RETURNING_GROUP_ID =
            "INSERT INTO groups (group_name) VALUES (?) RETURNING group_id";
    private static final String GROUP_ID_COLUMN = "group_id";
    private static final String SQL_UPDATE_GROUPS =
            "UPDATE groups SET students_count = ? WHERE group_id = ?";

    public GroupDAOIMPL(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public List<Group> insertGroupsReturningGroupId(List<Group> groups) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(SQL_INSERT_GROUP_NAMES_RETURNING_GROUP_ID)) {
            for (Group group : groups) {
                preparedStatement.setString(1, group.getGroupName());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        group.setGroupId(resultSet.getInt(GROUP_ID_COLUMN));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при вставке групп: " + e.getMessage(), e);
        } return groups;
    }

    @Override
    public void updateGroupsInDB(List<Group> groups) {
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_GROUPS)) {
            for (Group group : groups) {
                preparedStatement.setInt(1, group.getStudentCount());
                preparedStatement.setInt(2, group.getGroupId());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении групп: " + e.getMessage(), e);
        }
    }
}
