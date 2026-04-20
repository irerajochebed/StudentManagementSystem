import java.util.ArrayList;
import java.util.List;


public class Course {


    private String           courseCode;
    private String           courseName;
    private String           description;
    private int              creditHours;
    private int              maxCapacity;
    private Instructor       instructor;
    private List<Enrollment> enrollments;
    private String           semester;


    public Course(String courseCode, String courseName, String description,
                  int creditHours, int maxCapacity, String semester) {
        this.courseCode  = courseCode;
        this.courseName  = courseName;
        this.description = description;
        this.creditHours = creditHours;
        this.maxCapacity = maxCapacity;
        this.semester    = semester;
        this.enrollments = new ArrayList<>();
    }


    public void assignInstructor(Instructor instructor) {
        this.instructor = instructor;
        instructor.assignCourse(this);
    }

    public void addEnrollment(Enrollment enrollment) {
        if (!isFull()) {
            enrollments.add(enrollment);
        }
    }

    public boolean isFull() {
        return enrollments.size() >= maxCapacity;
    }

    public int getEnrolledCount() {
        return enrollments.size();
    }

    public int getAvailableSeats() {
        return maxCapacity - enrollments.size();
    }

    public void displayCourseInfo() {
        System.out.println("========== Course Info ==========");
        System.out.println("  Code        : " + courseCode);
        System.out.println("  Name        : " + courseName);
        System.out.println("  Description : " + description);
        System.out.println("  Credits     : " + creditHours);
        System.out.println("  Semester    : " + semester);
        System.out.println("  Instructor  : "
                + (instructor != null ? instructor.getFullName() : "TBA"));
        System.out.println("  Capacity    : " + enrollments.size()
                + " / " + maxCapacity);
        System.out.println("  Status      : " + (isFull() ? "FULL" : "OPEN"));
        System.out.println("=================================");
    }


    public void displayRoster() {
        System.out.println("--- Student Roster: " + courseName + " ---");

        if (enrollments.isEmpty()) {
            System.out.println("  No students enrolled.");
        } else {
            for (int i = 0; i < enrollments.size(); i++) {
                Enrollment e = enrollments.get(i);
                System.out.println("  " + (i + 1) + ". "
                        + e.getStudent().getFullName()
                        + " | Grade: " + e.getLetterGrade());
            }
        }
    }


    public String getCourseCode()    {
        return courseCode;  }
    public String getCourseName()    {
        return courseName;  }
    public String getDescription()   {
        return description; }
    public int  getCreditHours()   {
        return creditHours; }
    public int  getMaxCapacity()   {
        return maxCapacity; }
    public Instructor getInstructor()    {
        return instructor;  }
    public String getSemester()      {
        return semester;    }
    public List<Enrollment> getEnrollments()   {
        return enrollments; }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setMaxCapacity(int maxCapacity) {
        if (maxCapacity > 0) this.maxCapacity = maxCapacity;
    }

    @Override
    public String toString() {
        return courseCode + " - " + courseName + " (" + semester + ")";
    }
}