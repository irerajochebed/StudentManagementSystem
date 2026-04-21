/**
 * Thrown when a student tries to enroll in a course that is already full.
 */
public class CourseFullException extends StudentManagementException {

    private String courseName;
    private int maxCapacity;

    public CourseFullException(String courseName, int maxCapacity) {
        super(
                "Course '" + courseName + "' is full. Maximum capacity: " + maxCapacity,
                "COURSE_FULL"
        );
        this.courseName = courseName;
        this.maxCapacity = maxCapacity;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}