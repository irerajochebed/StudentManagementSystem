import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Course {

    private String courseCode;
    private String courseName;
    private String description;
    private int creditHours;
    private int maxCapacity;
    private Instructor instructor;
    private String semester;

    // List: one course has MANY enrollments (one-to-many relationship).
    // Order matters — we display students in the order they enrolled.
    private List<Enrollment> enrollments;

    // Set: stores unique student IDs enrolled in this course (unique relationship).
    // Prevents counting the same student twice and gives O(1) membership check.
    private Set<String> enrolledStudentIds;

    // Map<studentId, remark>: key-value relationship — each student has one remark.
    // Useful for instructor notes per student. Map ensures one remark per student.
    private Map<String, String> studentRemarks;

    // Constructor validates capacity
    public Course(String courseCode, String courseName, String description,
                  int creditHours, int maxCapacity, String semester) {

        // Validate capacity
        if (maxCapacity < 1) {
            throw new InvalidCapacityException(maxCapacity);
        }

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.description = description;
        this.creditHours = creditHours;
        this.maxCapacity = maxCapacity;
        this.semester          = semester;
        this.enrollments        = new ArrayList<>();
        this.enrolledStudentIds = new HashSet<>();
        this.studentRemarks     = new HashMap<>();
    }

    public void assignInstructor(Instructor instructor) {
        this.instructor = instructor;
        instructor.assignCourse(this);
    }

    public void addEnrollment(Enrollment enrollment) {
        if (isFull()) {
            throw new CourseFullException(courseName, maxCapacity);
        }
        enrollments.add(enrollment);                                    // List: add
        enrolledStudentIds.add(enrollment.getStudent().getPersonId()); // Set: add unique ID
    }

    // Map: add a remark for a student (key = studentId, value = remark text)
    public void addRemark(String studentId, String remark) {
        studentRemarks.put(studentId, remark);
    }

    // Map: retrieve a remark by student ID
    public String getRemark(String studentId) {
        return studentRemarks.getOrDefault(studentId, "No remark");
    }

    // Map: remove a remark by student ID
    public void removeRemark(String studentId) {
        studentRemarks.remove(studentId);
    }

    // Set: check if a student is enrolled using their ID
    public boolean isStudentEnrolled(String studentId) {
        return enrolledStudentIds.contains(studentId); // Set: retrieve (contains check)
    }

    public boolean isFull() {
        return enrollments.size() >= maxCapacity;
    }

    public int getEnrolledCount() { return enrollments.size(); }
    public int getAvailableSeats() { return maxCapacity - enrollments.size(); }

    public void displayCourseInfo() {
        System.out.println("========== Course Info ==========");
        System.out.println("  Code       : " + courseCode);
        System.out.println("  Name       : " + courseName);
        System.out.println("  Credits    : " + creditHours);
        System.out.println("  Semester   : " + semester);
        System.out.println("  Instructor : " + (instructor != null ? instructor.getFullName() : "TBA"));
        System.out.println("  Capacity   : " + enrollments.size() + " / " + maxCapacity);
        System.out.println("  Status     : " + (isFull() ? "FULL" : "OPEN"));
        System.out.println("=================================");
    }

    public void displayRoster() {
        System.out.println("--- Roster: " + courseName + " ---");
        if (enrollments.isEmpty()) {
            System.out.println("  No students enrolled.");
        } else {
            for (int i = 0; i < enrollments.size(); i++) {
                Enrollment e = enrollments.get(i);
                System.out.println("  " + (i + 1) + ". " + e.getStudent().getFullName() + " | Grade: " + e.getLetterGrade());
            }
        }
    }

    // Getters
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public String getDescription() { return description; }
    public int getCreditHours() { return creditHours; }
    public int getMaxCapacity() { return maxCapacity; }
    public Instructor getInstructor() { return instructor; }
    public String getSemester() { return semester; }
    public List<Enrollment> getEnrollments() { return enrollments; }
    public Set<String> getEnrolledStudentIds() { return enrolledStudentIds; }
    public Map<String, String> getStudentRemarks() { return studentRemarks; }

    @Override
    public String toString() {
        return courseCode + " - " + courseName;
    }
}