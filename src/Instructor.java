import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Instructor extends Person {

    private String specialization;
    private String officeLocation;
    private double salary;

    // List: one instructor teaches MANY courses (one-to-many relationship).
    // List keeps insertion order so we display courses in the order they were assigned.
    private List<Course> assignedCourses;

    // Map<semester, List<Course>>: key-value + combined collection (bonus).
    // Groups courses by semester — one semester maps to MANY courses.
    // This is a Map of Lists, showing a real nested relationship.
    private Map<String, List<Course>> coursesBySemester;

    public Instructor(String instructorId, String firstName, String lastName,
                      String email, int age, String specialization,
                      String officeLocation, double salary) {
        super(instructorId, firstName, lastName, email, age);
        this.specialization = specialization;
        this.officeLocation = officeLocation;
        this.salary = salary;
        this.assignedCourses    = new ArrayList<>();
        this.coursesBySemester  = new HashMap<>();
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
        assignedCourses.add(course); // List: add

        // Map<semester, List<Course>>: group this course under its semester key
        // If the semester key doesn't exist yet, create a new List for it first
        String sem = course.getSemester();
        if (!coursesBySemester.containsKey(sem)) {
            coursesBySemester.put(sem, new ArrayList<>());
        }
        coursesBySemester.get(sem).add(course); // retrieve list by key, then add to it
    }

    // Map: remove a course from the semester grouping by semester key
    public void removeSemester(String semester) {
        coursesBySemester.remove(semester); // Map: remove by key
    }

    public void displayCoursesBySemester() {
        System.out.println("--- Courses by Semester for " + getFullName() + " ---");
        if (coursesBySemester.isEmpty()) {
            System.out.println("  No courses.");
            return;
        }
        // Iterate over Map entries: each key is a semester, value is a List of courses
        for (Map.Entry<String, List<Course>> entry : coursesBySemester.entrySet()) {
            System.out.println("  Semester: " + entry.getKey());
            List<Course> semCourses = entry.getValue(); // Map: retrieve
            for (int i = 0; i < semCourses.size(); i++) {
                System.out.println("    " + (i + 1) + ". " + semCourses.get(i).getCourseCode()
                        + " | " + semCourses.get(i).getCourseName());
            }
        }
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
    public Map<String, List<Course>> getCoursesBySemester() { return coursesBySemester; }
}