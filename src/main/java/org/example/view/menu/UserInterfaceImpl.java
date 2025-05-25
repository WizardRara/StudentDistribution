package org.example.view.menu;

import org.example.view.console.ConsoleView;
import org.example.view.handler.addNewStudent.AddNewStudentHandler;
import org.example.view.handler.addStudentToCourse.AddStudentToCourseHandler;
import org.example.view.handler.deleteStudent.DeleteStudentHandler;
import org.example.view.handler.findGroups.GroupSearchHandler;
import org.example.view.handler.findStudents.StudentSearchHandler;
import org.example.view.handler.removeStudentFromCourse.RemoveStudentFromCourseHandler;

public class UserInterfaceImpl implements UserInterface {
    private final ConsoleView consoleView;
    private final GroupSearchHandler groupSearchHandler;
    private final StudentSearchHandler studentSearchHandler;
    private final AddNewStudentHandler addNewStudentHandler;
    private final DeleteStudentHandler deleteStudentHandler;
    private final AddStudentToCourseHandler addStudentToCourseHandler;
    private final RemoveStudentFromCourseHandler removeStudentFromCourseHandler;

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

    public UserInterfaceImpl(ConsoleView consoleView, GroupSearchHandler groupSearchHandler,
                             StudentSearchHandler studentSearchHandler, AddNewStudentHandler addNewStudentHandler,
                             DeleteStudentHandler deleteStudentHandler, AddStudentToCourseHandler addStudentToCourseHandler,
                             RemoveStudentFromCourseHandler removeStudentFromCourseHandler) {
        this.consoleView = consoleView;
        this.groupSearchHandler = groupSearchHandler;
        this.studentSearchHandler = studentSearchHandler;
        this.addNewStudentHandler = addNewStudentHandler;
        this.deleteStudentHandler = deleteStudentHandler;
        this.addStudentToCourseHandler = addStudentToCourseHandler;
        this.removeStudentFromCourseHandler = removeStudentFromCourseHandler;
    }

    @Override
    public void displayUserFunctions() {
        while (true) {
            String option = consoleView.getUserInput(MENU_OF_OPTIONS);
            switch (option) {
                case "1" -> groupSearchHandler.findGroups();
                case "2" -> studentSearchHandler.findStudents();
                case "3" -> addNewStudentHandler.addNewStudent();
                case "4" -> deleteStudentHandler.deleteStudentById();
                case "5" -> addStudentToCourseHandler.addStudentToCourse();
                case "6" -> removeStudentFromCourseHandler.removeStudentFromCourse();
                case "0" -> {
                    consoleView.showMessage(EXIT);
                    return;
                }
                default -> consoleView.showMessage(INCORRECT_INPUT);
            }
        }
    }
}
