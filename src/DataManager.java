import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String STUDENTS_FILE    = "students.txt";
    private static final String COURSES_FILE     = "courses.txt";
    private static final String ENROLLMENTS_FILE = "enrollments.txt";

    public static void saveStudents(List<Student> students) {
        try (FileWriter writer = new FileWriter(STUDENTS_FILE)) {

            writer.write("studentId,firstName,lastName,email,age,major,yearLevel,gpa\n");

            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                writer.write(s.getPersonId()  + "," + s.getFirstName() + "," + s.getLastName()  + ","
                           + s.getEmail()     + "," + s.getAge()       + "," + s.getMajor()     + ","
                           + s.getYearLevel() + "," + String.format("%.2f", s.getGpa())         + "\n");
            }
            System.out.println("  [SAVED] " + students.size() + " students -> " + STUDENTS_FILE);

        } catch (IOException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }


    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) { skipHeader = false; continue; } // skip header row

                // Split line by comma → each part is one field
                String[] p = line.split(",");
                // p[0]=id  p[1]=first  p[2]=last  p[3]=email  p[4]=age  p[5]=major  p[6]=year
                students.add(new Student(p[0], p[1], p[2], p[3],
                        Integer.parseInt(p[4]), p[5], Integer.parseInt(p[6])));
            }
            System.out.println("  [LOADED] " + students.size() + " students <- " + STUDENTS_FILE);

        } catch (IOException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
        return students;
    }

    public static void saveCourses(List<Course> courses) {
        try (FileWriter writer = new FileWriter(COURSES_FILE)) {

            writer.write("courseCode,courseName,description,creditHours,maxCapacity,semester,instructorName\n");

            for (int i = 0; i < courses.size(); i++) {
                Course c = courses.get(i);
                String instructor = (c.getInstructor() != null) ? c.getInstructor().getFullName() : "TBA";
                // Replace commas in description with semicolons to keep CSV format safe
                String desc = c.getDescription().replace(",", ";");

                writer.write(c.getCourseCode() + "," + c.getCourseName() + "," + desc        + ","
                           + c.getCreditHours() + "," + c.getMaxCapacity() + "," + c.getSemester() + ","
                           + instructor + "\n");
            }
            System.out.println("  [SAVED] " + courses.size() + " courses -> " + COURSES_FILE);

        } catch (IOException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }

    public static List<Course> loadCourses() {
        List<Course> courses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            boolean skipHeader = true;

            while ((line = reader.readLine()) != null) {
                if (skipHeader) { skipHeader = false; continue; }

                // Limit split to 7 so description field stays intact
                String[] p = line.split(",", 7);
                // p[0]=code  p[1]=name  p[2]=desc  p[3]=credits  p[4]=capacity  p[5]=semester  p[6]=instructor
                courses.add(new Course(p[0], p[1], p[2],
                        Integer.parseInt(p[3]), Integer.parseInt(p[4]), p[5]));
            }
            System.out.println("  [LOADED] " + courses.size() + " courses <- " + COURSES_FILE);

        } catch (IOException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
        return courses;
    }

    public static void saveEnrollments(List<Student> students) {
        try (FileWriter writer = new FileWriter(ENROLLMENTS_FILE)) {

            writer.write("studentId,studentName,courseCode,courseName,grade,status\n");
            int count = 0;

            for (int i = 0; i < students.size(); i++) {
                Student s = students.get(i);
                List<Enrollment> enrollments = s.getEnrollments();

                for (int j = 0; j < enrollments.size(); j++) {
                    Enrollment e = enrollments.get(j);
                    writer.write(s.getPersonId()               + "," + s.getFullName()               + ","
                               + e.getCourse().getCourseCode() + "," + e.getCourse().getCourseName() + ","
                               + e.getLetterGrade()            + "," + e.getStatus()                 + "\n");
                    count++;
                }
            }
            System.out.println("  [SAVED] " + count + " enrollments -> " + ENROLLMENTS_FILE);

        } catch (IOException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }


    public static void loadAndDisplayEnrollments() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ENROLLMENTS_FILE))) {
            String line;
            boolean skipHeader = true;
            int count = 0;

            System.out.println("  StudentID  | Student Name     | Course  | Course Name          | Grade | Status");
            System.out.println("  -----------|------------------|---------|----------------------|-------|----------");

            while ((line = reader.readLine()) != null) {
                if (skipHeader) { skipHeader = false; continue; }

                String[] p = line.split(",", 6);
                System.out.printf("  %-10s | %-16s | %-7s | %-20s | %-5s | %s%n",
                        p[0], p[1], p[2], p[3], p[4], p[5]);
                count++;
            }
            System.out.println("  [LOADED] " + count + " enrollments <- " + ENROLLMENTS_FILE);

        } catch (IOException e) {
            System.out.println("  [ERROR] " + e.getMessage());
        }
    }
}
