import java.time.LocalDate;


public class Enrollment {


    public static final double GRADE_A = 4.0;
    public static final double GRADE_B = 3.0;
    public static final double GRADE_C = 2.0;
    public static final double GRADE_D = 1.0;
    public static final double GRADE_F = 0.0;


    private Student   student;
    private Course    course;
    private double    gradePoints;
    private boolean   isGraded;
    private LocalDate enrollmentDate;
    private String    status;

    public Enrollment(Student student, Course course) {
        this.student        = student;
        this.course         = course;
        this.gradePoints    = 0.0;
        this.isGraded       = false;
        this.enrollmentDate = LocalDate.now();
        this.status         = "ACTIVE";
    }

    public void assignGrade(double gradePoints) {
        if (gradePoints < 0.0 || gradePoints > 4.0) {
            System.out.println("Invalid grade. Must be between 0.0 and 4.0");
            return;
        }
        this.gradePoints = gradePoints;
        this.isGraded    = true;
        this.status      = "COMPLETED";
        student.recalculateGPA();
        System.out.println("Grade " + getLetterGrade() + " assigned to "
                + student.getFullName() + " for "
                + course.getCourseName());
    }

    public void dropCourse() {
        this.status   = "DROPPED";
        this.isGraded = false;
    }


    public String getLetterGrade() {
        if (!isGraded)           return "N/A";
        if (gradePoints >= 3.7)  return "A";
        if (gradePoints >= 3.3)  return "A-";
        if (gradePoints >= 3.0)  return "B+";
        if (gradePoints >= 2.7)  return "B";
        if (gradePoints >= 2.3)  return "B-";
        if (gradePoints >= 2.0)  return "C+";
        if (gradePoints >= 1.7)  return "C";
        if (gradePoints >= 1.3)  return "C-";
        if (gradePoints >= 1.0)  return "D";
        return "F";
    }

    public String getSummary() {
        return course.getCourseCode() + " | "
                + course.getCourseName()  + " | "
                + "Grade: " + getLetterGrade() + " | "
                + "Status: " + status;
    }

    public Student   getStudent() {
        return student;
    }
    public Course    getCourse(){
        return course;
    }
    public double    getGradePoints() {
        return gradePoints;
    }
    public boolean   isGraded()  {
        return isGraded;
    }
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    public String  getStatus(){
        return status;
    }
}