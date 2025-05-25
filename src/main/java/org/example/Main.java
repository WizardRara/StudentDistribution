package org.example;

import org.example.config.DBConnection;
import org.example.config.PropertiesLoader;
import org.example.repository.*;
import org.example.service.*;
import org.example.service.course.CourseSelect;
import org.example.service.course.CourseService;
import org.example.service.course.CourseServiceImpl;
import org.example.service.course.CourseSelectImpl;
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
import org.example.view.handler.addNewStudent.assignCourse.AssignCourseAction;
import org.example.view.handler.addNewStudent.assignCourse.DeleteCourseAction;
import org.example.view.handler.addNewStudent.assignCourse.ReassignCourseAction;
import org.example.view.handler.addNewStudent.assignGroup.AssignGroupAction;
import org.example.view.handler.addNewStudent.assignGroup.DeleteGroupAction;
import org.example.view.handler.addNewStudent.assignGroup.ReassignGroupAction;
import org.example.view.handler.addNewStudent.saveStudent.SaveStudentHandlerImpl;
import org.example.view.handler.addStudentToCourse.AddStudentToCourseHandlerImpl;
import org.example.view.handler.addStudentToCourse.CourseAssignmentFlowHandler;
import org.example.view.handler.addStudentToCourse.StudentCourseSaverImpl;
import org.example.view.handler.deleteStudent.DeleteStudentHandlerImpl;
import org.example.view.handler.findGroups.GroupSearchHandlerImpl;
import org.example.view.handler.findStudents.StudentSearchHandlerImpl;
import org.example.view.handler.removeStudentFromCourse.CourseRemovalFlowHandler;
import org.example.view.handler.removeStudentFromCourse.RemoveStudentFromCourseHandlerImpl;
import org.example.view.handler.removeStudentFromCourse.StudentCourseRemoverImpl;
import org.example.view.menu.UserInterface;
import org.example.view.menu.UserInterfaceImpl;
import org.example.view.handler.addNewStudent.AddNewStudentHandlerImpl;
import org.example.view.handler.addNewStudent.assignCourse.AssignCourseHandlerImpl;
import org.example.view.handler.addNewStudent.assignGroup.AssignGroupHandlerImpl;
import org.example.view.selector.CourseSelector;
import org.example.view.selector.GroupSelector;
import org.example.view.selector.StudentSelector;

import java.util.Properties;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        PropertiesLoader loader = new PropertiesLoader(new Properties());
        DBConnection connection = new DBConnection(loader);
        TransactionManager transactionManager = new TransactionManager(connection);
        CourseDAO courseDAO = new CourseDAOIMPL(transactionManager);
        GroupDAO groupDAO = new GroupDAOIMPL(transactionManager);
        StudentDAO studentDAO = new StudentDAOIMPL(transactionManager);
        StudentCourseDAO studentCourseDAO = new StudentCourseDAOIMPL(transactionManager);

        DatabaseMaintenanceDAOIMPL dbMai = new DatabaseMaintenanceDAOIMPL(connection);
        dbMai.clearDatabase();

        DistributionService distributionService = new DistributionServiceIMPL(
                new DBGeneratorIMPL(new DataReaderIMPL(loader)),
                courseDAO,
                groupDAO,
                studentCourseDAO,
                studentDAO,
                new StudentGroupDistributionIMPL(),
                new StudentCourseDistributionIMPL());
        distributionService.distributeAll();

        ConsoleView consoleView = new ConsoleViewImpl(new Scanner(System.in));
        CourseView courseView = new CourseViewImpl(consoleView);
        GroupView groupView = new GroupViewImpl(consoleView);
        StudentView studentView = new StudentViewImpl(consoleView);
        InputValidator inputValidator = new InputValidatorIMPL();
        GroupSelect groupSelect = new GroupSelectImpl(groupDAO);
        CourseSelect courseSelect = new CourseSelectImpl(courseDAO);
        CourseSelector courseSelector = new CourseSelector(courseView);
        StudentSelect studentSelect = new StudentSelectImpl(studentDAO, studentCourseDAO);
        GroupService groupService = new GroupServiceImpl();
        CourseService courseService = new CourseServiceImpl();
        StudentSelector studentSelector = new StudentSelector(studentView);


        UserInterface userInterface = new UserInterfaceImpl(
                consoleView,
                new GroupSearchHandlerImpl(groupView, inputValidator, groupSelect),
                new StudentSearchHandlerImpl(courseView, studentView, courseSelect, courseSelector, studentSelect),
                new AddNewStudentHandlerImpl(
                        studentView,
                        inputValidator,
                        new AssignGroupHandlerImpl(
                                groupSelect,
                                new GroupSelector(groupView),
                                groupView,
                                new AssignGroupAction(groupService, groupView),
                                new ReassignGroupAction(groupService, groupView),
                                new DeleteGroupAction(groupService, groupView)),
                        new AssignCourseHandlerImpl(
                                courseView,
                                courseSelect,
                                courseSelector,
                                new AssignCourseAction(courseView, courseService),
                                new ReassignCourseAction(courseView, courseService),
                                new DeleteCourseAction(courseView, courseService)),
                        new SaveStudentHandlerImpl(
                                studentView,
                                new StudentInsertImpl(studentDAO, studentCourseDAO, transactionManager))),
                new DeleteStudentHandlerImpl(
                        studentView,
                        studentSelect,
                        studentSelector,
                        new StudentDeleteImpl(studentDAO)),
                new AddStudentToCourseHandlerImpl(
                        studentView,
                        courseView,
                        studentSelect,
                        studentSelector,
                        courseSelect,
                        new CourseAssignmentFlowHandler(courseView, courseSelector, courseService),
                        new StudentCourseSaverImpl(
                                courseView,
                                new StudentCoursesInsertImpl(studentCourseDAO))),
                new RemoveStudentFromCourseHandlerImpl(
                        studentView,
                        courseView,
                        studentSelect,
                        studentSelector,
                        new CourseRemovalFlowHandler(courseSelector, courseService, courseView),
                        new StudentCourseRemoverImpl(
                                courseView,
                                new StudentCoursesDeleteImpl(studentCourseDAO))));
        userInterface.displayUserFunctions();

    }
}
//docker-compose up -d
//docker exec -it postgres_student_distribution psql -U myuser -d mydatabase