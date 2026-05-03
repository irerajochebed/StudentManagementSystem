package com.university.backend.manager;

import com.university.backend.model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manager class orchestrates all backend operations for the Student Management System.
 * Handles CRUD operations for Students, Courses, Instructors, and Enrollments.
 */
public class StudentManagementManager {

    private List<Student> students;
    private List<Course> courses;
    private List<Instructor> instructors;
    private List<Department> departments;
    private List<Enrollment> enrollments;

    public StudentManagementManager() {
        this.students = new ArrayList<>();
        this.courses = new ArrayList<>();
        this.instructors = new ArrayList<>();
        this.departments = new ArrayList<>();
        this.enrollments = new ArrayList<>();
    }

    // ==================== STUDENT OPERATIONS ====================
    public void addStudent(Student student) {
        if (!studentExists(student.getPersonId())) {
            students.add(student);
        }
    }

    public Student getStudent(String studentId) {
        return students.stream()
                .filter(s -> s.getPersonId().equals(studentId))
                .findFirst()
                .orElse(null);
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public void removeStudent(String studentId) {
        students.removeIf(s -> s.getPersonId().equals(studentId));
    }

    public boolean studentExists(String studentId) {
        return students.stream().anyMatch(s -> s.getPersonId().equals(studentId));
    }

    public List<Student> searchStudentByName(String name) {
        return students.stream()
                .filter(s -> s.getFullName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    // ==================== COURSE OPERATIONS ====================
    public void addCourse(Course course) {
        if (!courseExists(course.getCourseCode())) {
            courses.add(course);
        }
    }

    public Course getCourse(String courseCode) {
        return courses.stream()
                .filter(c -> c.getCourseCode().equals(courseCode))
                .findFirst()
                .orElse(null);
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public void removeCourse(String courseCode) {
        courses.removeIf(c -> c.getCourseCode().equals(courseCode));
    }

    public boolean courseExists(String courseCode) {
        return courses.stream().anyMatch(c -> c.getCourseCode().equals(courseCode));
    }

    public List<Course> searchCourseByName(String name) {
        return courses.stream()
                .filter(c -> c.getCourseName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Course> getAvailableCourses() {
        return courses.stream()
                .filter(c -> !c.isFull())
                .collect(Collectors.toList());
    }

    // ==================== INSTRUCTOR OPERATIONS ====================
    public void addInstructor(Instructor instructor) {
        if (!instructorExists(instructor.getPersonId())) {
            instructors.add(instructor);
        }
    }

    public Instructor getInstructor(String instructorId) {
        return instructors.stream()
                .filter(i -> i.getPersonId().equals(instructorId))
                .findFirst()
                .orElse(null);
    }

    public List<Instructor> getAllInstructors() {
        return new ArrayList<>(instructors);
    }

    public void removeInstructor(String instructorId) {
        instructors.removeIf(i -> i.getPersonId().equals(instructorId));
    }

    public boolean instructorExists(String instructorId) {
        return instructors.stream().anyMatch(i -> i.getPersonId().equals(instructorId));
    }

    // ==================== ENROLLMENT OPERATIONS ====================
    public void enrollStudent(String studentId, String courseCode) {
        Student student = getStudent(studentId);
        Course course = getCourse(courseCode);

        if (student != null && course != null) {
            student.enrollInCourse(course);
            Enrollment enrollment = new Enrollment(student, course);
            enrollments.add(enrollment);
        }
    }

    public List<Enrollment> getStudentEnrollments(String studentId) {
        Student student = getStudent(studentId);
        if (student != null) {
            return new ArrayList<>(student.getEnrollments());
        }
        return new ArrayList<>();
    }

    public List<Enrollment> getCourseEnrollments(String courseCode) {
        Course course = getCourse(courseCode);
        if (course != null) {
            return new ArrayList<>(course.getEnrollments());
        }
        return new ArrayList<>();
    }

    // ==================== DEPARTMENT OPERATIONS ====================
    public void addDepartment(Department department) {
        if (!departmentExists(department.getDepartmentId())) {
            departments.add(department);
        }
    }

    public Department getDepartment(String departmentId) {
        return departments.stream()
                .filter(d -> d.getDepartmentId().equals(departmentId))
                .findFirst()
                .orElse(null);
    }

    public List<Department> getAllDepartments() {
        return new ArrayList<>(departments);
    }

    public void removeDepartment(String departmentId) {
        departments.removeIf(d -> d.getDepartmentId().equals(departmentId));
    }

    public boolean departmentExists(String departmentId) {
        return departments.stream().anyMatch(d -> d.getDepartmentId().equals(departmentId));
    }

    // ==================== GRADE OPERATIONS ====================
    public void assignGrade(String studentId, String courseCode, double gradePoints) {
        Student student = getStudent(studentId);
        if (student != null) {
            for (Enrollment enrollment : student.getEnrollments()) {
                if (enrollment.getCourse().getCourseCode().equals(courseCode)) {
                    enrollment.assignGrade(gradePoints);
                    break;
                }
            }
        }
    }

    // ==================== STATISTICS ====================
    public int getTotalStudents() {
        return students.size();
    }

    public int getTotalCourses() {
        return courses.size();
    }

    public int getTotalInstructors() {
        return instructors.size();
    }

    public int getTotalDepartments() {
        return departments.size();
    }

    public double getAverageStudentGPA() {
        if (students.isEmpty()) return 0.0;
        return students.stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0.0);
    }
}
