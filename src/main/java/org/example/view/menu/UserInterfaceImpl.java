package org.example.view.menu;

import org.example.view.console.ConsoleView;
import org.example.view.handler.Command;

import java.util.Map;

public class UserInterfaceImpl implements UserInterface {
    private final ConsoleView consoleView;
    private final Map<String, Command> menuCommands;

    private static final String MENU_OF_OPTIONS = """
            Choose an option and enter it's number:
            1.Find all groups with less or equal students number
            2.Find all students related to a course with a given name
            3.Add a new student
            4.Delete a student by STUDENT_ID
            5.Add a student to a course
            6.Remove a student from a course
            0.Exit the program""";
    private final static String EXIT = "Exiting...";
    private final static String INCORRECT_INPUT = "Please enter correct number";

    public UserInterfaceImpl(ConsoleView consoleView, Map<String, Command> menuCommands) {
        this.consoleView = consoleView;
        this.menuCommands = menuCommands;
    }

    @Override
    public void displayUserFunctions() {
        while (true) {
            String option = consoleView.getUserInput(MENU_OF_OPTIONS);
            if ("0".equals(option)) {
                consoleView.showMessage(EXIT);
                return;
            }
            Command command = menuCommands.get(option);
            if (command != null) {
                command.execute();
            } else {
                consoleView.showMessage(INCORRECT_INPUT);
            }
        }
    }
}
