package com.studentms.backend.model;

import com.studentms.backend.exception.CourseFullException;
import com.studentms.backend.exception.DuplicateEnrollmentException;

import java.util.ArrayList;
import java.util.List;

public class Student extends Person {

    private String major;
    private double gpa;
    private int    yearLevel;
    private List<Enrollment> enrollments;

    public Student(String studentId, String firstName, String lastName,
                   String email, int age, String major, int yearLevel) {
        super(studentId, firstName, lastName, email, age);
        this.major       = major;
        this.yearLevel   = yearLevel;
        this.gpa         = 0.0;
        this.enrollments = new ArrayList<>();
    }

    @Override public String getRole() { return "Student"; }

    @Override
    public void displayInfo() {
        System.out.println("ID: " + getPersonId() + " | " + getFullName()
                + " | Major: " + major + " | GPA: " + gpa);
    }

    public void enrollInCourse(Course course) {
        for (Enrollment e : enrollments) {
            if (e.getCourse().getCourseCode().equals(course.getCourseCode()))
                throw new DuplicateEnrollmentException(getFullName(), course.getCourseName());
        }
        if (course.isFull()) throw new CourseFullException(course.getCourseName(), course.getMaxCapacity());

        Enrollment enrollment = new Enrollment(this, course);
        enrollments.add(enrollment);
        course.addEnrollment(enrollment);
    }

    public void recalculateGPA() {
        double total = 0.0;
        int count = 0;
        for (Enrollment e : enrollments) {
            if (e.isGraded()) { total += e.getGradePoints(); count++; }
        }
        this.gpa = (count > 0) ? total / count : 0.0;
    }

    public String getMajor()                   { return major; }
    public double getGpa()                     { return gpa; }
    public int    getYearLevel()               { return yearLevel; }
    public List<Enrollment> getEnrollments()   { return enrollments; }
}
