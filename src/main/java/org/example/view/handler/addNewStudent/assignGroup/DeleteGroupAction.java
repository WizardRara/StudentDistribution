package org.example.view.handler.addNewStudent.assignGroup;

import org.example.entity.Student;
import org.example.service.group.GroupService;
import org.example.view.component.GroupView;

public class DeleteGroupAction {
    private final GroupService groupService;
    private final GroupView groupView;
    private static final String DELETE_SUCCESS = "Group deleted successfully";
    private static final String DELETE_FAILED = "Could not delete the group";

    public DeleteGroupAction(GroupService groupService, GroupView groupView) {
        this.groupService = groupService;
        this.groupView = groupView;
    }

    public void deleteGroupFromStudent(Student student) {
        boolean deleted = groupService.delete(student);
        if (deleted) {
            groupView.showMessage(DELETE_SUCCESS);
        } else {
            groupView.showMessage(DELETE_FAILED);
        }
    }
}
