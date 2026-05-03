package com.studentms.ui.controller;

import com.studentms.backend.auth.UserStore;
import com.studentms.backend.manager.StudentManager;
import com.studentms.backend.model.Course;
import com.studentms.backend.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class InstructorDashboardController {

    @FXML private ListView<String>       myCoursesList;
    @FXML private TextField              viewCourseCodeField;
    @FXML private TableView<Student>     courseStudentsTable;
    @FXML private TableColumn<Student, String>  csIdCol;
    @FXML private TableColumn<Student, String>  csNameCol;
    @FXML private TableColumn<Student, String>  csEmailCol;
    @FXML private TableColumn<Student, String>  csMajorCol;
    @FXML private TableColumn<Student, Double>  csGpaCol;
    @FXML private TextField              gradeStudentIdField;
    @FXML private TextField              gradeCourseCodeField;
    @FXML private TextField              gradePointsField;
    @FXML private TextField              enrollStudentIdField;
    @FXML private TextField              enrollCourseCodeField;
    @FXML private Label                  welcomeLabel;
    @FXML private Label                  messageLabel;
    @FXML private Button                 logoutBtn;

    private StudentManager manager;

    @FXML
    private void initialize() {
        manager = StudentManager.getInstance();
        csIdCol.setCellValueFactory(new PropertyValueFactory<>("personId"));
        csNameCol.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        csEmailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        csMajorCol.setCellValueFactory(new PropertyValueFactory<>("major"));
        csGpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));
    }

    public void initUser(UserStore.User user) {
        welcomeLabel.setText("Hello, " + user.getFullName());
        loadMyCourses();
        showMessage("Welcome, " + user.getFullName() + ". Manage your courses below.", "success");
    }

    private void loadMyCourses() {
        myCoursesList.getItems().clear();
        for (Course c : manager.getAllCourses()) {
            myCoursesList.getItems().add(
                c.getCourseCode() + " | " + c.getCourseName()
                + " | " + c.getSemester()
                + " | Enrolled: " + c.getEnrolledCount() + "/" + c.getMaxCapacity());
        }
        if (myCoursesList.getItems().isEmpty())
            myCoursesList.getItems().add("No courses found. Ask admin to add courses.");
    }

    @FXML
    private void loadStudentsInCourse() {
        String code = viewCourseCodeField.getText().trim();
        if (code.isEmpty()) { showMessage("Enter a course code.", "error"); return; }
        List<Student> students = manager.getStudentsInCourse(code);
        courseStudentsTable.setItems(FXCollections.observableArrayList(students));
        showMessage(students.isEmpty()
                ? "No students enrolled in " + code + " yet."
                : "Showing " + students.size() + " student(s) in " + code + ".", "info");
    }

    @FXML
    private void assignGrade() {
        try {
            String studentId  = gradeStudentIdField.getText().trim();
            String courseCode = gradeCourseCodeField.getText().trim();
            String pointsStr  = gradePointsField.getText().trim();
            if (studentId.isEmpty() || courseCode.isEmpty() || pointsStr.isEmpty()) {
                showMessage("Please fill in all grade fields.", "error"); return;
            }
            manager.assignGrade(studentId, courseCode, Double.parseDouble(pointsStr));
            gradeStudentIdField.clear(); gradeCourseCodeField.clear(); gradePointsField.clear();
            showMessage("Grade assigned to " + studentId + " for " + courseCode + ".", "success");
        } catch (NumberFormatException e) {
            showMessage("Grade must be a number between 0.0 and 4.0.", "error");
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), "error");
        }
    }

    @FXML
    private void enrollStudent() {
        try {
            String studentId  = enrollStudentIdField.getText().trim();
            String courseCode = enrollCourseCodeField.getText().trim();
            if (studentId.isEmpty() || courseCode.isEmpty()) {
                showMessage("Please enter both Student ID and Course Code.", "error"); return;
            }
            manager.enrollStudent(studentId, courseCode);
            enrollStudentIdField.clear(); enrollCourseCodeField.clear();
            loadMyCourses();
            showMessage("Student " + studentId + " enrolled in " + courseCode + ".", "success");
        } catch (Exception e) {
            showMessage("Error: " + e.getMessage(), "error");
        }
    }

    private void showMessage(String msg, String type) {
        messageLabel.setText(msg);
        String color = switch (type) {
            case "success" -> "#a5d6a7";
            case "error"   -> "#ef9a9a";
            default        -> "#90caf9";
        };
        messageLabel.setStyle("-fx-text-fill: " + color + "; -fx-padding: 0 15 6 15; -fx-font-size: 12px;");
    }

    @FXML
    private void logout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage = (Stage) logoutBtn.getScene().getWindow();
            Scene scene = new Scene(root, 900, 650);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            showMessage("Logout error: " + e.getMessage(), "error");
        }
    }
}
