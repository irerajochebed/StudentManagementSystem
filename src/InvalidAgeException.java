
public class InvalidAgeException extends StudentManagementException {

    private int attemptedAge;

    public InvalidAgeException(int attemptedAge) {
        super(
                "Invalid age: " + attemptedAge + ". Age must be between 16 and 100.",
                "INVALID_AGE"
        );
        this.attemptedAge = attemptedAge;
    }
    

    public int getAttemptedAge() {
        return attemptedAge;
    }
}