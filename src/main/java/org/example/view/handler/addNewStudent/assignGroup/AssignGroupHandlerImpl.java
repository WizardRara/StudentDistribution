package org.example.view.handler.addNewStudent.assignGroup;

import org.example.dto.GroupWithCount;
import org.example.entity.Student;
import org.example.service.group.GroupSelect;
import org.example.view.component.GroupView;
import org.example.view.selector.GroupSelector;

import java.util.List;

public class AssignGroupHandlerImpl implements AssignGroupHandler {
    private final GroupSelect groupSelect;
    private final GroupSelector groupSelector;
    private final GroupView groupView;
    private final AssignGroupAction assignGroupAction;
    private final ReassignGroupAction reassignGroupAction;
    private final DeleteGroupAction deleteGroupAction;
    private static final String OPTIONS = """
            What do you want to do, choose an option and enter it's number:
            1.Delete group from a student
            2.Reassign group for a student
            0.Return to previous menu""";
    private static final String DB_GROUPS = "Please select group you want to assign";

    public AssignGroupHandlerImpl(GroupSelect groupSelect, GroupSelector groupSelector, GroupView groupView,
                                  AssignGroupAction assignGroupAction, ReassignGroupAction reassignGroupAction,
                                  DeleteGroupAction deleteGroupAction) {
        this.groupSelect = groupSelect;
        this.groupSelector = groupSelector;
        this.groupView = groupView;
        this.assignGroupAction = assignGroupAction;
        this.reassignGroupAction = reassignGroupAction;
        this.deleteGroupAction = deleteGroupAction;
    }

    @Override
    public void assignGroup(Student student) {
        List<GroupWithCount> groups = groupSelect.groupSelectByUser(29);
        if (student.getGroup() != null) {
            handleAlreadyAssignedGroup(student, groups);
        } else {
            assignGroup(student, groups);
        }
    }

    private void handleAlreadyAssignedGroup(Student student, List<GroupWithCount> groups) {
        while (true) {
            String option = groupView.getUserInput(OPTIONS);
            switch (option) {
                case "1" -> {
                    deleteGroupAction.deleteGroupFromStudent(student);
                    return;
                }
                case "2" -> {
                    reassignGroup(student, groups);
                    return;
                }
                case "0" -> {
                    groupView.showMessage("Returning to previous menu");
                    return;
                }
                default -> groupView.showMessage("Invalid option. Please try again");
            }
        }
    }

    private void assignGroup(Student student, List<GroupWithCount> groups) {
        GroupWithCount selected = groupSelection(groups);
        assignGroupAction.assignGroupToStudent(student, selected);
    }

    private void reassignGroup(Student student, List<GroupWithCount> groups) {
        GroupWithCount selected = groupSelection(groups);
        reassignGroupAction.reassignGroupToStudent(student, selected);
    }

    private GroupWithCount groupSelection(List<GroupWithCount> groups) {
        groupView.showMessage(DB_GROUPS);
        groupView.showGroups(groups);
        return groupSelector.selectGroupWithCountByUser(groups);
    }
}
