package com.university.backend.exception;

public class StudentManagementException extends RuntimeException {

    private String errorCode;

    public StudentManagementException(String message) {
        super(message);
        this.errorCode = "SMS_ERROR";
    }

    public StudentManagementException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "[" + errorCode + "] " + getMessage();
    }
}
