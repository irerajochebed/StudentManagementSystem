import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        // ── SETUP ──────────────────────────────────────────────────
        Department csDept   = new Department("DEPT-CS",   "Computer Science", "Tech Hall A");
        Department mathDept = new Department("DEPT-MATH", "Mathematics",      "Science Block B");

        Instructor dr_smith = new Instructor("INS-001", "John",  "Smith", "j.smith@uni.edu", 45, "Algorithms & Data Structures", "Room 201", 85000.0);
        Instructor dr_patel = new Instructor("INS-002", "Priya", "Patel", "p.patel@uni.edu", 38, "Artificial Intelligence",      "Room 305", 90000.0);
        Instructor dr_jones = new Instructor("INS-003", "Emily", "Jones", "e.jones@uni.edu", 52, "Calculus & Linear Algebra",    "Room 110", 78000.0);

        csDept.addInstructor(dr_smith);
        csDept.addInstructor(dr_patel);
        mathDept.addInstructor(dr_jones);

        Course dataStructures = new Course("CS101",   "Data Structures",    "Arrays, Linked Lists, Trees", 3, 3,  "Fall 2025");
        Course aiCourse       = new Course("CS301",   "Introduction to AI", "Search, ML, Neural Networks", 3, 30, "Fall 2025");
        Course calculus       = new Course("MATH101", "Calculus I",         "Limits, Derivatives",         4, 25, "Fall 2025");

        dataStructures.assignInstructor(dr_smith);
        aiCourse.assignInstructor(dr_patel);
        calculus.assignInstructor(dr_jones);

        csDept.addCourse(dataStructures);
        csDept.addCourse(aiCourse);
        mathDept.addCourse(calculus);

        Student alice   = new Student("STU-001", "Alice",   "Johnson", "alice@uni.edu",   20, "Computer Science", 2);
        Student bob     = new Student("STU-002", "Bob",     "Williams","bob@uni.edu",     21, "Computer Science", 2);
        Student charlie = new Student("STU-003", "Charlie", "Brown",   "charlie@uni.edu", 19, "Mathematics",      1);
        Student diana   = new Student("STU-004", "Diana",   "Prince",  "diana@uni.edu",   22, "Computer Science", 3);

        // ── EXCEPTION HANDLING DEMOS ───────────────────────────────
        System.out.println("=== Exception Handling ===");
        try { new Student("X", "Bad", "Email", "bademail", 20, "CS", 1); }
        catch (InvalidEmailException e)    { System.out.println("InvalidEmail    : " + e.getMessage()); }

        try { new Student("X", "Too", "Young", "y@uni.edu", 10, "CS", 1); }
        catch (InvalidAgeException e)      { System.out.println("InvalidAge      : " + e.getMessage()); }

        try { new Course("X", "Bad", "Desc", 3, 0, "Fall 2025"); }
        catch (InvalidCapacityException e) { System.out.println("InvalidCapacity : " + e.getMessage()); }

        // ── ENROLLMENTS ────────────────────────────────────────────
        System.out.println("\n=== Enrollments ===");
        alice.enrollInCourse(dataStructures); alice.enrollInCourse(aiCourse);
        bob.enrollInCourse(dataStructures);   bob.enrollInCourse(aiCourse);
        charlie.enrollInCourse(dataStructures); charlie.enrollInCourse(calculus);
        diana.enrollInCourse(aiCourse);

        try { diana.enrollInCourse(dataStructures); }           // course full
        catch (CourseFullException e)          { System.out.println("CourseFull      : " + e.getMessage()); }

        try { alice.enrollInCourse(aiCourse); }                 // duplicate
        catch (DuplicateEnrollmentException e) { System.out.println("Duplicate       : " + e.getMessage()); }

        // ── GRADES ─────────────────────────────────────────────────
        System.out.println("\n=== Grades ===");
        try { alice.getEnrollments().get(0).assignGrade(5.0); } // above 4.0
        catch (InvalidGradeException e) { System.out.println("InvalidGrade    : " + e.getMessage()); }

        try { bob.getEnrollments().get(0).assignGrade(-1.0); }  // negative
        catch (InvalidGradeException e) { System.out.println("InvalidGrade    : " + e.getMessage()); }

        // valid grades
        alice.getEnrollments().get(0).assignGrade(3.7);
        alice.getEnrollments().get(1).assignGrade(3.3);
        bob.getEnrollments().get(0).assignGrade(2.3);
        bob.getEnrollments().get(1).assignGrade(3.0);
        charlie.getEnrollments().get(1).assignGrade(4.0);

        try { new Student("X", "Test", "User", "t@uni.edu", 150, "CS", 1); } // invalid age
        catch (InvalidAgeException e) { System.out.println("InvalidAge      : " + e.getMessage()); }

        // ── FINAL REPORT ───────────────────────────────────────────
        System.out.println("\n=== Final Report ===");
        alice.displayInfo();   alice.displayEnrollments();
        bob.displayInfo();     bob.displayEnrollments();
        charlie.displayInfo(); charlie.displayEnrollments();

        dataStructures.displayRoster(); aiCourse.displayRoster(); calculus.displayRoster();
        dataStructures.displayCourseInfo(); aiCourse.displayCourseInfo();

        dr_smith.displayInfo(); dr_smith.displayAssignedCourses();
        dr_patel.displayInfo(); dr_patel.displayAssignedCourses();

        csDept.displayDepartmentSummary();
        mathDept.displayDepartmentSummary();

        // ── COLLECTIONS DEMO ───────────────────────────────────────
        System.out.println("\n=== Collections Demo ===");

        // List — one student has many enrollments
        System.out.println("[List] Alice enrollments: " + alice.getEnrollments().size() + " courses");

        // Set — unique course codes, rejects duplicates
        System.out.println("[Set]  Alice course codes: " + alice.getEnrolledCourseCodes());
        System.out.println("       add CS101 again -> added? " + alice.getEnrolledCourseCodes().add("CS101"));

        // Map — student remarks per course
        dataStructures.addRemark("STU-001", "Excellent");
        System.out.println("[Map]  Remark STU-001: " + dataStructures.getRemark("STU-001"));
        dataStructures.removeRemark("STU-001");
        System.out.println("       after remove: " + dataStructures.getStudentRemarks());

        // Map<String, List> — instructor courses grouped by semester
        System.out.println("[Map<String,List>] Dr. Smith by semester:");
        dr_smith.displayCoursesBySemester();

        // Map — department course lookup by code
        System.out.println("[Map]  CS301 lookup: " + csDept.getCourseByCode("CS301").getCourseName());

        // Set — unique students across department
        csDept.refreshEnrolledStudents();
        System.out.println("[Set]  Unique students in CS Dept: " + csDept.getEnrolledStudentIds());

        // Map — enrollment grade history
        System.out.println("[Map]  Alice grade history in DS:");
        alice.getEnrollments().get(0).displayGradeHistory();

        // ── FILE I/O DEMO ──────────────────────────────────────────
        System.out.println("\n=== File I/O Demo ===");

        List<Student> allStudents = new ArrayList<>();
        allStudents.add(alice); allStudents.add(bob); allStudents.add(charlie); allStudents.add(diana);

        List<Course> allCourses = new ArrayList<>();
        allCourses.add(dataStructures); allCourses.add(aiCourse); allCourses.add(calculus);

        // Save
        DataManager.saveStudents(allStudents);
        DataManager.saveCourses(allCourses);
        DataManager.saveEnrollments(allStudents);

        // Load back
        System.out.println();
        List<Student> loadedStudents = DataManager.loadStudents();
        for (int i = 0; i < loadedStudents.size(); i++) {
            Student s = loadedStudents.get(i);
            System.out.println("  " + s.getPersonId() + " | " + s.getFullName() + " | " + s.getMajor());
        }

        System.out.println();
        List<Course> loadedCourses = DataManager.loadCourses();
        for (int i = 0; i < loadedCourses.size(); i++) {
            Course c = loadedCourses.get(i);
            System.out.println("  " + c.getCourseCode() + " | " + c.getCourseName() + " | " + c.getCreditHours() + " credits");
        }

        System.out.println();
        DataManager.loadAndDisplayEnrollments();
    }
}
