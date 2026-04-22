import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Student extends Person {

    private String major;
    private double gpa;
    private int yearLevel;

    // List: one student can have MANY enrollments (one-to-many relationship).
    // List preserves insertion order so we can display courses in the order they were enrolled.
    private List<Enrollment> enrollments;

    // Set: a student must NOT enroll in the same course twice (unique relationship).
    // HashSet gives O(1) duplicate checks, replacing the old manual for-loop search.
    private Set<String> enrolledCourseCodes;

    public Student(String studentId, String firstName, String lastName,
                   String email, int age, String major, int yearLevel) {
        super(studentId, firstName, lastName, email, age);
        this.major = major;
        this.yearLevel = yearLevel;
        this.gpa = 0.0;
        this.enrollments = new ArrayList<>();
        this.enrolledCourseCodes = new HashSet<>();
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public void displayInfo() {
        System.out.println("========== Student Profile ==========");
        System.out.println("  ID      : " + getPersonId());
        System.out.println("  Name    : " + getFullName());
        System.out.println("  Email   : " + getEmail());
        System.out.println("  Age     : " + getAge());
        System.out.println("  Major   : " + major);
        System.out.println("  Year    : " + getYearLevelName());
        System.out.printf("  GPA     : %.2f%n", gpa);
        System.out.println("  Courses : " + enrollments.size() + " enrolled");
        System.out.println("=====================================");
    }

    // enrollInCourse now throws exceptions instead of printing
    public void enrollInCourse(Course course) {

        // Set.contains() replaces the old for-loop — O(1) duplicate check
        if (enrolledCourseCodes.contains(course.getCourseCode())) {
            throw new DuplicateEnrollmentException(getFullName(), course.getCourseName());
        }

        // Check if course is full
        if (course.isFull()) {
            throw new CourseFullException(course.getCourseName(), course.getMaxCapacity());
        }

        // All checks passed - enroll student
        Enrollment enrollment = new Enrollment(this, course);
        enrollments.add(enrollment);          // add to ordered List
        enrolledCourseCodes.add(course.getCourseCode()); // add to Set for fast duplicate check
        course.addEnrollment(enrollment);

        System.out.println("  SUCCESS: " + getFullName() + " enrolled in '" + course.getCourseName() + "'");
    }

    public void recalculateGPA() {
        double total = 0.0;
        int count = 0;

        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment e = enrollments.get(i);
            if (e.isGraded()) {
                total += e.getGradePoints();
                count++;
            }
        }

        this.gpa = (count > 0) ? total / count : 0.0;
    }

    public void displayEnrollments() {
        System.out.println("--- Enrollments for " + getFullName() + " ---");
        if (enrollments.isEmpty()) {
            System.out.println("  No courses enrolled.");
        } else {
            for (int i = 0; i < enrollments.size(); i++) {
                Enrollment e = enrollments.get(i);
                System.out.println("  " + (i + 1) + ". " + e.getSummary());
            }
        }
    }

    private String getYearLevelName() {
        switch (yearLevel) {
            case 1: return "1st Year (Freshman)";
            case 2: return "2nd Year (Sophomore)";
            case 3: return "3rd Year (Junior)";
            case 4: return "4th Year (Senior)";
            default: return "Year " + yearLevel;
        }
    }

    // Getters
    public String getMajor() { return major; }
    public double getGpa() { return gpa; }
    public int getYearLevel() { return yearLevel; }
    public List<Enrollment> getEnrollments() { return enrollments; }
    public Set<String> getEnrolledCourseCodes() { return enrolledCourseCodes; }
}