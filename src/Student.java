import java.util.ArrayList;
import java.util.List;


public class Student extends Person {


    private String           major;
    private double           gpa;
    private int              yearLevel;
    private List<Enrollment> enrollments;


    public Student(String studentId, String firstName, String lastName,
                   String email, int age, String major, int yearLevel) {
        super(studentId, firstName, lastName, email, age);
        this.major       = major;
        this.yearLevel   = yearLevel;
        this.gpa         = 0.0;
        this.enrollments = new ArrayList<>();
    }

    @Override
    public String getRole() {
        return "Student";
    }

    @Override
    public void displayInfo() {
        System.out.println("========== Student Profile ==========");
        System.out.println("  ID        : " + getPersonId());
        System.out.println("  Name      : " + getFullName());
        System.out.println("  Email     : " + getEmail());
        System.out.println("  Age       : " + getAge());
        System.out.println("  Major     : " + major);
        System.out.println("  Year      : " + getYearLevelName());
        System.out.printf ("  GPA       : %.2f%n", gpa);
        System.out.println("  Courses   : " + enrollments.size() + " enrolled");
        System.out.println("=====================================");
    }


    public boolean enrollInCourse(Course course) {


        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment e = enrollments.get(i);
            if (e.getCourse().getCourseCode().equals(course.getCourseCode())) {
                System.out.println(getFullName() + " is already enrolled in "
                        + course.getCourseName());
                return false;
            }
        }


        if (course.isFull()) {
            System.out.println("Cannot enroll: " + course.getCourseName()
                    + " is already full.");
            return false;
        }

        Enrollment enrollment = new Enrollment(this, course);
        enrollments.add(enrollment);
        course.addEnrollment(enrollment);

        System.out.println(getFullName() + " successfully enrolled in "
                + course.getCourseName());
        return true;
    }


    public void recalculateGPA() {
        double total = 0.0;
        int    count = 0;

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
            case 1:  return "1st Year (Freshman)";
            case 2:  return "2nd Year (Sophomore)";
            case 3:  return "3rd Year (Junior)";
            case 4:  return "4th Year (Senior)";
            default: return "Year " + yearLevel;
        }
    }


    public String  getMajor() {
        return major;
    }
    public double   getGpa(){
        return gpa;
    }
    public int getYearLevel(){
        return yearLevel;
    }
    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setMajor(String major){
        this.major = major;
    }
    public void setYearLevel(int yearLevel) {
        if (yearLevel >= 1 && yearLevel <= 6) {
            this.yearLevel = yearLevel;
        }
    }
}