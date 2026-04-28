import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Department {


    private String departmentId;
    private String departmentName;
    private String building;

    private List<Instructor> instructors;
    private Map<String, Course> courseMap;
    private Set<String> enrolledStudentIds;


    public Department(String departmentId, String departmentName, String building) {
        this.departmentId   = departmentId;
        this.departmentName = departmentName;
        this.building       = building;
        this.instructors        = new ArrayList<>();
        this.courseMap          = new HashMap<>();
        this.enrolledStudentIds = new HashSet<>();
    }


    public void addInstructor(Instructor instructor) {
        instructors.add(instructor);
        System.out.println(instructor.getFullName()
                + " added to " + departmentName + " department.");
    }

    public void addCourse(Course course) {
        // Map.put: key = courseCode, value = Course object
        courseMap.put(course.getCourseCode(), course);
        System.out.println("Course '" + course.getCourseName()
                + "' added to " + departmentName + " department.");
    }

    // Retrieve a course by its code — O(1) Map lookup
    public Course getCourseByCode(String courseCode) {
        return courseMap.get(courseCode);
    }

    // Remove a course from the department by its code
    public void removeCourse(String courseCode) {
        Course removed = courseMap.remove(courseCode);
        if (removed != null) {
            System.out.println("Course '" + removed.getCourseName() + "' removed from " + departmentName + ".");
        }
    }

    // Call this after enrollments are made to keep the Set up to date
    public void refreshEnrolledStudents() {
        enrolledStudentIds.clear();
        for (Course c : courseMap.values()) {
            List<Enrollment> enrollments = c.getEnrollments();
            for (int i = 0; i < enrollments.size(); i++) {
                // Set.add automatically ignores duplicates
                enrolledStudentIds.add(enrollments.get(i).getStudent().getPersonId());
            }
        }
    }


    public void displayDepartmentSummary() {
        System.out.println("========================================");
        System.out.println("  DEPARTMENT : " + departmentName);
        System.out.println("  Building   : " + building);
        System.out.println("  Instructors: " + instructors.size());
        System.out.println("  Courses    : " + courseMap.size());
        refreshEnrolledStudents();
        System.out.println("  Unique Students Enrolled: " + enrolledStudentIds.size());
        System.out.println("----------------------------------------");

        System.out.println("  [Instructors]");
        for (int i = 0; i < instructors.size(); i++) {
            Instructor ins = instructors.get(i);
            System.out.println("    " + (i + 1) + ". "
                    + ins.getFullName()
                    + " (" + ins.getSpecialization() + ")");
        }

        System.out.println("  [Courses]");
        // Iterate over Map values to display all courses
        int index = 1;
        for (Map.Entry<String, Course> entry : courseMap.entrySet()) {
            Course c = entry.getValue();
            System.out.println("    " + index + ". "
                    + c.getCourseCode()
                    + " | " + c.getCourseName()
                    + " | Enrolled: " + c.getEnrolledCount()
                    + "/" + c.getMaxCapacity());
            index++;
        }

        System.out.println("========================================");
    }


    public String getDepartmentId()   { return departmentId;   }
    public String getDepartmentName() { return departmentName; }
    public String getBuilding()       { return building;       }
    public List<Instructor> getInstructors() { return instructors; }
    public Map<String, Course> getCourseMap() { return courseMap; }
    public Set<String> getEnrolledStudentIds() { return enrolledStudentIds; }

    public void setBuilding(String building) { this.building = building; }
}