import java.util.ArrayList;
import java.util.List;

/**
 * Represents a university instructor.
 * Demonstrates: Inheritance + Polymorphism (different displayInfo from Student)
 */
public class Instructor extends Person {

    // Instructor-specific fields (Encapsulation)
    private String       specialization;
    private String       officeLocation;
    private double       salary;
    private List<Course> assignedCourses;

    // ----------------------------------------------------------------
    // Constructor
    // ----------------------------------------------------------------
    public Instructor(String instructorId, String firstName, String lastName,
                      String email, int age, String specialization,
                      String officeLocation, double salary) {
        super(instructorId, firstName, lastName, email, age);
        this.specialization  = specialization;
        this.officeLocation  = officeLocation;
        this.salary          = salary;
        this.assignedCourses = new ArrayList<>();
    }


    @Override
    public String getRole() {
        return "Instructor";
    }

    @Override
    public void displayInfo() {
        System.out.println("========== Instructor Profile ==========");
        System.out.println("  ID             : " + getPersonId());
        System.out.println("  Name           : " + getFullName());
        System.out.println("  Email          : " + getEmail());
        System.out.println("  Age            : " + getAge());
        System.out.println("  Specialization : " + specialization);
        System.out.println("  Office         : " + officeLocation);
        System.out.println("  Courses Taught : " + assignedCourses.size());
        System.out.println("========================================");
    }

    public void assignCourse(Course course) {
        boolean alreadyAssigned = false;

        for (int i = 0; i < assignedCourses.size(); i++) {
            if (assignedCourses.get(i).getCourseCode()
                    .equals(course.getCourseCode())) {
                alreadyAssigned = true;
                break;
            }
        }

        if (!alreadyAssigned) {
            assignedCourses.add(course);
            System.out.println("Course '" + course.getCourseName()
                    + "' assigned to " + getFullName());
        } else {
            System.out.println(getFullName() + " is already teaching "
                    + course.getCourseName());
        }
    }


    public void displayAssignedCourses() {
        System.out.println("--- Courses taught by " + getFullName() + " ---");

        if (assignedCourses.isEmpty()) {
            System.out.println("  No courses assigned.");
        } else {
            for (int i = 0; i < assignedCourses.size(); i++) {
                Course c = assignedCourses.get(i);
                System.out.println("  " + (i + 1) + ". "
                        + c.getCourseCode()
                        + " | " + c.getCourseName()
                        + " | Students: " + c.getEnrolledCount());
            }
        }
    }

    public String getSpecialization()  {
        return specialization;
    }
    public String getOfficeLocation()  {
        return officeLocation;
    }
    public double getSalary(){
        return salary;
    }
    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    public void setSpecialization(String s)  {
        this.specialization = s;
    }
    public void setOfficeLocation(String o)  {
        this.officeLocation = o;
    }
    public void setSalary(double salary) {

        if (salary > 0) this.salary = salary;
    }
}