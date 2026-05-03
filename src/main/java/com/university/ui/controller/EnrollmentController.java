package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.manager.StudentManagementManager;
import com.university.backend.model.Enrollment;
import com.university.backend.model.Student;
import com.university.backend.model.Course;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Controller for the Enrollment Management Tab.
 * Handles student enrollment in courses and grade assignment.
 */
public class EnrollmentController implements Initializable {

    @FXML private ComboBox<String> studentCombo;
    @FXML private ComboBox<String> courseCombo;
    @FXML private ComboBox<String> enrollmentCombo;
    @FXML private Spinner<Double> gradeSpinner;
    @FXML private TableView<Enrollment> enrollmentTableView;
    @FXML private TableColumn<Enrollment, String> studentColumn;
    @FXML private TableColumn<Enrollment, String> courseCodeColumn;
    @FXML private TableColumn<Enrollment, String> courseNameColumn;
    @FXML private TableColumn<Enrollment, String> dateColumn;
    @FXML private TableColumn<Enrollment, String> statusColumn;
    @FXML private TableColumn<Enrollment, Double> gradeColumn;
    @FXML private TableColumn<Enrollment, String> letterGradeColumn;
    @FXML private Label statusLabel;

    private StudentManagementManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = StudentManagementApp.getManager();
        
        // Setup spinner
        SpinnerValueFactory<Double> gradeFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 4.0, 3.5, 0.1);
        gradeSpinner.setValueFactory(gradeFactory);
        
        // Setup table columns
        setupTableColumns();
        
        // Load data
        refreshEnrollmentData();
    }

    private void setupTableColumns() {
        studentColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStudent().getFullName()));
        courseCodeColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCourse().getCourseCode()));
        courseNameColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCourse().getCourseName()));
        dateColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEnrollmentDate().toString()));
        statusColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));
        gradeColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getGradePoints()));
        letterGradeColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLetterGrade()));
    }

    @FXML
    private void handleEnroll() {
        try {
            if (studentCombo.getValue() == null || courseCombo.getValue() == null) {
                setStatus("✗ Please select a student and course", true);
                return;
            }

            String studentId = studentCombo.getValue().split(" - ")[0];
            String courseCode = courseCombo.getValue().split(" - ")[0];

            manager.enrollStudent(studentId, courseCode);
            setStatus("✓ Student enrolled successfully!", false);
            refreshEnrollmentData();

        } catch (Exception e) {
            setStatus("✗ Error: " + e.getMessage(), true);
        }
    }

    @FXML
    private void handleAssignGrade() {
        try {
            if (enrollmentCombo.getValue() == null) {
                setStatus("✗ Please select an enrollment", true);
                return;
            }

            String[] parts = enrollmentCombo.getValue().split(" - ");
            String studentId = parts[0];
            String courseCode = parts[1];

            manager.assignGrade(studentId, courseCode, gradeSpinner.getValue());
            setStatus("✓ Grade assigned successfully!", false);
            refreshEnrollmentData();

        } catch (Exception e) {
            setStatus("✗ Error: " + e.getMessage(), true);
        }
    }

    @FXML
    private void handleRefresh() {
        refreshEnrollmentData();
        setStatus("✓ Enrollment list refreshed", false);
    }

    private void refreshEnrollmentData() {
        // Load students
        List<String> studentOptions = manager.getAllStudents().stream()
                .map(s -> s.getPersonId() + " - " + s.getFullName())
                .collect(Collectors.toList());
        studentCombo.setItems(FXCollections.observableArrayList(studentOptions));

        // Load available courses
        List<String> courseOptions = manager.getAllCourses().stream()
                .map(c -> c.getCourseCode() + " - " + c.getCourseName())
                .collect(Collectors.toList());
        courseCombo.setItems(FXCollections.observableArrayList(courseOptions));

        // Load enrollments
        List<Enrollment> enrollments = manager.getAllStudents().stream()
                .flatMap(s -> s.getEnrollments().stream())
                .collect(Collectors.toList());
        
        List<String> enrollmentOptions = enrollments.stream()
                .map(e -> e.getStudent().getPersonId() + " - " + e.getCourse().getCourseCode())
                .collect(Collectors.toList());
        enrollmentCombo.setItems(FXCollections.observableArrayList(enrollmentOptions));

        enrollmentTableView.setItems(FXCollections.observableArrayList(enrollments));
    }

    private void setStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setStyle(isError ? "-fx-text-fill: #e74c3c;" : "-fx-text-fill: #27ae60;");
    }
}
