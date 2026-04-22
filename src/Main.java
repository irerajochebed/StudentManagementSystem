import java.util.ArrayList;
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

        // ============================================================
        // COLLECTIONS DEMO — covers every class in the system
        // ============================================================
        System.out.println("==========================================");
        System.out.println("  COLLECTIONS FRAMEWORK DEMO");
        System.out.println("==========================================");

        // -------------------------------------------------------
        // STUDENT — List<Enrollment> + Set<String>
        // -------------------------------------------------------
        System.out.println("\n--- STUDENT COLLECTIONS ---");

        // List: one student -> many enrollments (one-to-many)
        System.out.println("[List] Alice's enrollments (ordered, one-to-many):");
        List<Enrollment> aliceEnrollments = alice.getEnrollments();
        for (int i = 0; i < aliceEnrollments.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + aliceEnrollments.get(i).getSummary()); // retrieve
        }
        // List remove + add back to show the operation
        Enrollment tempEnrollment = aliceEnrollments.remove(aliceEnrollments.size() - 1); // remove
        System.out.println("  [List.remove] Removed: " + tempEnrollment.getCourse().getCourseName());
        aliceEnrollments.add(tempEnrollment);                                              // add back
        System.out.println("  [List.add]    Re-added: " + tempEnrollment.getCourse().getCourseName());

        // Set: unique course codes — no duplicates allowed
        System.out.println("\n[Set] Alice's unique enrolled course codes:");
        Set<String> aliceCodes = alice.getEnrolledCourseCodes();
        System.out.println("  Current codes: " + aliceCodes);                  // retrieve
        boolean wasAdded = aliceCodes.add("CS101");                            // add duplicate
        System.out.println("  [Set.add] Add CS101 again -> added? " + wasAdded); // false
        aliceCodes.add("TEMP999");                                             // add temp
        System.out.println("  [Set.add] Added TEMP999: " + aliceCodes);
        aliceCodes.remove("TEMP999");                                          // remove
        System.out.println("  [Set.remove] Removed TEMP999: " + aliceCodes);

        // -------------------------------------------------------
        // COURSE — List<Enrollment> + Set<String> + Map<String,String>
        // -------------------------------------------------------
        System.out.println("\n--- COURSE COLLECTIONS ---");

        // Set: check if a student is enrolled by ID (unique relationship)
        System.out.println("[Set] Enrolled student IDs in Data Structures:");
        Set<String> dsStudentIds = dataStructures.getEnrolledStudentIds();
        System.out.println("  IDs: " + dsStudentIds);                                    // retrieve
        System.out.println("  [Set.contains] Is STU-001 enrolled? "
                + dsStudentIds.contains("STU-001"));                                     // retrieve
        dsStudentIds.add("TEMP-ID");                                                     // add
        System.out.println("  [Set.add] Added TEMP-ID: " + dsStudentIds);
        dsStudentIds.remove("TEMP-ID");                                                  // remove
        System.out.println("  [Set.remove] Removed TEMP-ID: " + dsStudentIds);

        // Map: student remarks — key=studentId, value=remark (key-value relationship)
        System.out.println("\n[Map] Student remarks in Data Structures:");
        dataStructures.addRemark("STU-001", "Excellent participation");  // add
        dataStructures.addRemark("STU-002", "Needs improvement");        // add
        System.out.println("  [Map.get] Remark for STU-001: "
                + dataStructures.getRemark("STU-001"));                  // retrieve
        System.out.println("  [Map.get] Remark for STU-002: "
                + dataStructures.getRemark("STU-002"));                  // retrieve
        dataStructures.removeRemark("STU-002");                          // remove
        System.out.println("  [Map.remove] After removing STU-002 remark: "
                + dataStructures.getStudentRemarks());

        // -------------------------------------------------------
        // INSTRUCTOR — List<Course> + Map<String, List<Course>>
        // -------------------------------------------------------
        System.out.println("\n--- INSTRUCTOR COLLECTIONS ---");

        // List: one instructor -> many courses (one-to-many)
        System.out.println("[List] Dr. Patel's assigned courses:");
        List<Course> patelCourses = dr_patel.getAssignedCourses();
        for (int i = 0; i < patelCourses.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + patelCourses.get(i)); // retrieve
        }

        // Map<semester, List<Course>>: combined collection — courses grouped by semester
        System.out.println("\n[Map<String,List>] Dr. Smith's courses grouped by semester:");
        dr_smith.displayCoursesBySemester();                                  // retrieve via Map
        Map<String, List<Course>> smithSemesters = dr_smith.getCoursesBySemester();
        // Add a temp semester entry to show Map.put
        List<Course> tempList = new ArrayList<>();
        tempList.add(dataStructures);
        smithSemesters.put("Spring 2026", tempList);                          // add
        System.out.println("  [Map.put] Added Spring 2026 entry. Keys: " + smithSemesters.keySet());
        smithSemesters.remove("Spring 2026");                                 // remove
        System.out.println("  [Map.remove] Removed Spring 2026. Keys: " + smithSemesters.keySet());

        // -------------------------------------------------------
        // ENROLLMENT — Map<String, String> grade history
        // -------------------------------------------------------
        System.out.println("\n--- ENROLLMENT COLLECTIONS ---");

        // Map: grade history — key=attempt label, value=grade (key-value relationship)
        Enrollment aliceDS = alice.getEnrollments().get(0);
        System.out.println("[Map] Grade history for Alice in Data Structures:");
        aliceDS.displayGradeHistory();                    // retrieve all entries
        // Add a second grade attempt to show Map.put
        aliceDS.assignGrade(4.0);                         // add (internally does Map.put)
        aliceDS.displayGradeHistory();                    // retrieve updated history
        aliceDS.removeLastGradeRecord();                  // remove last entry
        aliceDS.displayGradeHistory();                    // retrieve after removal

        // -------------------------------------------------------
        // DEPARTMENT — List<Instructor> + Map<String,Course> + Set<String>
        // -------------------------------------------------------
        System.out.println("\n--- DEPARTMENT COLLECTIONS ---");

        // Map: course lookup by code
        System.out.println("[Map] CS Dept course lookup:");
        Map<String, Course> csMap = csDept.getCourseMap();
        Course lookedUp = csMap.get("CS301");                                 // retrieve
        System.out.println("  [Map.get] CS301 -> " + (lookedUp != null ? lookedUp.getCourseName() : "not found"));
        Course tempCourse = new Course("CS999", "Temp", "Demo", 1, 5, "Fall 2025");
        csMap.put("CS999", tempCourse);                                       // add
        System.out.println("  [Map.put] Added CS999. Size: " + csMap.size());
        csMap.remove("CS999");                                                // remove
        System.out.println("  [Map.remove] Removed CS999. Size: " + csMap.size());

        // Set: unique students across all dept courses
        System.out.println("\n[Set] Unique students enrolled in CS Dept:");
        csDept.refreshEnrolledStudents();
        Set<String> uniqueStudents = csDept.getEnrolledStudentIds();
        System.out.println("  IDs: " + uniqueStudents);                       // retrieve
        uniqueStudents.add("TEMP-STU");                                       // add
        System.out.println("  [Set.add] Added TEMP-STU: " + uniqueStudents);
        uniqueStudents.remove("TEMP-STU");                                    // remove
        System.out.println("  [Set.remove] Removed TEMP-STU: " + uniqueStudents);

        System.out.println("\n==========================================");
        System.out.println("  Collections Demo Complete.");
        System.out.println("  All 5 classes use List, Set, and/or Map.");
        System.out.println("==========================================");
    }
}