package com.studentms.backend.exception;

public class StudentManagementException extends RuntimeException {

    private String errorCode;

    public StudentManagementException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() { return errorCode; }
}
