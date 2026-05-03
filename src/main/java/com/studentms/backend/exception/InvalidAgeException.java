package com.studentms.backend.exception;

public class InvalidAgeException extends StudentManagementException {
    public InvalidAgeException(int age) {
        super("Invalid age: " + age + ". Age must be between 16 and 100.", "INVALID_AGE");
    }
}
