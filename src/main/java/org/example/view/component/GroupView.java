package org.example.view.component;

import org.example.dto.GroupWithCount;
import org.example.entity.Student;
import org.example.view.console.ConsoleView;

import java.util.List;

public interface GroupView extends ConsoleView {
    void showGroups(List<GroupWithCount> groups);
    void showAssignmentSuccess(Student student, GroupWithCount group);
}
