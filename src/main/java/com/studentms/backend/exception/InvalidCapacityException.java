package com.studentms.backend.exception;

public class InvalidCapacityException extends StudentManagementException {
    public InvalidCapacityException(int capacity) {
        super("Invalid capacity: " + capacity + ". Capacity must be at least 1.", "INVALID_CAPACITY");
    }
}
