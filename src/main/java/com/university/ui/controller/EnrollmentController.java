package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.manager.StudentManagementManager;
import com.university.backend.model.Enrollment;
import com.university.ui.component.StatusLabel;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * EnrollmentController — UI bridge for enrollment and grade operations.
 * UI only: captures input → calls backend → displays output.
 */
public class EnrollmentController implements Initializable {

    // ── Enroll section ────────────────────────────────────────────
    @FXML private ComboBox<String> studentCombo;
    @FXML private ComboBox<String> courseCombo;

    // ── Grade section ─────────────────────────────────────────────
    @FXML private ComboBox<String>  enrollmentCombo;
    @FXML private Spinner<Double>   gradeSpinner;

    // ── Table ─────────────────────────────────────────────────────
    @FXML private TableView<Enrollment>            enrollmentTableView;
    @FXML private TableColumn<Enrollment, String>  studentColumn;
    @FXML private TableColumn<Enrollment, String>  courseCodeColumn;
    @FXML private TableColumn<Enrollment, String>  courseNameColumn;
    @FXML private TableColumn<Enrollment, String>  dateColumn;
    @FXML private TableColumn<Enrollment, String>  statusColumn;
    @FXML private TableColumn<Enrollment, Double>  gradeColumn;
    @FXML private TableColumn<Enrollment, String>  letterGradeColumn;

    @FXML private Label statusLabel;

    private StudentManagementManager manager;
    private StatusLabel status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = StudentManagementApp.getManager();
        status  = new StatusLabel(statusLabel);

        gradeSpinner.setValueFactory(
            new SpinnerValueFactory.DoubleSpinnerValueFactory(0.0, 4.0, 3.5, 0.1));

        // Wire table columns
        studentColumn.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getStudent().getFullName()));
        courseCodeColumn.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getCourse().getCourseCode()));
        courseNameColumn.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getCourse().getCourseName()));
        dateColumn.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getEnrollmentDate().toString()));
        statusColumn.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getStatus()));
        gradeColumn.setCellValueFactory(d ->
            new SimpleObjectProperty<>(d.getValue().getGradePoints()));
        letterGradeColumn.setCellValueFactory(d ->
            new SimpleStringProperty(d.getValue().getLetterGrade()));

        refreshAll();
    }

    // ── ENROLL ───────────────────────────────────────────────────
    @FXML
    private void handleEnroll() {
        if (studentCombo.getValue() == null || courseCombo.getValue() == null) {
            status.error("Select both a student and a course.");
            return;
        }
        try {
            // IDs are stored as "ID - Name" in the combo
            String studentId  = studentCombo.getValue().split(" - ")[0];
            String courseCode = courseCombo.getValue().split(" - ")[0];

            manager.enrollStudent(studentId, courseCode);
            status.success("Student enrolled successfully!");
            refreshAll();
        } catch (Exception e) {
            status.error(e.getMessage());
        }
    }

    // ── ASSIGN GRADE ─────────────────────────────────────────────
    @FXML
    private void handleAssignGrade() {
        if (enrollmentCombo.getValue() == null) {
            status.error("Select an enrollment first.");
            return;
        }
        try {
            String[] parts    = enrollmentCombo.getValue().split(" - ");
            String studentId  = parts[0];
            String courseCode = parts[1];

            manager.assignGrade(studentId, courseCode, gradeSpinner.getValue());
            status.success("Grade assigned successfully!");
            refreshAll();
        } catch (Exception e) {
            status.error(e.getMessage());
        }
    }

    // ── SORT by student name ──────────────────────────────────────
    @FXML
    private void handleSortByStudent() {
        List<Enrollment> list = getAllEnrollments();
        for (int i = 0; i < list.size() - 1; i++)
            for (int j = 0; j < list.size() - 1 - i; j++)
                if (list.get(j).getStudent().getFullName()
                        .compareTo(list.get(j + 1).getStudent().getFullName()) > 0) {
                    Enrollment t = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, t);
                }
        Platform.runLater(() -> {
            enrollmentTableView.setItems(FXCollections.observableArrayList(list));
            status.info("Sorted by student name.");
        });
    }

    @FXML
    private void handleRefresh() {
        refreshAll();
        status.info("Enrollment list refreshed.");
    }

    // ── Helpers ───────────────────────────────────────────────────
    private void refreshAll() {
        Platform.runLater(() -> {
            // Populate student combo: "ID - Full Name"
            List<String> students = manager.getAllStudents().stream()
                .map(s -> s.getPersonId() + " - " + s.getFullName())
                .collect(Collectors.toList());
            studentCombo.setItems(FXCollections.observableArrayList(students));

            // Populate course combo: "Code - Name"
            List<String> courses = manager.getAllCourses().stream()
                .map(c -> c.getCourseCode() + " - " + c.getCourseName())
                .collect(Collectors.toList());
            courseCombo.setItems(FXCollections.observableArrayList(courses));

            // Populate enrollment combo for grade assignment
            List<Enrollment> enrollments = getAllEnrollments();
            List<String> enrollOpts = enrollments.stream()
                .map(e -> e.getStudent().getPersonId() + " - " + e.getCourse().getCourseCode())
                .collect(Collectors.toList());
            enrollmentCombo.setItems(FXCollections.observableArrayList(enrollOpts));

            // Populate table
            enrollmentTableView.setItems(FXCollections.observableArrayList(enrollments));
        });
    }

    private List<Enrollment> getAllEnrollments() {
        List<Enrollment> all = new ArrayList<>();
        manager.getAllStudents().forEach(s -> all.addAll(s.getEnrollments()));
        return all;
    }
}
