package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.exception.StudentManagementException;
import com.university.backend.manager.StudentManagementManager;
import com.university.backend.model.Student;
import com.university.ui.component.StatusLabel;
import javafx.application.Platform;
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
 * StudentController — UI bridge for student operations.
 *
 * Responsibilities (UI layer only):
 *   1. Capture input from fields
 *   2. Call StudentManagementManager (backend)
 *   3. Display results / errors — NO business logic here
 */
public class StudentController implements Initializable {

    // ── Input fields ───────────────────────────────────────────────
    @FXML private TextField studentIdField;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private Spinner<Integer> ageSpinner;
    @FXML private TextField majorField;
    @FXML private ComboBox<Integer> yearLevelCombo;

    // ── Search ─────────────────────────────────────────────────────
    @FXML private TextField searchField;

    // ── Table ──────────────────────────────────────────────────────
    @FXML private TableView<Student> studentTableView;
    @FXML private TableColumn<Student, String>  idColumn;
    @FXML private TableColumn<Student, String>  nameColumn;
    @FXML private TableColumn<Student, String>  emailColumn;
    @FXML private TableColumn<Student, Integer> ageColumn;
    @FXML private TableColumn<Student, String>  majorColumn;
    @FXML private TableColumn<Student, Integer> yearColumn;
    @FXML private TableColumn<Student, Double>  gpaColumn;

    // ── Status ─────────────────────────────────────────────────────
    @FXML private Label statusLabel;

    // ── Backend + component ────────────────────────────────────────
    private StudentManagementManager manager;
    private StatusLabel status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = StudentManagementApp.getManager();
        status  = new StatusLabel(statusLabel);

        // Spinner: age 16–100, default 20
        ageSpinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(16, 100, 20));

        // Year level dropdown
        yearLevelCombo.setItems(FXCollections.observableArrayList(1, 2, 3, 4));

        // Wire table columns to Student properties
        idColumn.setCellValueFactory(new PropertyValueFactory<>("personId"));
        nameColumn.setCellValueFactory(d ->
            new javafx.beans.property.SimpleStringProperty(d.getValue().getFullName()));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("yearLevel"));
        gpaColumn.setCellValueFactory(new PropertyValueFactory<>("gpa"));

        refreshTable();
    }

    // ── ADD ────────────────────────────────────────────────────────
    @FXML
    private void handleAddStudent() {
        // 1. Capture input
        String id        = studentIdField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName  = lastNameField.getText().trim();
        String email     = emailField.getText().trim();
        String major     = majorField.getText().trim();
        Integer year     = yearLevelCombo.getValue();

        // 2. Basic UI validation
        if (id.isEmpty() || firstName.isEmpty() || lastName.isEmpty()
                || email.isEmpty() || major.isEmpty() || year == null) {
            status.error("Please fill in all fields.");
            return;
        }

        try {
            // 3. Call backend
            Student student = new Student(id, firstName, lastName, email,
                                          ageSpinner.getValue(), major, year);
            manager.addStudent(student);

            // 4. Update UI
            status.success("Student " + student.getFullName() + " added!");
            handleClear();
            refreshTable();

        } catch (StudentManagementException e) {
            status.error(e.getMessage());   // show backend exception in UI
        }
    }

    // ── REMOVE ────────────────────────────────────────────────────
    @FXML
    private void handleRemoveStudent() {
        Student selected = studentTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            status.error("Select a student from the table first.");
            return;
        }
        // Confirm with a real JavaFX dialog
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "Remove " + selected.getFullName() + "?",
            ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirm Remove");
        confirm.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.YES) {
                manager.removeStudent(selected.getPersonId());
                Platform.runLater(() -> {
                    refreshTable();
                    status.success("Student removed.");
                });
            }
        });
    }

    // ── SEARCH ────────────────────────────────────────────────────
    @FXML
    private void handleSearch() {
        String term = searchField.getText().trim();
        if (term.isEmpty()) {
            refreshTable();
            status.info("Showing all students.");
            return;
        }
        // Call backend search
        List<Student> results = manager.searchStudentByName(term);
        Platform.runLater(() -> {
            studentTableView.setItems(FXCollections.observableArrayList(results));
            status.info("Found " + results.size() + " student(s) matching \"" + term + "\".");
        });
    }

    // ── SORT ──────────────────────────────────────────────────────
    @FXML
    private void handleSortByName() {
        // Get list from backend, sort it (bubble sort — beginner friendly)
        List<Student> list = new ArrayList<>(manager.getAllStudents());
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getFullName()
                        .compareTo(list.get(j + 1).getFullName()) > 0) {
                    Student temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        Platform.runLater(() -> {
            studentTableView.setItems(FXCollections.observableArrayList(list));
            status.info("Sorted by name (A → Z).");
        });
    }

    @FXML
    private void handleSortByGPA() {
        // Sort highest GPA first
        List<Student> list = new ArrayList<>(manager.getAllStudents());
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - 1 - i; j++) {
                if (list.get(j).getGpa() < list.get(j + 1).getGpa()) {
                    Student temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
        Platform.runLater(() -> {
            studentTableView.setItems(FXCollections.observableArrayList(list));
            status.info("Sorted by GPA (highest first).");
        });
    }

    // ── VIEW DETAILS ──────────────────────────────────────────────
    @FXML
    private void handleViewDetails() {
        Student s = studentTableView.getSelectionModel().getSelectedItem();
        if (s == null) { status.error("Select a student first."); return; }

        // Real JavaFX Alert for details
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Student Details");
        alert.setHeaderText(s.getFullName());
        alert.setContentText(
            "ID       : " + s.getPersonId()  + "\n" +
            "Email    : " + s.getEmail()      + "\n" +
            "Age      : " + s.getAge()        + "\n" +
            "Major    : " + s.getMajor()      + "\n" +
            "Year     : " + s.getYearLevel()  + "\n" +
            "GPA      : " + String.format("%.2f", s.getGpa()) + "\n" +
            "Courses  : " + s.getEnrollments().size()
        );
        alert.showAndWait();
    }

    // ── CLEAR / REFRESH ───────────────────────────────────────────
    @FXML
    private void handleClear() {
        studentIdField.clear();
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        majorField.clear();
        ageSpinner.getValueFactory().setValue(20);
        yearLevelCombo.setValue(null);
    }

    @FXML
    private void handleRefresh() {
        refreshTable();
        status.info("List refreshed.");
    }

    // ── Helper ────────────────────────────────────────────────────
    private void refreshTable() {
        Platform.runLater(() ->
            studentTableView.setItems(
                FXCollections.observableArrayList(manager.getAllStudents())));
    }
}
