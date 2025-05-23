CREATE TABLE groups (
    group_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    group_name VARCHAR(255) NOT NULL
);

CREATE TABLE students (
    student_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    group_id INT REFERENCES groups(group_id)
);

CREATE TABLE courses (
    course_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    course_name VARCHAR(255) NOT NULL,
    course_description VARCHAR(255) NOT NULL
);

CREATE TABLE student_courses (
    student_id INT REFERENCES students(student_id),
    course_id INT REFERENCES courses(course_id)
);