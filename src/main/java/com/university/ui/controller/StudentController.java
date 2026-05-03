package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.manager.StudentManagementManager;
import com.university.backend.model.Student;
import com.university.backend.exception.StudentManagementException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the Student Management Tab.
 * Handles CRUD operations for students.
 */
public class StudentController implements Initializable {

    @FXML private TextField studentIdField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private Spinner<Integer> ageSpinner;
    @FXML private TextField majorField;
    @FXML private ComboBox<Integer> yearLevelCombo;
    @FXML private TextField searchField;
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, String> idColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> emailColumn;
    @FXML private TableColumn<Student, Integer> ageColumn;
    @FXML private TableColumn<Student, String> majorColumn;
    @FXML private TableColumn<Student, Integer> yearColumn;
    @FXML private TableColumn<Student, Double> gpaColumn;
    @FXML private TableColumn<Student, Void> actionsColumn;
    @FXML private Label statusLabel;

    private StudentManagementManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = StudentManagementApp.getManager();
        
        // Setup spinners
        SpinnerValueFactory<Integer> ageFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(16, 100, 20);
        ageSpinner.setValueFactory(ageFactory);
        
        // Setup ComboBox
        yearLevelCombo.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
        
        // Setup TableColumns
        setupTableColumns();
        
        // Load data
        refreshStudentTable();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("personId"));
        nameColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearLevel"));
        gpaColumn.setCellValueFactory(new PropertyValueFactory<>("gpa"));
    }

    @FXML
    private void handleAddStudent() {
        try {
            // Validate inputs
            if (studentIdField.getText().isEmpty() || firstNameField.getText().isEmpty() || 
                lastNameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                yearLevelCombo.getValue() == null) {
                setStatus("✗ Please fill in all required fields", true);
                return;
            }

            Student student = new Student(
                    studentIdField.getText(),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    ageSpinner.getValue(),
                    majorField.getText(),
                    yearLevelCombo.getValue()
            );

            manager.addStudent(student);
            setStatus("✓ Student added successfully!", false);
            handleClear();
            refreshStudentTable();

        } catch (StudentManagementException e) {
            setStatus("✗ Error: " + e.getMessage(), true);
        }
    }

    @FXML
    private void handleRemoveStudent() {
        Student selected = studentTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            manager.removeStudent(selected.getPersonId());
            setStatus("✓ Student removed successfully!", false);
            refreshStudentTable();
        } else {
            setStatus("✗ Please select a student to remove", true);
        }
    }

    @FXML
    private void handleViewDetails() {
        Student selected = studentTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String details = String.format(
                    "ID: %s\nName: %s\nEmail: %s\nAge: %d\nMajor: %s\nYear: %d\nGPA: %.2f\nEnrollments: %d",
                    selected.getPersonId(),
                    selected.getFullName(),
                    selected.getEmail(),
                    selected.getAge(),
                    selected.getMajor(),
                    selected.getYearLevel(),
                    selected.getGpa(),
                    selected.getEnrollments().size()
            );
            showAlert("Student Details", details);
        } else {
            setStatus("✗ Please select a student to view details", true);
        }
    }

    @FXML
    private void handleSearch() {
        String searchText = searchField.getText();
        if (searchText.isEmpty()) {
            refreshStudentTable();
        } else {
            List<Student> results = manager.searchStudentByName(searchText);
            studentTableView.setItems(FXCollections.observableArrayList(results));
        }
    }

    @FXML
    private void handleClear() {
        studentIdField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        ageSpinner.getValueFactory().setValue(20);
        majorField.clear();
        yearLevelCombo.setValue(null);
    }

    @FXML
    private void handleRefresh() {
        refreshStudentTable();
        setStatus("✓ Student list refreshed", false);
    }

    private void refreshStudentTable() {
        List<Student> students = manager.getAllStudents();
        studentTableView.setItems(FXCollections.observableArrayList(students));
    }

    private void setStatus(String message, boolean isError) {
        statusLabel.setText(message);
        statusLabel.setStyle(isError ? "-fx-text-fill: #e74c3c;" : "-fx-text-fill: #27ae60;");
    }

    private void showAlert(String title, String message) {
        System.out.println(title + ": " + message);
        // TODO: Implement proper alert dialog
    }
}
