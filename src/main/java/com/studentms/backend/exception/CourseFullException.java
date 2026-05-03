package com.studentms.backend.exception;

public class CourseFullException extends StudentManagementException {
    public CourseFullException(String courseName, int maxCapacity) {
        super("Course '" + courseName + "' is full. Maximum capacity: " + maxCapacity, "COURSE_FULL");
    }
}
