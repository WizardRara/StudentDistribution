package org.example.view.handler.findGroups;

import org.example.dto.GroupWithCount;
import org.example.service.group.GroupSelect;
import org.example.util.InputValidator;
import org.example.view.component.GroupView;

import java.util.List;
import java.util.OptionalInt;

public class GroupSearchHandlerImpl implements GroupSearchHandler {
    private final GroupView groupView;
    private final InputValidator inputValidator;
    private final GroupSelect groupSelect;
    private final static String FIND_GROUPS = """
            Enter a number between 10 and 30
            Find all groups with less than or equal to that number of students
            Enter 0 to return to the main menu""";
    private final static String RETURN = "Returning to main menu";
    private final static String INCORRECT_INPUT = "Please enter correct number";

    public GroupSearchHandlerImpl(GroupView groupView, InputValidator inputValidator, GroupSelect groupSelect) {
        this.groupView = groupView;
        this.inputValidator = inputValidator;
        this.groupSelect = groupSelect;
    }

    @Override
    public void findGroups() {
        int amountOfStudents = getUserInputOrReturn();
        if (amountOfStudents == -1) return;
        List<GroupWithCount> groups = groupSelect.groupSelectByUser(amountOfStudents);
        groupView.showMessage("Groups with " + amountOfStudents + " students or less is:");
        groupView.showGroups(groups);
        groupView.showMessage(RETURN);
    }

    private int getUserInputOrReturn() {
        while (true) {
            String input = groupView.getUserInput(FIND_GROUPS);
            if (input.equals("0")) {
                groupView.showMessage(RETURN);
                return -1;
            }
            OptionalInt parsed = inputValidator.parseIntInRange(input, 10, 30);
            if (parsed.isPresent()) {
                return parsed.getAsInt();
            } else {
                groupView.showMessage(INCORRECT_INPUT);
            }
        }
    }
}
