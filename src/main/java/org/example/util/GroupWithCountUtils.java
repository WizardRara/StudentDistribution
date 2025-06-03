package org.example.util;

import org.example.dto.GroupWithCount;

import java.util.List;

public final class GroupWithCountUtils {
    private GroupWithCountUtils() {}

    public static GroupWithCount findGroupWithCountById(List<GroupWithCount> groupsWithCount, int groupId) {
        for (GroupWithCount group : groupsWithCount) {
            if (group.groupId() == groupId) {
                return group;
            }
        }
        return null;
    }

    public static String getGroupsWithCountAsString(List<GroupWithCount> groupsWithCount) {
        StringBuilder sb = new StringBuilder();
        for (GroupWithCount group : groupsWithCount) {
            sb.append(String.format("%d.%s - %d%n", group.groupId(), group.groupName(), group.studentCount()));
        }
        return sb.toString();
    }
}
