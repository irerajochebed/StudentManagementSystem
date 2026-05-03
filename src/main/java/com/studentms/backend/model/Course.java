package com.studentms.backend.model;

import com.studentms.backend.exception.CourseFullException;
import com.studentms.backend.exception.InvalidCapacityException;

import java.util.ArrayList;
import java.util.List;

public class Course {

    private String courseCode;
    private String courseName;
    private String description;
    private int creditHours;
    private int maxCapacity;
    private Instructor instructor;
    private List<Enrollment> enrollments;
    private String semester;

    public Course(String courseCode, String courseName, String description,
                  int creditHours, int maxCapacity, String semester) {
        if (maxCapacity < 1) throw new InvalidCapacityException(maxCapacity);
        this.courseCode   = courseCode;
        this.courseName   = courseName;
        this.description  = description;
        this.creditHours  = creditHours;
        this.maxCapacity  = maxCapacity;
        this.semester     = semester;
        this.enrollments  = new ArrayList<>();
    }

    public void assignInstructor(Instructor instructor) {
        this.instructor = instructor;
        instructor.assignCourse(this);
    }

    public void addEnrollment(Enrollment enrollment) {
        if (isFull()) throw new CourseFullException(courseName, maxCapacity);
        enrollments.add(enrollment);
    }

    public boolean isFull()            { return enrollments.size() >= maxCapacity; }
    public int getEnrolledCount()      { return enrollments.size(); }
    public int getAvailableSeats()     { return maxCapacity - enrollments.size(); }

    public String getCourseCode()      { return courseCode; }
    public String getCourseName()      { return courseName; }
    public String getDescription()     { return description; }
    public int    getCreditHours()     { return creditHours; }
    public int    getMaxCapacity()     { return maxCapacity; }
    public Instructor getInstructor()  { return instructor; }
    public String getSemester()        { return semester; }
    public List<Enrollment> getEnrollments() { return enrollments; }

    @Override
    public String toString() {
        String ins = (instructor != null) ? " | Instructor: " + instructor.getFullName() : "";
        return courseCode + " - " + courseName + ins;
    }
}
