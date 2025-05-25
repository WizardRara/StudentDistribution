package org.example.view.handler.addNewStudent.assignGroup;

import org.example.dto.GroupWithCount;
import org.example.entity.Student;
import org.example.service.group.GroupService;
import org.example.view.component.GroupView;

public class AssignGroupAction {
    private final GroupService groupService;
    private final GroupView groupView;

    public AssignGroupAction(GroupService groupService, GroupView groupView) {
        this.groupService = groupService;
        this.groupView = groupView;
    }

    public void assignGroupToStudent(Student student, GroupWithCount group) {
        if (group == null) {
            return;
        }
        boolean assigned = groupService.assign(student, group);
        if (assigned) {
            groupView.showAssignmentSuccess(student, group);
        }
    }
}
