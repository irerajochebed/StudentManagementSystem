package com.studentms.ui.controller;

import com.studentms.backend.auth.UserStore;
import com.studentms.backend.manager.StudentManager;
import com.studentms.backend.model.Enrollment;
import com.studentms.backend.model.Student;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class StudentDashboardController {

    // Profile labels
    @FXML private Label profileId;
    @FXML private Label profileName;
    @FXML private Label profileEmail;
    @FXML private Label profileMajor;
    @FXML private Label profileYear;
    @FXML private Label profileGpa;

    // Courses & Grades table
    @FXML private TableView<Enrollment>            myCoursesTable;
    @FXML private TableColumn<Enrollment, String>  myCourseCodeCol;
    @FXML private TableColumn<Enrollment, String>  myCourseNameCol;
    @FXML private TableColumn<Enrollment, String>  myCreditHoursCol;
    @FXML private TableColumn<Enrollment, String>  mySemesterCol;
    @FXML private TableColumn<Enrollment, String>  myGradeCol;
    @FXML private TableColumn<Enrollment, String>  myStatusCol;

    @FXML private Label totalEnrolledLabel;
    @FXML private Label gpaLabel;

    // Top bar
    @FXML private Label  welcomeLabel;
    @FXML private Label  messageLabel;
    @FXML private Button logoutBtn;

    private StudentManager manager;

    @FXML
    private void initialize() {
        manager = StudentManager.getInstance();
        myCourseCodeCol.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue().getCourse().getCourseCode()));
        myCourseNameCol.setCellValueFactory(d  -> new SimpleStringProperty(d.getValue().getCourse().getCourseName()));
        myCreditHoursCol.setCellValueFactory(d -> new SimpleStringProperty(String.valueOf(d.getValue().getCourse().getCreditHours())));
        mySemesterCol.setCellValueFactory(d    -> new SimpleStringProperty(d.getValue().getCourse().getSemester()));
        myGradeCol.setCellValueFactory(d       -> new SimpleStringProperty(d.getValue().getLetterGrade()));
        myStatusCol.setCellValueFactory(d      -> new SimpleStringProperty(d.getValue().getStatus()));
    }

    public void initUser(UserStore.User user) {
        welcomeLabel.setText("Hello, " + user.getFullName());
        Student student = manager.searchStudent(user.getLinkedId());
        if (student == null) {
            messageLabel.setText("⚠ Student record '" + user.getLinkedId()
                    + "' not found. Ask admin to add you first.");
            return;
        }
        loadStudentData(student);
    }

    private void loadStudentData(Student student) {
        profileId.setText(student.getPersonId());
        profileName.setText(student.getFullName());
        profileEmail.setText(student.getEmail());
        profileMajor.setText(student.getMajor());
        profileYear.setText("Year " + student.getYearLevel());
        profileGpa.setText(String.format("%.2f", student.getGpa()));

        ObservableList<Enrollment> data = FXCollections.observableArrayList(student.getEnrollments());
        myCoursesTable.setItems(data);
        totalEnrolledLabel.setText(String.valueOf(student.getEnrollments().size()));
        gpaLabel.setText(String.format("%.2f", student.getGpa()));

        messageLabel.setText(student.getEnrollments().isEmpty()
                ? "You are not enrolled in any courses yet."
                : "Welcome back, " + student.getFirstName()
                  + "! Enrolled in " + student.getEnrollments().size() + " course(s).");
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
            messageLabel.setText("Logout error: " + e.getMessage());
        }
    }
}
