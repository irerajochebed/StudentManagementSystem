package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.manager.StudentManagementManager;
import com.university.ui.component.StatusLabel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * MainController — manages the top toolbar and status bar.
 * Handles Save, Load, Clear All, and About actions.
 */
public class MainController implements Initializable {

    @FXML private TabPane mainTabPane;
    @FXML private Label   statusLabel;
    @FXML private Label   statsLabel;

    private StudentManagementManager manager;
    private StatusLabel status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = StudentManagementApp.getManager();
        status  = new StatusLabel(statusLabel);
        updateStats();
    }

    // ── SAVE ──────────────────────────────────────────────────────
    @FXML
    private void handleSaveData() {
        try {
            StudentManagementApp.getFileIOHandler().saveManager(manager);
            Platform.runLater(() -> {
                status.success("Data saved to /data folder.");
                updateStats();
            });
        } catch (Exception e) {
            status.error("Save failed: " + e.getMessage());
        }
    }

    // ── LOAD ──────────────────────────────────────────────────────
    @FXML
    private void handleLoadData() {
        try {
            StudentManagementApp.getFileIOHandler().loadDataIntoManager(manager);
            Platform.runLater(() -> {
                status.success("Data loaded successfully.");
                updateStats();
            });
        } catch (Exception e) {
            status.error("Load failed: " + e.getMessage());
        }
    }

    // ── CLEAR ALL ─────────────────────────────────────────────────
    @FXML
    private void handleClearAll() {
        // Real JavaFX confirmation dialog
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
            "This will delete ALL students, courses and instructors.\nAre you sure?",
            ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Clear All Data");
        confirm.setHeaderText("Warning: This cannot be undone.");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            // Reset manager — backend handles clearing
            StudentManagementApp.resetManager();
            Platform.runLater(() -> {
                status.success("All data cleared.");
                updateStats();
            });
        }
    }

    // ── ABOUT ─────────────────────────────────────────────────────
    @FXML
    private void handleAbout() {
        Alert about = new Alert(Alert.AlertType.INFORMATION);
        about.setTitle("About");
        about.setHeaderText("University Student Management System v2.0");
        about.setContentText(
            "A JavaFX Maven application demonstrating:\n\n" +
            "  • OOP — Person, Student, Instructor, Course\n" +
            "  • Generics — type-safe collections\n" +
            "  • Collections — ArrayList for data storage\n" +
            "  • Exception Handling — custom exceptions\n" +
            "  • File I/O — CSV save and load\n" +
            "  • JavaFX UI — FXML + Controllers\n\n" +
            "UI layer calls backend only — no business logic in controllers."
        );
        about.showAndWait();
    }

    // ── Helper ────────────────────────────────────────────────────
    public void updateStats() {
        statsLabel.setText(String.format(
            "Students: %d  |  Courses: %d  |  Instructors: %d",
            manager.getTotalStudents(),
            manager.getTotalCourses(),
            manager.getTotalInstructors()));
    }
}
