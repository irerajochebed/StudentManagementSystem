import java.util.ArrayList;
import java.util.List;

public class Instructor extends Person {

    private String specialization;
    private String officeLocation;
    private double salary;
    private List<Course> assignedCourses;

    public Instructor(String instructorId, String firstName, String lastName,
                      String email, int age, String specialization,
                      String officeLocation, double salary) {
        super(instructorId, firstName, lastName, email, age);
        this.specialization = specialization;
        this.officeLocation = officeLocation;
        this.salary = salary;
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
        System.out.println("  Specialization : " + specialization);
        System.out.println("  Office         : " + officeLocation);
        System.out.println("  Courses Taught : " + assignedCourses.size());
        System.out.println("========================================");
    }

    public void assignCourse(Course course) {
        assignedCourses.add(course);
    }

    public void displayAssignedCourses() {
        System.out.println("--- Courses taught by " + getFullName() + " ---");
        if (assignedCourses.isEmpty()) {
            System.out.println("  No courses assigned.");
        } else {
            for (int i = 0; i < assignedCourses.size(); i++) {
                Course c = assignedCourses.get(i);
                System.out.println("  " + (i + 1) + ". " + c.getCourseCode() + " | " + c.getCourseName() + " | Students: " + c.getEnrolledCount());
            }
        }
    }

    
    // Getters
    public String getSpecialization() { return specialization; }
    public String getOfficeLocation() { return officeLocation; }
    public double getSalary() { return salary; }
    public List<Course> getAssignedCourses() { return assignedCourses; }
}