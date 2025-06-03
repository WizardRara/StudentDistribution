package org.example.service.course;

import org.example.entity.Course;
import org.example.entity.Student;
import org.example.util.CourseUtils;

public class CourseServiceImpl implements CourseService {
    @Override
    public boolean assign(Student student, Course course) {
        if (student.getCourseList().size() >= 3) return false;
        if (CourseUtils.isCourseAssigned(student, course)) return false;
        student.getCourseList().add(course);
        return true;
    }

    @Override
    public boolean reassign(Student student, Course from, Course to) {
        if (from.getCourseId() == to.getCourseId()) return false;
        student.getCourseList().remove(from);
        student.getCourseList().add(to);
        return true;
    }

    @Override
    public boolean delete(Student student, int courseId) {
        return student.getCourseList().removeIf(c -> c.getCourseId() == courseId);
    }
}
