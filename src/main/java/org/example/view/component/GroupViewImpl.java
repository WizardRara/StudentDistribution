package org.example.view.component;

import org.example.dto.GroupWithCount;
import org.example.entity.Student;
import org.example.util.GroupWithCountUtils;
import org.example.view.console.ConsoleView;

import java.util.List;

public class GroupViewImpl implements GroupView {
    private final ConsoleView consoleView;

    public GroupViewImpl(ConsoleView consoleView) {
        this.consoleView = consoleView;
    }

    @Override
    public void showMessage(String message) {
        consoleView.showMessage(message);
    }

    @Override
    public String getUserInput(String prompt) {
        return consoleView.getUserInput(prompt);
    }

    @Override
    public void showGroups(List<GroupWithCount> groups) {
        String formatedGroups = GroupWithCountUtils.getGroupsWithCountAsString(groups);
        consoleView.showMessage(formatedGroups);
    }

    @Override
    public void showAssignmentSuccess(Student student, GroupWithCount group) {
        consoleView.showMessage(String.format("Student %s %s successfully assigned to group %s%n",
                student.getFirstName(), student.getLastName(), group.groupName()));
    }
}
