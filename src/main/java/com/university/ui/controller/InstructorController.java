package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.manager.StudentManagementManager;
import com.university.backend.model.Instructor;
import com.university.backend.exception.StudentManagementException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for the Instructor Management Tab.
 * Handles CRUD operations for instructors.
 */
public class InstructorController implements Initializable {

    @FXML private TextField instructorIdField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private Spinner<Integer> ageSpinner;
    @FXML private TextField specializationField;
    @FXML private TextField officeField;
    @FXML private TextField salaryField;
    @FXML private TableView<Instructor> instructorTableView;
    @FXML private TableColumn<Instructor, String> idColumn;
    @FXML private TableColumn<Instructor, String> nameColumn;
    @FXML private TableColumn<Instructor, String> emailColumn;
    @FXML private TableColumn<Instructor, Integer> ageColumn;
    @FXML private TableColumn<Instructor, String> specializationColumn;
    @FXML private TableColumn<Instructor, String> officeColumn;
    @FXML private TableColumn<Instructor, Integer> coursesColumn;
    @FXML private TableColumn<Instructor, Void> actionsColumn;
    @FXML private Label statusLabel;

    private StudentManagementManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = StudentManagementApp.getManager();
        
        // Setup spinner
        SpinnerValueFactory<Integer> ageFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(20, 80, 35);
        ageSpinner.setValueFactory(ageFactory);
        
        // Setup table columns
        setupTableColumns();
        
        // Load data
        refreshInstructorTable();
    }

    private void setupTableColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("personId"));
        nameColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFullName()));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        officeColumn.setCellValueFactory(new PropertyValueFactory<>("officeLocation"));
        coursesColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getAssignedCourses().size()));
    }

    @FXML
    private void handleAddInstructor() {
        try {
            if (instructorIdField.getText().isEmpty() || firstNameField.getText().isEmpty() ||
                lastNameField.getText().isEmpty() || emailField.getText().isEmpty()) {
                setStatus("✗ Please fill in all required fields", true);
                return;
            }

            double salary = 0;
            if (!salaryField.getText().isEmpty()) {
                salary = Double.parseDouble(salaryField.getText());
            }

            Instructor instructor = new Instructor(
                    instructorIdField.getText(),
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    ageSpinner.getValue(),
                    specializationField.getText(),
                    officeField.getText(),
                    salary
            );

            manager.addInstructor(instructor);
            setStatus("✓ Instructor added successfully!", false);
            handleClear();
            refreshInstructorTable();

        } catch (StudentManagementException e) {
            setStatus("✗ Error: " + e.getMessage(), true);
        }
    }

    @FXML
    private void handleRemoveInstructor() {
        Instructor selected = instructorTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            manager.removeInstructor(selected.getPersonId());
            setStatus("✓ Instructor removed successfully!", false);
            refreshInstructorTable();
        } else {
            setStatus("✗ Please select an instructor to remove", true);
        }
    }

    @FXML
    private void handleViewDetails() {
        Instructor selected = instructorTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String details = String.format(
                    "ID: %s\nName: %s\nEmail: %s\nAge: %d\nSpecialization: %s\nOffice: %s\nSalary: $%.2f\nCourses Taught: %d",
                    selected.getPersonId(),
                    selected.getFullName(),
                    selected.getEmail(),
                    selected.getAge(),
                    selected.getSpecialization(),
                    selected.getOfficeLocation(),
                    selected.getSalary(),
                    selected.getAssignedCourses().size()
            );
            showAlert("Instructor Details", details);
        } else {
            setStatus("✗ Please select an instructor to view details", true);
        }
    }

    @FXML
    private void handleClear() {
        instructorIdField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        ageSpinner.getValueFactory().setValue(35);
        specializationField.clear();
        officeField.clear();
        salaryField.clear();
    }

    @FXML
    private void handleRefresh() {
        refreshInstructorTable();
        setStatus("✓ Instructor list refreshed", false);
    }

    private void refreshInstructorTable() {
        List<Instructor> instructors = manager.getAllInstructors();
        instructorTableView.setItems(FXCollections.observableArrayList(instructors));
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
