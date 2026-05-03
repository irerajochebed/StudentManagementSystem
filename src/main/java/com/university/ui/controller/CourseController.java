package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.manager.StudentManagementManager;
import com.university.backend.model.Course;
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
 * Controller for the Course Management Tab.
 * Handles CRUD operations for courses.
 */
public class CourseController implements Initializable {

    @FXML private TextField courseCodeField;
    @FXML private TextField courseNameField;
    @FXML private TextArea descriptionArea;
    @FXML private Spinner<Integer> creditHoursSpinner;
    @FXML private Spinner<Integer> maxCapacitySpinner;
    @FXML private ComboBox<String> semesterCombo;
    @FXML private TableView<Course> courseTableView;
    @FXML private TableColumn<Course, String> codeColumn;
    @FXML private TableColumn<Course, String> nameColumn;
    @FXML private TableColumn<Course, Integer> creditsColumn;
    @FXML private TableColumn<Course, String> semesterColumn;
    @FXML private TableColumn<Course, String> capacityColumn;
    @FXML private TableColumn<Course, String> statusColumn;
    @FXML private TableColumn<Course, Void> actionsColumn;
    @FXML private Label statusLabel;

    private StudentManagementManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = StudentManagementApp.getManager();
        
        // Setup spinners
        SpinnerValueFactory<Integer> creditFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 3);
        creditHoursSpinner.setValueFactory(creditFactory);
        
        SpinnerValueFactory<Integer> capacityFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 30);
        maxCapacitySpinner.setValueFactory(capacityFactory);
        
        // Setup semester combobox
        semesterCombo.setItems(FXCollections.observableArrayList("Fall", "Spring", "Summer", "Winter"));
        
        // Setup table columns
        setupTableColumns();
        
        // Load data
        refreshCourseTable();
    }

    private void setupTableColumns() {
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("creditHours"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        
        capacityColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getEnrolledCount() + "/" + cellData.getValue().getMaxCapacity()
            ));
        
        statusColumn.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().isFull() ? "FULL" : "OPEN"
            ));
    }

    @FXML
    private void handleAddCourse() {
        try {
            if (courseCodeField.getText().isEmpty() || courseNameField.getText().isEmpty() ||
                semesterCombo.getValue() == null) {
                setStatus("✗ Please fill in all required fields", true);
                return;
            }

            Course course = new Course(
                    courseCodeField.getText(),
                    courseNameField.getText(),
                    descriptionArea.getText(),
                    creditHoursSpinner.getValue(),
                    maxCapacitySpinner.getValue(),
                    semesterCombo.getValue()
            );

            manager.addCourse(course);
            setStatus("✓ Course added successfully!", false);
            handleClear();
            refreshCourseTable();

        } catch (StudentManagementException e) {
            setStatus("✗ Error: " + e.getMessage(), true);
        }
    }

    @FXML
    private void handleRemoveCourse() {
        Course selected = courseTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            manager.removeCourse(selected.getCourseCode());
            setStatus("✓ Course removed successfully!", false);
            refreshCourseTable();
        } else {
            setStatus("✗ Please select a course to remove", true);
        }
    }

    @FXML
    private void handleViewDetails() {
        Course selected = courseTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String details = String.format(
                    "Code: %s\nName: %s\nCredits: %d\nSemester: %s\nCapacity: %d/%d\nDescription: %s",
                    selected.getCourseCode(),
                    selected.getCourseName(),
                    selected.getCreditHours(),
                    selected.getSemester(),
                    selected.getEnrolledCount(),
                    selected.getMaxCapacity(),
                    selected.getDescription()
            );
            showAlert("Course Details", details);
        } else {
            setStatus("✗ Please select a course to view details", true);
        }
    }

    @FXML
    private void handleClear() {
        courseCodeField.clear();
        courseNameField.clear();
        descriptionArea.clear();
        creditHoursSpinner.getValueFactory().setValue(3);
        maxCapacitySpinner.getValueFactory().setValue(30);
        semesterCombo.setValue(null);
    }

    @FXML
    private void handleRefresh() {
        refreshCourseTable();
        setStatus("✓ Course list refreshed", false);
    }

    private void refreshCourseTable() {
        List<Course> courses = manager.getAllCourses();
        courseTableView.setItems(FXCollections.observableArrayList(courses));
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
