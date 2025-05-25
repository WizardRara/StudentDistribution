package org.example.view.selector;

import org.example.dto.GroupWithCount;
import org.example.util.GroupWithCountUtils;
import org.example.view.component.GroupView;

import java.util.List;

public class GroupSelector {
    private final GroupView groupView;
    private static final String CHOOSE_GROUP = "Enter group id, or 0 to return to the previous menu";
    private static final String RETURN = "Returning to previous menu";
    private static final String INVALID_GROUP_ID = "Invalid group id. Please try again";
    private static final String INVALID_INPUT = "Invalid input. Please enter a number";

    public GroupSelector(GroupView groupView) {
        this.groupView = groupView;
    }

    public GroupWithCount selectGroupWithCountByUser(List<GroupWithCount> groups) {
        while (true) {
            String groupIdInput = groupView.getUserInput(CHOOSE_GROUP);
            try {
                int groupId = Integer.parseInt(groupIdInput);
                if (groupId == 0) {
                    groupView.showMessage(RETURN);
                    return null;
                }
                GroupWithCount group = GroupWithCountUtils.findGroupWithCountById(groups, groupId);
                if (group != null) return group;
                groupView.showMessage(INVALID_GROUP_ID);
            } catch (NumberFormatException e) {
                groupView.showMessage(INVALID_INPUT);
            }
        }
    }
}
