package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.exception.StudentManagementException;
import com.university.backend.manager.StudentManagementManager;
import com.university.backend.model.Course;
import com.university.backend.model.User;
import com.university.ui.component.StatusLabel;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * CourseController — UI bridge for course operations.
 * UI only: captures input → calls backend → displays output.
 */
public class CourseController implements Initializable {

    @FXML private TextField courseCodeField;
    @FXML private TextField courseNameField;
    @FXML private TextArea  descriptionArea;
    @FXML private Spinner<Integer> creditHoursSpinner;
    @FXML private Spinner<Integer> maxCapacitySpinner;
    @FXML private ComboBox<String> semesterCombo;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button clearButton;

    @FXML private TableView<Course>            courseTableView;
    @FXML private TableColumn<Course, String>  codeColumn;
    @FXML private TableColumn<Course, String>  nameColumn;
    @FXML private TableColumn<Course, Integer> creditsColumn;
    @FXML private TableColumn<Course, String>  semesterColumn;
    @FXML private TableColumn<Course, String>  capacityColumn;
    @FXML private TableColumn<Course, String>  statusColumn;

    @FXML private Label statusLabel;

    private StudentManagementManager manager;
    private StatusLabel status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = StudentManagementApp.getManager();
        status  = new StatusLabel(statusLabel);

        creditHoursSpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 3));
        maxCapacitySpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 30));

        semesterCombo.setItems(
            FXCollections.observableArrayList("Fall", "Spring", "Summer", "Winter"));

        // Wire columns
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        creditsColumn.setCellValueFactory(new PropertyValueFactory<>("creditHours"));
        semesterColumn.setCellValueFactory(new PropertyValueFactory<>("semester"));
        capacityColumn.setCellValueFactory(d ->
            new SimpleStringProperty(
                d.getValue().getEnrolledCount() + "/" + d.getValue().getMaxCapacity()));
        statusColumn.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().isFull() ? "FULL" : "OPEN"));

        configureRoleBasedAccess();
        refreshTable();
    }

    private void configureRoleBasedAccess() {
        User currentUser = StudentManagementApp.getAuthManager().getCurrentUser();
        if (currentUser == null) return;

        if (currentUser.getRole() != User.UserRole.ADMIN) {
            if (addButton != null) addButton.setDisable(true);
            if (removeButton != null) removeButton.setDisable(true);
            if (clearButton != null) clearButton.setDisable(true);
            courseCodeField.setDisable(true);
            courseNameField.setDisable(true);
            descriptionArea.setDisable(true);
            creditHoursSpinner.setDisable(true);
            maxCapacitySpinner.setDisable(true);
            semesterCombo.setDisable(true);
        }
    }

    // ── ADD ───────────────────────────────────────────────────────
    @FXML
    private void handleAddCourse() {
        String code = courseCodeField.getText().trim();
        String name = courseNameField.getText().trim();
        String sem  = semesterCombo.getValue();

        if (code.isEmpty() || name.isEmpty() || sem == null) {
            status.error("Course Code, Name and Semester are required.");
            return;
        }
        try {
            Course course = new Course(code, name, descriptionArea.getText(),
                creditHoursSpinner.getValue(), maxCapacitySpinner.getValue(), sem);
            manager.addCourse(course);
            status.success("Course \"" + name + "\" added!");
            handleClear();
            refreshTable();
        } catch (StudentManagementException e) {
            status.error(e.getMessage());
        }
    }

    // ── REMOVE ───────────────────────────────────────────────────
    @FXML
    private void handleRemoveCourse() {
        Course selected = courseTableView.getSelectionModel().getSelectedItem();
        if (selected == null) { status.error("Select a course first."); return; }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Remove course " + selected.getCourseCode() + "?",
            ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Remove");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                manager.removeCourse(selected.getCourseCode());
                Platform.runLater(() -> {
                    refreshTable();
                    status.success("Course removed.");
                });
            }
        });
    }

    // ── SEARCH ───────────────────────────────────────────────────
    @FXML
    private void handleSearch() {
        String term = courseCodeField.getText().trim();
        if (term.isEmpty()) { refreshTable(); return; }
        List<Course> results = manager.searchCourseByName(term);
        Platform.runLater(() -> {
            courseTableView.setItems(FXCollections.observableArrayList(results));
            status.info("Found " + results.size() + " course(s).");
        });
    }

    // ── SORT by name ─────────────────────────────────────────────
    @FXML
    private void handleSortByName() {
        List<Course> list = new ArrayList<>(manager.getAllCourses());
        for (int i = 0; i < list.size() - 1; i++)
            for (int j = 0; j < list.size() - 1 - i; j++)
                if (list.get(j).getCourseName()
                        .compareTo(list.get(j + 1).getCourseName()) > 0) {
                    Course t = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, t);
                }
        Platform.runLater(() -> {
            courseTableView.setItems(FXCollections.observableArrayList(list));
            status.info("Sorted by course name.");
        });
    }

    // ── VIEW DETAILS ─────────────────────────────────────────────
    @FXML
    private void handleViewDetails() {
        Course c = courseTableView.getSelectionModel().getSelectedItem();
        if (c == null) { status.error("Select a course first."); return; }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Course Details");
        alert.setHeaderText(c.getCourseCode() + " — " + c.getCourseName());
        alert.setContentText(
            "Credits     : " + c.getCreditHours()  + "\n" +
            "Semester    : " + c.getSemester()      + "\n" +
            "Capacity    : " + c.getEnrolledCount() + "/" + c.getMaxCapacity() + "\n" +
            "Status      : " + (c.isFull() ? "FULL" : "OPEN") + "\n" +
            "Description : " + c.getDescription()
        );
        alert.showAndWait();
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
        refreshTable();
        status.info("Course list refreshed.");
    }

    private void refreshTable() {
        Platform.runLater(() ->
            courseTableView.setItems(
                FXCollections.observableArrayList(manager.getAllCourses())));
    }
}
