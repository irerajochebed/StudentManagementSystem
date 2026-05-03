package com.studentms.ui.controller;

import com.studentms.backend.auth.UserStore;
import com.studentms.backend.manager.StudentManager;
import com.studentms.backend.model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainController {

    // ── Student fields ─────────────────────────────────────────────
    @FXML private TextField studentIdField, firstNameField, lastNameField;
    @FXML private TextField emailField, ageField, majorField, yearLevelField;
    @FXML private Button    addStudentBtn, removeStudentBtn, searchStudentBtn;
    @FXML private Button    sortByNameBtn, sortByGPABtn;
    @FXML private TextField searchStudentField;

    @FXML private TableView<Student>           studentTable;
    @FXML private TableColumn<Student, String> studentIdCol, studentNameCol, emailCol, majorCol;
    @FXML private TableColumn<Student, Integer> ageCol, yearLevelCol;
    @FXML private TableColumn<Student, Double>  gpaCol;

    // ── Course fields ──────────────────────────────────────────────
    @FXML private TextField courseCodeField, courseNameField, descriptionField;
    @FXML private TextField creditHoursField, maxCapacityField, semesterField;
    @FXML private Button    addCourseBtn, removeCourseBtn;
    @FXML private ListView<Course> courseListView;

    // ── Instructor fields ──────────────────────────────────────────
    @FXML private TextField insIdField, insFirstNameField, insLastNameField;
    @FXML private TextField insEmailField, insAgeField, insSpecField, insOfficeField;
    @FXML private TextField assignInsIdField, assignInsCourseField;
    @FXML private Button    addInstructorBtn, assignInstructorBtn;
    @FXML private TableView<Instructor>            instructorTable;
    @FXML private TableColumn<Instructor, String>  insIdCol, insNameCol, insEmailCol, insSpecCol, insOfficeCol;

    // ── Enrollment fields ──────────────────────────────────────────
    @FXML private TextField  enrollStudentIdField, enrollCourseCodeField;
    @FXML private TextField  viewEnrollStudentIdField;
    @FXML private Button     enrollBtn, viewEnrollmentsBtn;
    @FXML private ListView<String> enrollmentListView;

    // ── Grade fields ───────────────────────────────────────────────
    @FXML private TextField adminGradeStudentIdField, adminGradeCourseCodeField, adminGradePointsField;
    @FXML private Button    assignGradeBtn;

    // ── Top bar ────────────────────────────────────────────────────
    @FXML private Label    messageLabel, statsLabel, welcomeLabel, roleBadge;
    @FXML private Button   logoutBtn;
    @FXML private TabPane  mainTabPane;

    // ── Backend ────────────────────────────────────────────────────
    private StudentManager manager;
    private ObservableList<Student> studentData;

    @FXML
    private void initialize() {
        manager = StudentManager.getInstance();
        setupTableColumns();
        refreshUI();
        setupEventHandlers();
    }

    public void initUser(UserStore.User user) {
        welcomeLabel.setText("Hello, " + user.getFullName());
        roleBadge.setText("ADMIN");
        roleBadge.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; "
                + "-fx-background-radius: 10; -fx-padding: 3 10 3 10; "
                + "-fx-font-size: 11px; -fx-font-weight: bold;");
        showMessage("Logged in as " + user.getFullName() + " — Full Admin Access", "success");
    }

    // ── Setup ──────────────────────────────────────────────────────

    private void setupTableColumns() {
        studentIdCol.setCellValueFactory(new PropertyValueFactory<>("personId"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        majorCol.setCellValueFactory(new PropertyValueFactory<>("major"));
        yearLevelCol.setCellValueFactory(new PropertyValueFactory<>("yearLevel"));
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));

        insIdCol.setCellValueFactory(new PropertyValueFactory<>("personId"));
        insNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        insEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        insSpecCol.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        insOfficeCol.setCellValueFactory(new PropertyValueFactory<>("officeLocation"));

        studentData = FXCollections.observableArrayList();
        studentTable.setItems(studentData);
    }

    private void setupEventHandlers() {
        addStudentBtn.setOnAction(e    -> addStudent());
        removeStudentBtn.setOnAction(e -> removeStudent());
        searchStudentBtn.setOnAction(e -> searchStudent());
        sortByNameBtn.setOnAction(e    -> sortStudentsByName());
        sortByGPABtn.setOnAction(e     -> sortStudentsByGPA());
        addCourseBtn.setOnAction(e     -> addCourse());
        removeCourseBtn.setOnAction(e  -> removeCourse());
        addInstructorBtn.setOnAction(e    -> addInstructor());
        assignInstructorBtn.setOnAction(e -> assignInstructor());
        enrollBtn.setOnAction(e           -> enrollStudent());
        viewEnrollmentsBtn.setOnAction(e  -> viewEnrollments());
        assignGradeBtn.setOnAction(e      -> assignGrade());
        logoutBtn.setOnAction(e           -> logout());
    }

    // ── Student operations ─────────────────────────────────────────

    private void addStudent() {
        try {
            String id        = studentIdField.getText().trim();
            String firstName = firstNameField.getText().trim();
            String lastName  = lastNameField.getText().trim();
            String email     = emailField.getText().trim();
            String major     = majorField.getText().trim();
            int age          = Integer.parseInt(ageField.getText().trim());
            int yearLevel    = Integer.parseInt(yearLevelField.getText().trim());
            if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || major.isEmpty()) {
                showMessage("Please fill in all student fields.", "error"); return;
            }
            manager.addStudent(id, firstName, lastName, email, age, major, yearLevel);
            clearStudentFields();
            refreshUI();
            showMessage("Student added successfully!", "success");
        } catch (NumberFormatException e) {
            showMessage("Age and Year Level must be numbers.", "error");
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), "error");
        }
    }

    private void removeStudent() {
        try {
            String id = studentIdField.getText().trim();
            if (id.isEmpty()) { showMessage("Enter Student ID to remove.", "error"); return; }
            manager.removeStudent(id);
            clearStudentFields();
            refreshUI();
            showMessage("Student removed successfully!", "success");
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), "error");
        }
    }

    private void searchStudent() {
        String term = searchStudentField.getText().trim();
        if (term.isEmpty()) { refreshStudentTable(); return; }
        var results = manager.searchStudentsByName(term);
        studentData.clear();
        studentData.addAll(results);
        showMessage("Found " + results.size() + " student(s).", "info");
    }

    private void sortStudentsByName() {
        manager.sortStudentsByName();
        refreshStudentTable();
        showMessage("Sorted by name.", "info");
    }

    private void sortStudentsByGPA() {
        manager.sortStudentsByGPA();
        refreshStudentTable();
        showMessage("Sorted by GPA.", "info");
    }

    // ── Course operations ──────────────────────────────────────────

    private void addCourse() {
        try {
            String code  = courseCodeField.getText().trim();
            String name  = courseNameField.getText().trim();
            String desc  = descriptionField.getText().trim();
            String sem   = semesterField.getText().trim();
            int credits  = Integer.parseInt(creditHoursField.getText().trim());
            int capacity = Integer.parseInt(maxCapacityField.getText().trim());
            if (code.isEmpty() || name.isEmpty() || desc.isEmpty() || sem.isEmpty()) {
                showMessage("Please fill in all course fields.", "error"); return;
            }
            manager.addCourse(code, name, desc, credits, capacity, sem);
            clearCourseFields();
            refreshUI();
            showMessage("Course added successfully!", "success");
        } catch (NumberFormatException e) {
            showMessage("Credit Hours and Max Capacity must be numbers.", "error");
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), "error");
        }
    }

    private void removeCourse() {
        try {
            String code = courseCodeField.getText().trim();
            if (code.isEmpty()) { showMessage("Enter Course Code to remove.", "error"); return; }
            manager.removeCourse(code);
            clearCourseFields();
            refreshUI();
            showMessage("Course removed successfully!", "success");
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), "error");
        }
    }

    // ── Instructor operations ──────────────────────────────────────

    private void addInstructor() {
        try {
            String id     = insIdField.getText().trim();
            String first  = insFirstNameField.getText().trim();
            String last   = insLastNameField.getText().trim();
            String email  = insEmailField.getText().trim();
            String spec   = insSpecField.getText().trim();
            String office = insOfficeField.getText().trim();
            int age       = Integer.parseInt(insAgeField.getText().trim());
            if (id.isEmpty() || first.isEmpty() || last.isEmpty() || email.isEmpty()) {
                showMessage("Please fill in all instructor fields.", "error"); return;
            }
            manager.addInstructor(id, first, last, email, age, spec, office);
            insIdField.clear(); insFirstNameField.clear(); insLastNameField.clear();
            insEmailField.clear(); insAgeField.clear(); insSpecField.clear(); insOfficeField.clear();
            refreshUI();
            showMessage("Instructor added successfully!", "success");
        } catch (NumberFormatException e) {
            showMessage("Age must be a number.", "error");
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), "error");
        }
    }

    private void assignInstructor() {
        try {
            String insId  = assignInsIdField.getText().trim();
            String course = assignInsCourseField.getText().trim();
            if (insId.isEmpty() || course.isEmpty()) {
                showMessage("Enter both Instructor ID and Course Code.", "error"); return;
            }
            manager.assignInstructorToCourse(insId, course);
            assignInsIdField.clear(); assignInsCourseField.clear();
            refreshUI();
            showMessage("Instructor " + insId + " assigned to " + course + ".", "success");
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), "error");
        }
    }

    // ── Enrollment operations ──────────────────────────────────────

    private void enrollStudent() {
        try {
            String studentId  = enrollStudentIdField.getText().trim();
            String courseCode = enrollCourseCodeField.getText().trim();
            if (studentId.isEmpty() || courseCode.isEmpty()) {
                showMessage("Enter both Student ID and Course Code.", "error"); return;
            }
            manager.enrollStudent(studentId, courseCode);
            enrollStudentIdField.clear(); enrollCourseCodeField.clear();
            refreshUI();
            showMessage("Student enrolled successfully!", "success");
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), "error");
        }
    }

    private void viewEnrollments() {
        String studentId = viewEnrollStudentIdField.getText().trim();
        if (studentId.isEmpty()) { showMessage("Enter a Student ID.", "error"); return; }
        Student student = manager.searchStudent(studentId);
        if (student == null) { showMessage("Student not found.", "error"); return; }
        enrollmentListView.getItems().clear();
        if (student.getEnrollments().isEmpty()) {
            enrollmentListView.getItems().add("No enrollments for " + student.getFullName());
        } else {
            for (Enrollment e : student.getEnrollments())
                enrollmentListView.getItems().add(e.getSummary());
        }
        showMessage("Enrollments for " + student.getFullName(), "info");
    }

    // ── Grade operations ───────────────────────────────────────────

    private void assignGrade() {
        try {
            String studentId  = adminGradeStudentIdField.getText().trim();
            String courseCode = adminGradeCourseCodeField.getText().trim();
            String pointsStr  = adminGradePointsField.getText().trim();
            if (studentId.isEmpty() || courseCode.isEmpty() || pointsStr.isEmpty()) {
                showMessage("Fill in all grade fields.", "error"); return;
            }
            manager.assignGrade(studentId, courseCode, Double.parseDouble(pointsStr));
            adminGradeStudentIdField.clear(); adminGradeCourseCodeField.clear(); adminGradePointsField.clear();
            refreshUI();
            showMessage("Grade assigned to " + studentId + " for " + courseCode + ".", "success");
        } catch (NumberFormatException e) {
            showMessage("Grade must be a number between 0.0 and 4.0.", "error");
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), "error");
        }
    }

    // ── UI helpers ─────────────────────────────────────────────────

    private void refreshUI() {
        Platform.runLater(() -> {
            refreshStudentTable();
            courseListView.getItems().setAll(manager.getAllCourses());
            instructorTable.getItems().setAll(manager.getAllInstructors());
            int ts = manager.getTotalStudents(), tc = manager.getTotalCourses();
            statsLabel.setText(String.format("Students: %d | Courses: %d | Avg GPA: %.2f",
                    ts, tc, manager.getAverageGPA()));
        });
    }

    private void refreshStudentTable() {
        studentData.clear();
        studentData.addAll(manager.getAllStudents());
    }

    private void showMessage(String message, String type) {
        Platform.runLater(() -> {
            messageLabel.setText(message);
            messageLabel.getStyleClass().clear();
            messageLabel.getStyleClass().add("message-" + type);
        });
    }

    private void clearStudentFields() {
        studentIdField.clear(); firstNameField.clear(); lastNameField.clear();
        emailField.clear(); ageField.clear(); majorField.clear(); yearLevelField.clear();
    }

    private void clearCourseFields() {
        courseCodeField.clear(); courseNameField.clear(); descriptionField.clear();
        creditHoursField.clear(); maxCapacityField.clear(); semesterField.clear();
    }

    private void logout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            Scene scene = new Scene(root, 900, 650);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Student Management System");
        } catch (Exception e) {
            showMessage("Logout error.", "error");
        }
    }
}
