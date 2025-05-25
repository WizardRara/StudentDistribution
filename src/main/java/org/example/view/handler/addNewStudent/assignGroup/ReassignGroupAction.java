package org.example.view.handler.addNewStudent.assignGroup;

import org.example.dto.GroupWithCount;
import org.example.entity.Student;
import org.example.service.group.GroupService;
import org.example.view.component.GroupView;

public class ReassignGroupAction {
    private final GroupService groupService;
    private final GroupView groupView;
    private static final String ALREADY_ASSIGNED = "Student is already assigned to this group";

    public ReassignGroupAction(GroupService groupService, GroupView groupView) {
        this.groupService = groupService;
        this.groupView = groupView;
    }

    public void reassignGroupToStudent(Student student, GroupWithCount group) {
        boolean reassigned = groupService.reassign(student, group);
        if (reassigned) {
            groupView.showAssignmentSuccess(student, group);
        } else {
            groupView.showMessage(ALREADY_ASSIGNED);
        }
    }
}
