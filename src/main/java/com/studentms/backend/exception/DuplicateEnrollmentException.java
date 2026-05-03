package com.studentms.backend.exception;

public class DuplicateEnrollmentException extends StudentManagementException {
    public DuplicateEnrollmentException(String studentName, String courseName) {
        super("Student '" + studentName + "' is already enrolled in '" + courseName + "'.", "DUPLICATE_ENROLLMENT");
    }
}
