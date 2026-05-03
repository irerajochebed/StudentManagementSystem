package com.university.backend.exception;

public class DuplicateEnrollmentException extends StudentManagementException {

    private String studentName;
    private String courseName;

    public DuplicateEnrollmentException(String studentName, String courseName) {
        super(
                "Student '" + studentName + "' is already enrolled in '" + courseName + "'.",
                "DUPLICATE_ENROLLMENT"
        );
        this.studentName = studentName;
        this.courseName = courseName;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCourseName() {
        return courseName;
    }
}
