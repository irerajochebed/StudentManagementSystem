import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class Enrollment {

    private Student student;
    private Course course;
    private double gradePoints;
    private boolean isGraded;
    private LocalDate enrollmentDate;
    private String status;

    // Map<attemptLabel, gradePoints as String>: key-value relationship.
    // Tracks the history of grade attempts (e.g. "Attempt 1" -> "3.5").
    // LinkedHashMap preserves insertion order so history is shown chronologically.
    private Map<String, String> gradeHistory;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.gradePoints    = 0.0;
        this.isGraded       = false;
        this.enrollmentDate = LocalDate.now();
        this.status         = "ACTIVE";
        this.gradeHistory   = new LinkedHashMap<>(); // preserves insertion order
    }

    public void assignGrade(double gradePoints) {
        if (gradePoints < 0.0 || gradePoints > 4.0) {
            throw new InvalidGradeException(gradePoints);
        }
        this.gradePoints = gradePoints;
        this.isGraded    = true;
        this.status      = "COMPLETED";

        // Map: record this grade in history (key = attempt label, value = grade string)
        String attemptKey = "Attempt " + (gradeHistory.size() + 1);
        gradeHistory.put(attemptKey, String.valueOf(gradePoints)); // Map: add

        student.recalculateGPA();
        System.out.println("  Grade " + getLetterGrade() + " assigned to "
                + student.getFullName() + " for " + course.getCourseName());
    }

    // Map: retrieve the full grade history
    public void displayGradeHistory() {
        System.out.println("  Grade History for " + student.getFullName()
                + " in " + course.getCourseName() + ":");
        if (gradeHistory.isEmpty()) {
            System.out.println("    No grades recorded yet.");
        } else {
            for (Map.Entry<String, String> entry : gradeHistory.entrySet()) {
                System.out.println("    " + entry.getKey() + " -> " + entry.getValue()); // retrieve
            }
        }
    }

    // Map: remove the last grade attempt from history
    public void removeLastGradeRecord() {
        if (!gradeHistory.isEmpty()) {
            String lastKey = "Attempt " + gradeHistory.size();
            gradeHistory.remove(lastKey); // Map: remove
            System.out.println("  Removed " + lastKey + " from grade history.");
        }
    }

    public String getLetterGrade() {
        if (!isGraded) return "N/A";
        if (gradePoints >= 3.7) return "A";
        if (gradePoints >= 3.3) return "A-";
        if (gradePoints >= 3.0) return "B+";
        if (gradePoints >= 2.7) return "B";
        if (gradePoints >= 2.3) return "B-";
        if (gradePoints >= 2.0) return "C+";
        if (gradePoints >= 1.7) return "C";
        if (gradePoints >= 1.3) return "C-";
        if (gradePoints >= 1.0) return "D";
        return "F";
    }

    public String getSummary() {
        return course.getCourseCode() + " | " + course.getCourseName() + " | Grade: " + getLetterGrade() + " | Status: " + status;
    }

    // Getters
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public double getGradePoints() { return gradePoints; }
    public boolean isGraded() { return isGraded; }
    public String getStatus() { return status; }
    public Map<String, String> getGradeHistory() { return gradeHistory; }
}