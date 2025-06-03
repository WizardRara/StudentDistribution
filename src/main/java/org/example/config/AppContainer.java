package org.example.config;

import org.example.repository.*;
import org.example.service.*;
import org.example.service.course.CourseSelect;
import org.example.service.course.CourseSelectImpl;
import org.example.service.course.CourseService;
import org.example.service.course.CourseServiceImpl;
import org.example.service.group.GroupSelect;
import org.example.service.group.GroupSelectImpl;
import org.example.service.group.GroupService;
import org.example.service.group.GroupServiceImpl;
import org.example.service.student.StudentDeleteImpl;
import org.example.service.student.StudentInsertImpl;
import org.example.service.student.StudentSelect;
import org.example.service.student.StudentSelectImpl;
import org.example.service.studentCourse.StudentCoursesDeleteImpl;
import org.example.service.studentCourse.StudentCoursesInsertImpl;
import org.example.util.InputValidator;
import org.example.util.InputValidatorIMPL;
import org.example.view.component.*;
import org.example.view.console.ConsoleView;
import org.example.view.console.ConsoleViewImpl;
import org.example.view.handler.Command;
import org.example.view.handler.addNewStudent.AddNewStudentCommand;
import org.example.view.handler.addNewStudent.assignCourse.AssignCourseAction;
import org.example.view.handler.addNewStudent.assignCourse.AssignCourseHandlerImpl;
import org.example.view.handler.addNewStudent.assignCourse.DeleteCourseAction;
import org.example.view.handler.addNewStudent.assignCourse.ReassignCourseAction;
import org.example.view.handler.addNewStudent.assignGroup.AssignGroupAction;
import org.example.view.handler.addNewStudent.assignGroup.AssignGroupHandlerImpl;
import org.example.view.handler.addNewStudent.assignGroup.DeleteGroupAction;
import org.example.view.handler.addNewStudent.assignGroup.ReassignGroupAction;
import org.example.view.handler.addNewStudent.saveStudent.SaveStudentHandlerImpl;
import org.example.view.handler.addStudentToCourse.AddStudentToCourseCommand;
import org.example.view.handler.addStudentToCourse.CourseAssignmentFlowHandler;
import org.example.view.handler.addStudentToCourse.StudentCourseSaverImpl;
import org.example.view.handler.deleteStudent.DeleteStudentCommand;
import org.example.view.handler.findGroups.GroupSearchCommand;
import org.example.view.handler.findStudents.StudentSearchCommand;
import org.example.view.handler.removeStudentFromCourse.CourseRemovalFlowHandler;
import org.example.view.handler.removeStudentFromCourse.RemoveStudentFromCourseCommand;
import org.example.view.handler.removeStudentFromCourse.StudentCourseRemoverImpl;
import org.example.view.menu.UserInterface;
import org.example.view.menu.UserInterfaceImpl;
import org.example.view.selector.CourseSelector;
import org.example.view.selector.GroupSelector;
import org.example.view.selector.StudentSelector;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class AppContainer {
    private final PropertiesLoader propertiesLoader;
    private final DBConnection dbConnection;
    private final TransactionManager transactionManager;

    private final CourseDAO courseDAO;
    private final GroupDAO groupDAO;
    private final StudentCourseDAO studentCourseDAO;
    private final StudentDAO studentDAO;

    private final ConsoleView consoleView;
    private final CourseView courseView;
    private final GroupView groupView;
    private final StudentView studentView;

    private final InputValidator inputValidator;
    private final GroupSelect groupSelect;
    private final CourseSelect courseSelect;
    private final CourseSelector courseSelector;
    private final StudentSelect studentSelect;
    private final GroupSelector groupSelector;
    private final GroupService groupService;
    private final CourseService courseService;
    private final StudentSelector studentSelector;

    public AppContainer() {
        propertiesLoader = new PropertiesLoader(new Properties());
        dbConnection = new DBConnection(propertiesLoader);
        transactionManager = new TransactionManager(dbConnection);

        courseDAO = new CourseDAOIMPL(transactionManager);
        groupDAO = new GroupDAOIMPL(transactionManager);
        studentCourseDAO = new StudentCourseDAOIMPL(transactionManager);
        studentDAO = new StudentDAOIMPL(transactionManager);

        consoleView = new ConsoleViewImpl(new Scanner(System.in));
        courseView = new CourseViewImpl(consoleView);
        groupView = new GroupViewImpl(consoleView);
        studentView = new StudentViewImpl(consoleView);

        inputValidator = new InputValidatorIMPL();
        groupSelect = new GroupSelectImpl(groupDAO);
        courseSelect = new CourseSelectImpl(courseDAO);
        courseSelector = new CourseSelector(courseView);
        studentSelect = new StudentSelectImpl(studentDAO, studentCourseDAO);
        groupSelector = new GroupSelector(groupView);
        groupService = new GroupServiceImpl();
        courseService = new CourseServiceImpl();
        studentSelector = new StudentSelector(studentView);

    }

    public DemoDataInitializer getDemoDataInitializer() {
        return new DemoDataInitializer(getDatabaseMaintenanceDAO(), getDistributionService());
    }

    public UserInterface getUserInterface() {
        return new UserInterfaceImpl(consoleView, createMenuCommands());
    }

    private Map<String, Command> createMenuCommands() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("1", getGroupSearchCommand());
        commands.put("2", getStudentSearchCommand());
        commands.put("3", getAddNewStudentCommand());
        commands.put("4", getDeleteStudentCommand());
        commands.put("5", getAddStudentToCourseCommand());
        commands.put("6", getRemoveStudentFromCourseCommand());
        return commands;
    }

    private DatabaseMaintenanceDAO getDatabaseMaintenanceDAO() {
        return new DatabaseMaintenanceDAOIMPL(dbConnection);
    }

    private DistributionService getDistributionService() {
        return new DistributionServiceIMPL(
                new DBGeneratorIMPL(new DataReaderIMPL(propertiesLoader)),
                courseDAO,
                groupDAO,
                studentCourseDAO,
                studentDAO,
                new StudentGroupDistributionIMPL(),
                new StudentCourseDistributionIMPL());
    }

    private Command getGroupSearchCommand() {
        return new GroupSearchCommand(groupView, inputValidator, groupSelect);
    }

    private Command getStudentSearchCommand() {
        return new StudentSearchCommand(courseView, studentView, courseSelect, courseSelector, studentSelect);
    }

    private AddNewStudentCommand getAddNewStudentCommand() {
        return new AddNewStudentCommand(
                studentView,
                inputValidator,
                new AssignGroupHandlerImpl(groupSelect, groupSelector, groupView,
                        new AssignGroupAction(groupService, groupView),
                        new ReassignGroupAction(groupService, groupView),
                        new DeleteGroupAction(groupService, groupView)),
                new AssignCourseHandlerImpl(courseView, courseSelect, courseSelector,
                        new AssignCourseAction(courseView, courseService),
                        new ReassignCourseAction(courseView, courseService),
                        new DeleteCourseAction(courseView, courseService)),
                new SaveStudentHandlerImpl(studentView,
                        new StudentInsertImpl(studentDAO, studentCourseDAO, transactionManager)));
    }

    private DeleteStudentCommand getDeleteStudentCommand() {
        return new DeleteStudentCommand(
                studentView,
                studentSelect,
                studentSelector,
                new StudentDeleteImpl(studentDAO));
    }

    private AddStudentToCourseCommand getAddStudentToCourseCommand() {
        return new AddStudentToCourseCommand(
                studentView,
                courseView,
                studentSelect,
                studentSelector,
                courseSelect,
                new CourseAssignmentFlowHandler(courseView, courseSelector, courseService),
                new StudentCourseSaverImpl(
                        courseView,
                        new StudentCoursesInsertImpl(studentCourseDAO)));
    }

    private RemoveStudentFromCourseCommand getRemoveStudentFromCourseCommand() {
        return new RemoveStudentFromCourseCommand(
                studentView,
                courseView,
                studentSelect,
                studentSelector,
                new CourseRemovalFlowHandler(courseSelector, courseService, courseView),
                new StudentCourseRemoverImpl(
                        courseView,
                        new StudentCoursesDeleteImpl(studentCourseDAO)));
    }
}
