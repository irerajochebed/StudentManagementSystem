
public class Main {

    public static void main(String[] args) {


        System.out.println("║    UNIVERSITY STUDENT MANAGEMENT SYSTEM  ║");
        System.out.println("--------------------------------------------\n");

        System.out.println(">>> Setting up Departments...\n");

        Department csDept   = new Department("DEPT-CS",   "Computer Science", "Tech Hall A");
        Department mathDept = new Department("DEPT-MATH", "Mathematics",      "Science Block B");


        System.out.println("\n>>> Creating Instructors...\n");

        Instructor dr_smith = new Instructor(
                "INS-001", "John",  "Smith", "j.smith@uni.edu",
                45, "Algorithms & Data Structures", "Tech Hall A - Room 201", 85000.0
        );
        Instructor dr_patel = new Instructor(
                "INS-002", "Priya", "Patel", "p.patel@uni.edu",
                38, "Artificial Intelligence",      "Tech Hall A - Room 305", 90000.0
        );
        Instructor dr_jones = new Instructor(
                "INS-003", "Emily", "Jones", "e.jones@uni.edu",
                52, "Calculus & Linear Algebra",    "Science Block B - Room 110", 78000.0
        );

        csDept.addInstructor(dr_smith);
        csDept.addInstructor(dr_patel);
        mathDept.addInstructor(dr_jones);


        System.out.println("\n>>> Creating Courses...\n");

        Course dataStructures = new Course(
                "CS101", "Data Structures",
                "Arrays, Linked Lists, Trees, Graphs", 3, 3, "Fall 2025"
        );
        Course aiCourse = new Course(
                "CS301", "Introduction to AI",
                "Search, ML basics, Neural Networks",  3, 30, "Fall 2025"
        );
        Course calculus = new Course(
                "MATH101", "Calculus I",
                "Limits, Derivatives, Integrals",      4, 25, "Fall 2025"
        );

        System.out.println("\n>>> Assigning Instructors to Courses...\n");
        dataStructures.assignInstructor(dr_smith);
        aiCourse.assignInstructor(dr_patel);
        calculus.assignInstructor(dr_jones);

        csDept.addCourse(dataStructures);
        csDept.addCourse(aiCourse);
        mathDept.addCourse(calculus);


        System.out.println("\n>>> Creating Students...\n");

        Student alice   = new Student("STU-001", "Alice",   "Johnson",
                "alice@uni.edu",   20, "Computer Science", 2);
        Student bob     = new Student("STU-002", "Bob",     "Williams",
                "bob@uni.edu",     21, "Computer Science", 2);
        Student charlie = new Student("STU-003", "Charlie", "Brown",
                "charlie@uni.edu", 19, "Mathematics",      1);
        Student diana   = new Student("STU-004", "Diana",   "Prince",
                "diana@uni.edu",   22, "Computer Science", 3);


        System.out.println("\n>>> Enrolling Students in Courses...\n");

        alice.enrollInCourse(dataStructures);
        alice.enrollInCourse(aiCourse);
        alice.enrollInCourse(calculus);

        bob.enrollInCourse(dataStructures);
        bob.enrollInCourse(aiCourse);

        charlie.enrollInCourse(calculus);
        charlie.enrollInCourse(dataStructures);

        diana.enrollInCourse(dataStructures);   // Makes course FULL
        diana.enrollInCourse(aiCourse);

        // Test: enroll when course is full
        System.out.println("\n>>> Testing: Enroll when course is full...");
        Student eve = new Student("STU-005", "Eve", "Taylor",
                "eve@uni.edu", 20, "CS", 1);
        eve.enrollInCourse(dataStructures);     // Should be rejected

        // Test: duplicate enrollment
        System.out.println("\n>>> Testing: Duplicate enrollment...");
        alice.enrollInCourse(aiCourse);         // Should be rejected

        System.out.println("\n>>> Assigning Grades...\n");


        for (int i = 0; i < alice.getEnrollments().size(); i++) {
            Enrollment e = alice.getEnrollments().get(i);
            if (e.getCourse().getCourseCode().equals("CS101")) {
                e.assignGrade(3.7);   // A
            } else if (e.getCourse().getCourseCode().equals("CS301")) {
                e.assignGrade(3.3);   // A-
            } else if (e.getCourse().getCourseCode().equals("MATH101")) {
                e.assignGrade(2.7);   // B
            }
        }


        for (int i = 0; i < bob.getEnrollments().size(); i++) {
            Enrollment e = bob.getEnrollments().get(i);
            if (e.getCourse().getCourseCode().equals("CS101")) {
                e.assignGrade(2.3);   // B-
            } else if (e.getCourse().getCourseCode().equals("CS301")) {
                e.assignGrade(3.0);   // B+
            }
        }


        for (int i = 0; i < charlie.getEnrollments().size(); i++) {
            Enrollment e = charlie.getEnrollments().get(i);
            if (e.getCourse().getCourseCode().equals("MATH101")) {
                e.assignGrade(4.0);   // A
            }
        }



        System.out.println(" POLYMORPHISM DEMO ");
        System.out.println("---------------------------\n");
        System.out.println("  Person[] holds Students &     ");
        System.out.println(" Instructors — same method call,");
        System.out.println("different output at runtime     ");


        Person[] people = { alice, bob, charlie, dr_smith, dr_patel };

        for (int i = 0; i < people.length; i++) {
            System.out.println("--- Person " + (i + 1) + " of " + people.length + " ---");
            people[i].displayInfo();
            System.out.println("  Role: " + people[i].getRole());
            System.out.println();
        }

        System.out.println(" DETAILED STUDENT REPORTS    ");
        System.out.println("---------------------------\n");

        alice.displayEnrollments();
        System.out.printf("  Final GPA: %.2f%n%n", alice.getGpa());

        bob.displayEnrollments();
        System.out.printf("  Final GPA: %.2f%n%n", bob.getGpa());

        charlie.displayEnrollments();
        System.out.printf("  Final GPA: %.2f%n%n", charlie.getGpa());



        System.out.println("COURSE ROSTERS       ");
        System.out.println("---------------------------\n");

        dataStructures.displayRoster();
        System.out.println();
        aiCourse.displayRoster();
        System.out.println();
        calculus.displayRoster();



        System.out.println(" INSTRUCTOR DASHBOARDS      ");
        System.out.println("---------------------------\n");

        dr_smith.displayInfo();
        dr_smith.displayAssignedCourses();
        System.out.println();
        dr_patel.displayInfo();
        dr_patel.displayAssignedCourses();

        System.out.println(" DEPARTMENT SUMMARIES         ");
        System.out.println("---------------------------\n");

        csDept.displayDepartmentSummary();
        System.out.println();
        mathDept.displayDepartmentSummary();

        System.out.println("COURSE DETAILS           ");
        System.out.println("---------------------------\n");

        dataStructures.displayCourseInfo();
        System.out.println();
        aiCourse.displayCourseInfo();
        System.out.println();
        calculus.displayCourseInfo();
    }
}