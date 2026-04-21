
public class InvalidGradeException extends StudentManagementException {

    private double attemptedGrade;

    public InvalidGradeException(double attemptedGrade) {
        super(
                "Invalid grade: " + attemptedGrade + ". Grade must be between 0.0 and 4.0.",
                "INVALID_GRADE"
        );
        this.attemptedGrade = attemptedGrade;
    }

    public double getAttemptedGrade() {
        return attemptedGrade;
    }
}