import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        System.out.println("==========================================");
        System.out.println("  UNIVERSITY STUDENT MANAGEMENT SYSTEM");
        System.out.println("  With Exception Handling");
        System.out.println("==========================================\n");


        System.out.println("------------------------------------------");
        System.out.println("SCENARIO 1: Create Student with Invalid Email");
        System.out.println("------------------------------------------");

        try {
            Student badEmail = new Student(
                    "STU-BAD", "Bad", "Email",
                    "notanemail",  // INVALID - no @ or .
                    20, "CS", 1
            );
        } catch (InvalidEmailException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
            System.out.println("  Attempted Email: " + e.getAttemptedEmail());
        }
        System.out.println();

        System.out.println("------------------------------------------");
        System.out.println("SCENARIO 2: Create Student with Invalid Age");
        System.out.println("------------------------------------------");

        try {
            Student tooYoung = new Student(
                    "STU-YOUNG", "Too", "Young",
                    "young@uni.edu",
                    10,  // INVALID - must be 16-100
                    "CS", 1
            );
        } catch (InvalidAgeException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
            System.out.println("  Attempted Age: " + e.getAttemptedAge());
        }
        System.out.println();


        System.out.println("------------------------------------------");
        System.out.println("SCENARIO 3: Create Course with Zero Capacity");
        System.out.println("------------------------------------------");

        try {
            Course badCourse = new Course(
                    "CS999", "Bad Course", "Description",
                    3, 0, "Fall 2025"  // INVALID - capacity must be >= 1
            );
        } catch (InvalidCapacityException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
            System.out.println("  Attempted Capacity: " + e.getAttemptedCapacity());
        }
        System.out.println();


        System.out.println("------------------------------------------");
        System.out.println("SETUP: Creating Valid Objects");
        System.out.println("------------------------------------------\n");

        Department csDept = new Department("DEPT-CS", "Computer Science", "Tech Hall A");
        Department mathDept = new Department("DEPT-MATH", "Mathematics", "Science Block B");

        Instructor dr_smith = new Instructor(
                "INS-001", "John", "Smith", "j.smith@uni.edu",
                45, "Algorithms & Data Structures", "Tech Hall A - Room 201", 85000.0
        );
        Instructor dr_patel = new Instructor(
                "INS-002", "Priya", "Patel", "p.patel@uni.edu",
                38, "Artificial Intelligence", "Tech Hall A - Room 305", 90000.0
        );
        Instructor dr_jones = new Instructor(
                "INS-003", "Emily", "Jones", "e.jones@uni.edu",
                52, "Calculus & Linear Algebra", "Science Block B - Room 110", 78000.0
        );

        csDept.addInstructor(dr_smith);
        csDept.addInstructor(dr_patel);
        mathDept.addInstructor(dr_jones);

        // Create courses with capacity 3 to test FULL scenario
        Course dataStructures = new Course(
                "CS101", "Data Structures",
                "Arrays, Linked Lists, Trees, Graphs", 3, 3, "Fall 2025"
        );
        Course aiCourse = new Course(
                "CS301", "Introduction to AI",
                "Search, ML basics, Neural Networks", 3, 30, "Fall 2025"
        );
        Course calculus = new Course(
                "MATH101", "Calculus I",
                "Limits, Derivatives, Integrals", 4, 25, "Fall 2025"
        );

        dataStructures.assignInstructor(dr_smith);
        aiCourse.assignInstructor(dr_patel);
        calculus.assignInstructor(dr_jones);

        csDept.addCourse(dataStructures);
        csDept.addCourse(aiCourse);
        mathDept.addCourse(calculus);

        Student alice = new Student("STU-001", "Alice", "Johnson", "alice@uni.edu", 20, "Computer Science", 2);
        Student bob = new Student("STU-002", "Bob", "Williams", "bob@uni.edu", 21, "Computer Science", 2);
        Student charlie = new Student("STU-003", "Charlie", "Brown", "charlie@uni.edu", 19, "Mathematics", 1);
        Student diana = new Student("STU-004", "Diana", "Prince", "diana@uni.edu", 22, "Computer Science", 3);

        System.out.println("All valid objects created successfully.\n");


        System.out.println("------------------------------------------");
        System.out.println("SCENARIO 4: Valid Enrollments");
        System.out.println("------------------------------------------");

        try {
            alice.enrollInCourse(dataStructures);
            alice.enrollInCourse(aiCourse);
            bob.enrollInCourse(dataStructures);
            bob.enrollInCourse(aiCourse);
            charlie.enrollInCourse(dataStructures);  // This fills the course (capacity = 3)
            charlie.enrollInCourse(calculus);
            diana.enrollInCourse(aiCourse);
        } catch (StudentManagementException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
        }
        System.out.println();


        System.out.println("------------------------------------------");
        System.out.println("SCENARIO 5: Enroll in a Full Course");
        System.out.println("------------------------------------------");

        try {
            // dataStructures is now FULL (3/3 students enrolled)
            diana.enrollInCourse(dataStructures);
        } catch (CourseFullException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
            System.out.println("  Course: " + e.getCourseName());
            System.out.println("  Max Capacity: " + e.getMaxCapacity());
        } catch (StudentManagementException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
        }
        System.out.println();


        System.out.println("------------------------------------------");
        System.out.println("SCENARIO 6: Duplicate Enrollment");
        System.out.println("------------------------------------------");

        try {
            // Alice is already enrolled in aiCourse
            alice.enrollInCourse(aiCourse);
        } catch (DuplicateEnrollmentException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
            System.out.println("  Student: " + e.getStudentName());
            System.out.println("  Course: " + e.getCourseName());
        } catch (StudentManagementException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
        }
        System.out.println();


        System.out.println("------------------------------------------");
        System.out.println("SCENARIO 7: Assign Invalid Grade (above 4.0)");
        System.out.println("------------------------------------------");

        try {
            Enrollment aliceDS = alice.getEnrollments().get(0);
            aliceDS.assignGrade(5.0);  // INVALID - max is 4.0
        } catch (InvalidGradeException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
            System.out.println("  Attempted Grade: " + e.getAttemptedGrade());
        }
        System.out.println();


        System.out.println("------------------------------------------");
        System.out.println("SCENARIO 8: Assign Invalid Grade (negative)");
        System.out.println("------------------------------------------");

        try {
            Enrollment bobDS = bob.getEnrollments().get(0);
            bobDS.assignGrade(-1.0);  // INVALID - must be >= 0.0
        } catch (InvalidGradeException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
            System.out.println("  Error Code: " + e.getErrorCode());
            System.out.println("  Attempted Grade: " + e.getAttemptedGrade());
        }
        System.out.println();


        System.out.println("------------------------------------------");
        System.out.println("SCENARIO 9: Assign Valid Grades");
        System.out.println("------------------------------------------");

        try {
            // Alice grades
            for (int i = 0; i < alice.getEnrollments().size(); i++) {
                Enrollment e = alice.getEnrollments().get(i);
                if (e.getCourse().getCourseCode().equals("CS101")) {
                    e.assignGrade(3.7);
                } else if (e.getCourse().getCourseCode().equals("CS301")) {
                    e.assignGrade(3.3);
                }
            }

            // Bob grades
            for (int i = 0; i < bob.getEnrollments().size(); i++) {
                Enrollment e = bob.getEnrollments().get(i);
                if (e.getCourse().getCourseCode().equals("CS101")) {
                    e.assignGrade(2.3);
                } else if (e.getCourse().getCourseCode().equals("CS301")) {
                    e.assignGrade(3.0);
                }
            }

            // Charlie grades
            for (int i = 0; i < charlie.getEnrollments().size(); i++) {
                Enrollment e = charlie.getEnrollments().get(i);
                if (e.getCourse().getCourseCode().equals("MATH101")) {
                    e.assignGrade(4.0);
                }
            }
        } catch (InvalidGradeException e) {
            System.out.println("  CAUGHT: " + e.getMessage());
        }
        System.out.println();


        System.out.println("------------------------------------------");
        System.out.println("SCENARIO 10: Multiple Catch Blocks Demo");
        System.out.println("------------------------------------------");

        try {
            Student testStudent = new Student(
                    "STU-TEST", "Test", "User",
                    "test@uni.edu", 150,  // INVALID age
                    "CS", 1
            );
            testStudent.enrollInCourse(dataStructures);
        } catch (InvalidAgeException e) {
            System.out.println("  CAUGHT InvalidAgeException (most specific)");
            System.out.println("  Message: " + e.getMessage());
        } catch (CourseFullException e) {
            System.out.println("  CAUGHT CourseFullException");
            System.out.println("  Message: " + e.getMessage());
        } catch (StudentManagementException e) {
            System.out.println("  CAUGHT StudentManagementException (base)");
            System.out.println("  Message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  CAUGHT Unexpected Exception");
            System.out.println("  Message: " + e.getMessage());
        }
        System.out.println();


        System.out.println("==========================================");
        System.out.println("         FINAL SYSTEM REPORT");
        System.out.println("==========================================\n");

        System.out.println("--- STUDENT PROFILES ---\n");
        alice.displayInfo();
        alice.displayEnrollments();
        System.out.printf("  Final GPA: %.2f%n%n", alice.getGpa());

        bob.displayInfo();
        bob.displayEnrollments();
        System.out.printf("  Final GPA: %.2f%n%n", bob.getGpa());

        charlie.displayInfo();
        charlie.displayEnrollments();
        System.out.printf("  Final GPA: %.2f%n%n", charlie.getGpa());

        System.out.println("--- COURSE ROSTERS ---\n");
        dataStructures.displayRoster();
        System.out.println();
        aiCourse.displayRoster();
        System.out.println();
        calculus.displayRoster();
        System.out.println();

        System.out.println("--- COURSE DETAILS ---\n");
        dataStructures.displayCourseInfo();
        System.out.println();
        aiCourse.displayCourseInfo();
        System.out.println();

        System.out.println("--- INSTRUCTOR DASHBOARDS ---\n");
        dr_smith.displayInfo();
        dr_smith.displayAssignedCourses();
        System.out.println();
        dr_patel.displayInfo();
        dr_patel.displayAssignedCourses();
        System.out.println();

        System.out.println("--- DEPARTMENT SUMMARIES ---\n");
        csDept.displayDepartmentSummary();
        System.out.println();
        mathDept.displayDepartmentSummary();

       
        // COLLECTIONS DEMO
        // Shows List, Set, and Map operations: add, retrieve, remove
        // ============================================================
        System.out.println("==========================================");
        System.out.println("  COLLECTIONS FRAMEWORK DEMO");
        System.out.println("==========================================");

        // --- LIST DEMO ---
        // Relationship: one student has MANY enrollments (one-to-many)
        // List preserves order and allows duplicates (though we prevent them via Set)
        System.out.println("\n[LIST] Alice's enrollments (ordered, one-to-many):");
        List<Enrollment> aliceEnrollments = alice.getEnrollments();
        for (int i = 0; i < aliceEnrollments.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + aliceEnrollments.get(i).getSummary()); // retrieve
        }
        // Remove demo: unenroll from last course, then re-enroll to restore state
        if (!aliceEnrollments.isEmpty()) {
            Enrollment removed = aliceEnrollments.remove(aliceEnrollments.size() - 1); // remove
            System.out.println("  [List.remove] Temporarily removed: " + removed.getCourse().getCourseName());
            aliceEnrollments.add(removed); // add back
            System.out.println("  [List.add]    Re-added: " + removed.getCourse().getCourseName());
        }

        // --- SET DEMO ---
        // Relationship: unique course codes a student is enrolled in (unique relationship)
        // Set automatically rejects duplicates — no manual loop needed
        System.out.println("\n[SET] Alice's unique enrolled course codes (no duplicates allowed):");
        Set<String> aliceCodes = alice.getEnrolledCourseCodes();
        System.out.println("  Current codes: " + aliceCodes); // retrieve (print all)
        boolean added = aliceCodes.add("CS101"); // add duplicate — Set will reject it
        System.out.println("  [Set.add] Try adding CS101 again -> was added? " + added); // false
        aliceCodes.add("TEMP999"); // add a temporary code
        System.out.println("  [Set.add] Added TEMP999: " + aliceCodes);
        aliceCodes.remove("TEMP999"); // remove
        System.out.println("  [Set.remove] Removed TEMP999: " + aliceCodes);

        // --- MAP DEMO ---
        // Relationship: courseCode -> Course object (key-value lookup)
        // Map lets us find a course in O(1) instead of looping through a list
        System.out.println("\n[MAP] CS Department course lookup by course code:");
        Map<String, Course> csCoursesMap = csDept.getCourseMap();

        // Retrieve: look up a course by its code
        Course found = csCoursesMap.get("CS101"); // retrieve by key
        System.out.println("  [Map.get] CS101 -> " + (found != null ? found.getCourseName() : "not found"));

        // Add: put a temporary course into the map
        Course tempCourse = new Course("CS999", "Temp Course", "Demo", 1, 5, "Fall 2025");
        csCoursesMap.put(tempCourse.getCourseCode(), tempCourse); // add
        System.out.println("  [Map.put] Added CS999. Map size: " + csCoursesMap.size());

        // Remove: remove the temporary course by key
        csCoursesMap.remove("CS999"); // remove
        System.out.println("  [Map.remove] Removed CS999. Map size: " + csCoursesMap.size());

        // Also demonstrate Department's helper that uses the Map
        Course lookedUp = csDept.getCourseByCode("CS301");
        System.out.println("  [getCourseByCode] CS301 -> " + (lookedUp != null ? lookedUp.getCourseName() : "not found"));

        System.out.println("\n==========================================");
        System.out.println("  10 Scenarios + Collections Demo Done.");
        System.out.println("  All exceptions handled gracefully.");
        System.out.println("==========================================");
    }
}