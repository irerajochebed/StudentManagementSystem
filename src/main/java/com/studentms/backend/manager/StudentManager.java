package com.studentms.backend.manager;

import com.studentms.backend.data.DataManager;
import com.studentms.backend.model.*;

import java.util.ArrayList;
import java.util.List;

public class StudentManager {

    // ── SINGLETON ──────────────────────────────────────────────────
    private static StudentManager instance;

    public static StudentManager getInstance() {
        if (instance == null) instance = new StudentManager();
        return instance;
    }

    private List<Student>    students;
    private List<Course>     courses;
    private List<Instructor> instructors;

    private StudentManager() {
        students    = new ArrayList<>();
        courses     = new ArrayList<>();
        instructors = new ArrayList<>();
        loadData();
    }

    // ── STUDENT OPERATIONS ─────────────────────────────────────────

    public void addStudent(String id, String firstName, String lastName,
                           String email, int age, String major, int yearLevel) {
        for (Student s : students) {
            if (s.getPersonId().equals(id))
                throw new RuntimeException("Student ID '" + id + "' already exists.");
        }
        students.add(new Student(id, firstName, lastName, email, age, major, yearLevel));
        saveData();
    }

    public void removeStudent(String studentId) {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getPersonId().equals(studentId)) {
                students.remove(i);
                saveData();
                return;
            }
        }
        throw new RuntimeException("Student '" + studentId + "' not found.");
    }

    public Student searchStudent(String studentId) {
        for (Student s : students) {
            if (s.getPersonId().equals(studentId)) return s;
        }
        return null;
    }

    public List<Student> getAllStudents()                  { return new ArrayList<>(students); }

    public List<Student> searchStudentsByName(String name) {
        List<Student> results = new ArrayList<>();
        String term = name.toLowerCase();
        for (Student s : students) {
            if (s.getFullName().toLowerCase().contains(term)) results.add(s);
        }
        return results;
    }

    // ── COURSE OPERATIONS ──────────────────────────────────────────

    public void addCourse(String code, String name, String description,
                          int creditHours, int maxCapacity, String semester) {
        for (Course c : courses) {
            if (c.getCourseCode().equals(code))
                throw new RuntimeException("Course code '" + code + "' already exists.");
        }
        courses.add(new Course(code, name, description, creditHours, maxCapacity, semester));
        saveData();
    }

    public void removeCourse(String courseCode) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseCode().equals(courseCode)) {
                courses.remove(i);
                saveData();
                return;
            }
        }
        throw new RuntimeException("Course '" + courseCode + "' not found.");
    }

    public Course searchCourse(String courseCode) {
        for (Course c : courses) {
            if (c.getCourseCode().equals(courseCode)) return c;
        }
        return null;
    }

    public List<Course> getAllCourses()                    { return new ArrayList<>(courses); }

    // ── INSTRUCTOR OPERATIONS ──────────────────────────────────────

    public void addInstructor(String id, String firstName, String lastName,
                              String email, int age, String specialization, String office) {
        for (Instructor ins : instructors) {
            if (ins.getPersonId().equals(id))
                throw new RuntimeException("Instructor ID '" + id + "' already exists.");
        }
        instructors.add(new Instructor(id, firstName, lastName, email, age, specialization, office, 0.0));
    }

    public void assignInstructorToCourse(String instructorId, String courseCode) {
        Instructor instructor = null;
        for (Instructor ins : instructors) {
            if (ins.getPersonId().equals(instructorId)) { instructor = ins; break; }
        }
        if (instructor == null)
            throw new RuntimeException("Instructor '" + instructorId + "' not found.");
        Course course = searchCourse(courseCode);
        if (course == null)
            throw new RuntimeException("Course '" + courseCode + "' not found.");
        course.assignInstructor(instructor);
    }

    public List<Instructor> getAllInstructors()            { return new ArrayList<>(instructors); }

    // ── ENROLLMENT OPERATIONS ──────────────────────────────────────

    public void enrollStudent(String studentId, String courseCode) {
        Student student = searchStudent(studentId);
        if (student == null) throw new RuntimeException("Student '" + studentId + "' not found.");
        Course course = searchCourse(courseCode);
        if (course == null) throw new RuntimeException("Course '" + courseCode + "' not found.");
        student.enrollInCourse(course);
        saveData();
    }

    public List<Student> getStudentsInCourse(String courseCode) {
        List<Student> result = new ArrayList<>();
        Course course = searchCourse(courseCode);
        if (course == null) return result;
        for (Enrollment e : course.getEnrollments()) result.add(e.getStudent());
        return result;
    }

    // ── GRADE OPERATIONS ───────────────────────────────────────────

    public void assignGrade(String studentId, String courseCode, double gradePoints) {
        Student student = searchStudent(studentId);
        if (student == null) throw new RuntimeException("Student '" + studentId + "' not found.");
        for (Enrollment e : student.getEnrollments()) {
            if (e.getCourse().getCourseCode().equals(courseCode)) {
                e.assignGrade(gradePoints);
                saveData();
                return;
            }
        }
        throw new RuntimeException("Student is not enrolled in '" + courseCode + "'.");
    }

    // ── SORTING OPERATIONS ─────────────────────────────────────────

    public void sortStudentsByName() {
        for (int i = 0; i < students.size() - 1; i++)
            for (int j = 0; j < students.size() - 1 - i; j++)
                if (students.get(j).getFullName().compareTo(students.get(j + 1).getFullName()) > 0) {
                    Student t = students.get(j); students.set(j, students.get(j + 1)); students.set(j + 1, t);
                }
    }

    public void sortStudentsByGPA() {
        for (int i = 0; i < students.size() - 1; i++)
            for (int j = 0; j < students.size() - 1 - i; j++)
                if (students.get(j).getGpa() < students.get(j + 1).getGpa()) {
                    Student t = students.get(j); students.set(j, students.get(j + 1)); students.set(j + 1, t);
                }
    }

    // ── UTILITY ────────────────────────────────────────────────────

    public int    getTotalStudents() { return students.size(); }
    public int    getTotalCourses()  { return courses.size(); }

    public double getAverageGPA() {
        if (students.isEmpty()) return 0.0;
        double total = 0.0;
        for (Student s : students) total += s.getGpa();
        return total / students.size();
    }

    // ── FILE I/O ───────────────────────────────────────────────────

    private void saveData() {
        try {
            DataManager.saveStudents(students);
            DataManager.saveCourses(courses);
            DataManager.saveEnrollments(students);
        } catch (Exception e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadData() {
        try {
            students = DataManager.loadStudents();
            courses  = DataManager.loadCourses();
            DataManager.loadEnrollments(students, courses);
        } catch (Exception e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }
}
