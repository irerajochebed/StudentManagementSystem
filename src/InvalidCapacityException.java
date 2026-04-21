
public class InvalidCapacityException extends StudentManagementException {

    private int attemptedCapacity;

    public InvalidCapacityException(int attemptedCapacity) {
        super(
                "Invalid capacity: " + attemptedCapacity + ". Capacity must be at least 1.",
                "INVALID_CAPACITY"
        );
        this.attemptedCapacity = attemptedCapacity;
    }

    public int getAttemptedCapacity() {
        return attemptedCapacity;
    }
}