package org.example.repository;

import org.example.entity.Group;
import org.example.exception.StudentInsertException;
import org.example.exception.StudentSelectException;
import org.example.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOIMPL implements StudentDAO {
    private final TransactionManager transactionManager;
    private static final String SQL_INSERT_STUDENTS =
            "INSERT INTO students (first_name, last_name, group_id) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_STUDENTS_BY_COURSE_ID = """
            SELECT s.student_id, s.first_name, s.last_name
            FROM students s
            JOIN student_courses sc ON s.student_id = sc.student_id
            WHERE sc.course_id = ?""";
    private static final String SQL_SELECT_ALL_STUDENTS_WITH_GROUPS = """
            SELECT s.student_id, s.first_name, s.last_name, g.group_id, g.group_name
            FROM students s
            LEFT JOIN groups g on g.group_id = s.group_id""";

    public StudentDAOIMPL(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Student> insertStudents(List<Student> students) {
        return transactionManager.doInTransaction(connection -> insertStudents(students, connection), false);
    }

    public List<Student> insertStudents(List<Student> students, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT_STUDENTS,
                Statement.RETURN_GENERATED_KEYS)) {
            for (Student student : students) {
                preparedStatement.setString(1, student.getFirstName());
                preparedStatement.setString(2, student.getLastName());
                if (student.getGroup() != null) {
                    preparedStatement.setInt(3, student.getGroup().getGroupId());
                } else {
                    preparedStatement.setNull(3, Types.INTEGER);
                }
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                for (Student student : students) {
                    if (generatedKeys.next()) {
                        student.setStudentId(generatedKeys.getInt(1));
                    } else {
                        throw new StudentInsertException("Failed to retrieve generated key for student: %s %s"
                                .formatted(student.getFirstName(), student.getLastName()));
                    }
                }
            }
            return students;
        }
    }

    @Override
    public List<Student> getStudentsByCourseId(int courseId) {
        return transactionManager.doInTransaction(connection -> {
            List<Student> students = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_STUDENTS_BY_COURSE_ID)) {
                preparedStatement.setInt(1, courseId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        students.add(new Student(
                                resultSet.getInt(1),
                                resultSet.getString(2),
                                resultSet.getString(3)));
                    }
                } catch (StudentSelectException e) {
                    throw new StudentSelectException("Student selection operation failed: " + e.getMessage());
                }
            }
            return students;
        }, true);
    }

    @Override
    public List<Student> selectAllStudentsWithGroups() {
        return transactionManager.doInTransaction(connection -> {
            List<Student> students = new ArrayList<>();
            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_STUDENTS_WITH_GROUPS)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int studentId = resultSet.getInt(1);
                        String firstName = resultSet.getString(2);
                        String lastName = resultSet.getString(3);
                        int groupId = resultSet.getInt(4);
                        String groupName = resultSet.getString(5);
                        Group group = resultSet.wasNull() ? null : new Group(groupId, groupName);
                        students.add(new Student(studentId, firstName, lastName, group));
                    }
                }
            }
            return students;
        }, false);
    }

    @Override
    public boolean deleteById(int studentId) {
        return transactionManager.doInTransaction(connection -> {
            String sqlDeleteFromStudentCourses = "DELETE FROM student_courses WHERE student_id = ?";
            String sqlDeleteFromStudents = "DELETE FROM students WHERE student_id = ?";
            try (PreparedStatement deleteCourses = connection.prepareStatement(sqlDeleteFromStudentCourses);
            PreparedStatement deleteStudents = connection.prepareStatement(sqlDeleteFromStudents)) {
                deleteCourses.setInt(1, studentId);
                deleteCourses.executeUpdate();
                deleteStudents.setInt(1, studentId);
                int affectedRows = deleteStudents.executeUpdate();
                return affectedRows == 1;
            }
        }, false);
    }
}
