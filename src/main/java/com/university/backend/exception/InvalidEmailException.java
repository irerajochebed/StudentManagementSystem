package com.university.backend.exception;

public class InvalidEmailException extends StudentManagementException {

    private String attemptedEmail;

    public InvalidEmailException(String attemptedEmail) {
        super(
                "Invalid email: '" + attemptedEmail + "'. Email must contain '@' and '.'",
                "INVALID_EMAIL"
        );
        this.attemptedEmail = attemptedEmail;
    }

    public String getAttemptedEmail() {
        return attemptedEmail;
    }
}
