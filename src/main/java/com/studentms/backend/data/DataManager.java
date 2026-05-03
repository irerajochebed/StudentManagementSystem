package com.studentms.backend.data;

import com.studentms.backend.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    private static final String STUDENTS_FILE    = "students.txt";
    private static final String COURSES_FILE     = "courses.txt";
    private static final String ENROLLMENTS_FILE = "enrollments.txt";

    // ── STUDENTS ───────────────────────────────────────────────────

    public static void saveStudents(List<Student> students) {
        try (FileWriter w = new FileWriter(STUDENTS_FILE)) {
            w.write("studentId,firstName,lastName,email,age,major,yearLevel,gpa\n");
            for (Student s : students) {
                w.write(s.getPersonId() + "," + s.getFirstName() + "," + s.getLastName() + ","
                      + s.getEmail() + "," + s.getAge() + "," + s.getMajor() + ","
                      + s.getYearLevel() + "," + String.format("%.2f", s.getGpa()) + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving students: " + e.getMessage());
        }
    }

    public static List<Student> loadStudents() {
        List<Student> students = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line; boolean skip = true;
            while ((line = r.readLine()) != null) {
                if (skip) { skip = false; continue; }
                String[] p = line.split(",");
                if (p.length < 7) continue;
                students.add(new Student(p[0], p[1], p[2], p[3],
                        Integer.parseInt(p[4]), p[5], Integer.parseInt(p[6])));
            }
        } catch (IOException ignored) {}
        return students;
    }

    // ── COURSES ────────────────────────────────────────────────────

    public static void saveCourses(List<Course> courses) {
        try (FileWriter w = new FileWriter(COURSES_FILE)) {
            w.write("courseCode,courseName,description,creditHours,maxCapacity,semester\n");
            for (Course c : courses) {
                String desc = c.getDescription().replace(",", ";");
                w.write(c.getCourseCode() + "," + c.getCourseName() + "," + desc + ","
                      + c.getCreditHours() + "," + c.getMaxCapacity() + "," + c.getSemester() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving courses: " + e.getMessage());
        }
    }

    public static List<Course> loadCourses() {
        List<Course> courses = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line; boolean skip = true;
            while ((line = r.readLine()) != null) {
                if (skip) { skip = false; continue; }
                String[] p = line.split(",", 6);
                if (p.length < 6) continue;
                courses.add(new Course(p[0], p[1], p[2],
                        Integer.parseInt(p[3]), Integer.parseInt(p[4]), p[5]));
            }
        } catch (IOException ignored) {}
        return courses;
    }

    // ── ENROLLMENTS ────────────────────────────────────────────────

    public static void saveEnrollments(List<Student> students) {
        try (FileWriter w = new FileWriter(ENROLLMENTS_FILE)) {
            w.write("studentId,courseCode,gradePoints,isGraded,status\n");
            for (Student s : students) {
                for (Enrollment e : s.getEnrollments()) {
                    w.write(s.getPersonId() + ","
                          + e.getCourse().getCourseCode() + ","
                          + String.format("%.2f", e.getGradePoints()) + ","
                          + e.isGraded() + ","
                          + e.getStatus() + "\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error saving enrollments: " + e.getMessage());
        }
    }

    public static void loadEnrollments(List<Student> students, List<Course> courses) {
        try (BufferedReader r = new BufferedReader(new FileReader(ENROLLMENTS_FILE))) {
            String line; boolean skip = true;
            while ((line = r.readLine()) != null) {
                if (skip) { skip = false; continue; }
                String[] p = line.split(",");
                if (p.length < 5) continue;

                String  studentId  = p[0];
                String  courseCode = p[1];
                double  grade      = Double.parseDouble(p[2]);
                boolean isGraded   = Boolean.parseBoolean(p[3]);

                Student student = null;
                for (Student s : students) {
                    if (s.getPersonId().equals(studentId)) { student = s; break; }
                }
                Course course = null;
                for (Course c : courses) {
                    if (c.getCourseCode().equals(courseCode)) { course = c; break; }
                }
                if (student == null || course == null) continue;

                Enrollment enrollment = new Enrollment(student, course);
                if (isGraded) enrollment.assignGrade(grade);
                student.getEnrollments().add(enrollment);
                course.getEnrollments().add(enrollment);
            }
        } catch (IOException ignored) {}
    }
}
