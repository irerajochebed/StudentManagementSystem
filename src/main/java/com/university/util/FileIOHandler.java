package com.university.util;

import com.university.backend.manager.StudentManagementManager;
import com.university.backend.manager.AuthenticationManager;
import com.university.backend.model.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Handles file I/O operations for the Student Management System.
 * Supports saving and loading student, course, instructor, and enrollment data.
 */
public class FileIOHandler {

    private static final String DATA_DIR = "data";
    private static final String STUDENTS_FILE = DATA_DIR + "/students.csv";
    private static final String COURSES_FILE = DATA_DIR + "/courses.csv";
    private static final String INSTRUCTORS_FILE = DATA_DIR + "/instructors.csv";
    private static final String ENROLLMENTS_FILE = DATA_DIR + "/enrollments.csv";
    private static final String USERS_FILE = DATA_DIR + "/users.csv";

    public FileIOHandler() {
        createDataDirectory();
    }

    private void createDataDirectory() {
        try {
            Path dataPath = Paths.get(DATA_DIR);
            if (!Files.exists(dataPath)) {
                Files.createDirectory(dataPath);
            }
        } catch (IOException e) {
            System.err.println("Error creating data directory: " + e.getMessage());
        }
    }

    // ==================== SAVE OPERATIONS ====================
    public void saveManager(StudentManagementManager manager, AuthenticationManager authManager) {
        saveStudents(manager.getAllStudents());
        saveCourses(manager.getAllCourses());
        saveInstructors(manager.getAllInstructors());
        saveUsers(authManager);
    }

    public void saveManager(StudentManagementManager manager) {
        saveStudents(manager.getAllStudents());
        saveCourses(manager.getAllCourses());
        saveInstructors(manager.getAllInstructors());
    }

    public void saveStudents(List<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            writer.println("ID,FirstName,LastName,Email,Age,Major,YearLevel");
            for (Student student : students) {
                writer.printf("%s,%s,%s,%s,%d,%s,%d%n",
                        student.getPersonId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getAge(),
                        student.getMajor(),
                        student.getYearLevel()
                );
            }
        } catch (IOException e) {
            System.err.println("Error saving students: " + e.getMessage());
        }
    }

    public void saveCourses(List<Course> courses) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(COURSES_FILE))) {
            writer.println("CourseCode,CourseName,Description,Credits,MaxCapacity,Semester");
            for (Course course : courses) {
                writer.printf("%s,%s,%s,%d,%d,%s%n",
                        course.getCourseCode(),
                        course.getCourseName(),
                        course.getDescription(),
                        course.getCreditHours(),
                        course.getMaxCapacity(),
                        course.getSemester()
                );
            }
        } catch (IOException e) {
            System.err.println("Error saving courses: " + e.getMessage());
        }
    }

    public void saveInstructors(List<Instructor> instructors) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(INSTRUCTORS_FILE))) {
            writer.println("ID,FirstName,LastName,Email,Age,Specialization,Office,Salary");
            for (Instructor instructor : instructors) {
                writer.printf("%s,%s,%s,%s,%d,%s,%s,%.2f%n",
                        instructor.getPersonId(),
                        instructor.getFirstName(),
                        instructor.getLastName(),
                        instructor.getEmail(),
                        instructor.getAge(),
                        instructor.getSpecialization(),
                        instructor.getOfficeLocation(),
                        instructor.getSalary()
                );
            }
        } catch (IOException e) {
            System.err.println("Error saving instructors: " + e.getMessage());
        }
    }

    public void saveUsers(AuthenticationManager authManager) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            writer.println("Username,Password,Role,LinkedId");
            for (User user : authManager.getAllUsers().values()) {
                writer.printf("%s,%s,%s,%s%n",
                        user.getUsername(),
                        user.getPassword(),
                        user.getRole().name(),
                        user.getLinkedId() == null ? "" : user.getLinkedId()
                );
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    // ==================== LOAD OPERATIONS ====================
    public void loadDataIntoManager(StudentManagementManager manager, AuthenticationManager authManager) {
        loadStudents(manager);
        loadCourses(manager);
        loadInstructors(manager);
        loadUsers(authManager);
    }

    public void loadDataIntoManager(StudentManagementManager manager) {
        loadStudents(manager);
        loadCourses(manager);
        loadInstructors(manager);
    }

    public void loadStudents(StudentManagementManager manager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) {
                    try {
                        Student student = new Student(
                                parts[0], // ID
                                parts[1], // FirstName
                                parts[2], // LastName
                                parts[3], // Email
                                Integer.parseInt(parts[4]), // Age
                                parts[5], // Major
                                Integer.parseInt(parts[6])  // YearLevel
                        );
                        manager.addStudent(student);
                    } catch (Exception e) {
                        System.err.println("Error parsing student line: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Students file not found or error reading: " + e.getMessage());
        }
    }

    public void loadCourses(StudentManagementManager manager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(COURSES_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
                if (parts.length >= 6) {
                    try {
                        Course course = new Course(
                                parts[0], // CourseCode
                                parts[1], // CourseName
                                parts[2], // Description
                                Integer.parseInt(parts[3]), // Credits
                                Integer.parseInt(parts[4]), // MaxCapacity
                                parts[5]  // Semester
                        );
                        manager.addCourse(course);
                    } catch (Exception e) {
                        System.err.println("Error parsing course line: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Courses file not found or error reading: " + e.getMessage());
        }
    }

    public void loadInstructors(StudentManagementManager manager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(INSTRUCTORS_FILE))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 8) {
                    try {
                        Instructor instructor = new Instructor(
                                parts[0], // ID
                                parts[1], // FirstName
                                parts[2], // LastName
                                parts[3], // Email
                                Integer.parseInt(parts[4]), // Age
                                parts[5], // Specialization
                                parts[6], // Office
                                Double.parseDouble(parts[7]) // Salary
                        );
                        manager.addInstructor(instructor);
                    } catch (Exception e) {
                        System.err.println("Error parsing instructor line: " + line + " - " + e.getMessage());
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Instructors file not found or error reading: " + e.getMessage());
        }
    }

    public void loadUsers(AuthenticationManager authManager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            reader.readLine(); // Skip header
            Map<String, User> users = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 4);
                if (parts.length >= 3) {
                    try {
                        String linkedId = parts.length == 4 && !parts[3].isEmpty() ? parts[3] : null;
                        User user = new User(
                                parts[0], // Username
                                parts[1], // Password
                                User.UserRole.valueOf(parts[2]), // Role
                                linkedId  // LinkedId
                        );
                        users.put(user.getUsername(), user);
                    } catch (Exception e) {
                        System.err.println("Error parsing user line: " + line + " - " + e.getMessage());
                    }
                }
            }
            authManager.setUsers(users);
        } catch (IOException e) {
            System.err.println("Users file not found, using default admin account");
        }
    }
}
