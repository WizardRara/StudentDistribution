package org.example.view.handler.addNewStudent;

import org.example.entity.Student;
import org.example.util.InputValidator;
import org.example.view.component.StudentView;
import org.example.view.handler.addNewStudent.assignCourse.AssignCourseHandler;
import org.example.view.handler.addNewStudent.assignGroup.AssignGroupHandler;
import org.example.view.handler.addNewStudent.saveStudent.SaveStudentHandler;

public class AddNewStudentHandlerImpl implements AddNewStudentHandler {
    private final StudentView studentView;
    private final InputValidator inputValidator;
    private final AssignGroupHandler assignGroupHandler;
    private final AssignCourseHandler assignCourseHandler;
    private final SaveStudentHandler saveStudentHandler;
    private final static String SUBMENU_OPTIONS = """
            What do you want to do, choose an option and enter it's number:
            1.Assign student to group (optional)
            2.Select 1-3 courses for the student
            3.Save student in Database and return to main menu
            4.Show student
            0.Return to main menu""";

    public AddNewStudentHandlerImpl(StudentView studentView, InputValidator inputValidator,
                                    AssignGroupHandler assignGroupHandler, AssignCourseHandler assignCourseHandler,
                                    SaveStudentHandler saveStudentHandler) {
        this.studentView = studentView;
        this.inputValidator = inputValidator;
        this.assignGroupHandler = assignGroupHandler;
        this.assignCourseHandler = assignCourseHandler;
        this.saveStudentHandler = saveStudentHandler;
    }

    @Override
    public void addNewStudent() {
        String firstName = getValidNameOrReturn("Enter student first name, enter 0 to return to main menu");
        if (firstName == null) return;
        String lastName = getValidNameOrReturn("Enter student last name, enter 0 to return to main menu");
        if (lastName == null) return;
        Student student = new Student(firstName, lastName);
        studentView.showStudentInfo(student);
        studentSubMenu(student);

    }

    private String getValidNameOrReturn(String prompt) {
        while (true) {
            String input = studentView.getUserInput(prompt);
            if (input.equals("0")) {
                return null;
            }
            if (!inputValidator.isValidName(input)) {
                studentView.showMessage("Invalid name. Please try again");
                continue;
            }
            return input;
        }
    }

    private void studentSubMenu(Student student) {
        while (true) {
            String option = studentView.getUserInput(SUBMENU_OPTIONS);
            switch (option) {
                case "1" -> assignGroupHandler.assignGroup(student);
                case "2" -> assignCourseHandler.assignCourse(student);
                case "3" -> {
                    saveStudentHandler.saveStudent(student);
                    return;
                }
                case "4" -> studentView.showStudentInfo(student);
                case "0" -> {
                    studentView.showMessage("Returning to main menu...");
                    return;
                }
                default -> studentView.showMessage("Invalid option. Please try again");
            }
        }
    }
}

