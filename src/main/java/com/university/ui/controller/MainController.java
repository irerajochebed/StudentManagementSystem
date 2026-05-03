package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.manager.StudentManagementManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.application.Platform;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Main Window.
 * Manages the tab pane and application-level events.
 */
public class MainController implements Initializable {

    @FXML private TabPane mainTabPane;
    @FXML private Label statusLabel;
    @FXML private Label statsLabel;

    private StudentManagementManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = StudentManagementApp.getManager();
        updateStatistics();
    }

    @FXML
    private void handleSaveData() {
        try {
            StudentManagementApp.getFileIOHandler().saveManager(manager);
            updateStatus("✓ Data saved successfully!");
        } catch (Exception e) {
            updateStatus("✗ Error saving data: " + e.getMessage());
        }
    }

    @FXML
    private void handleLoadData() {
        try {
            StudentManagementApp.getFileIOHandler().loadDataIntoManager(manager);
            updateStatus("✓ Data loaded successfully!");
            updateStatistics();
        } catch (Exception e) {
            updateStatus("✗ Error loading data: " + e.getMessage());
        }
    }

    @FXML
    private void handleClearAll() {
        if (confirmAction("Are you sure you want to clear all data?")) {
            manager = new StudentManagementManager();
            StudentManagementApp.getFileIOHandler().saveManager(manager);
            updateStatus("✓ All data cleared!");
            updateStatistics();
        }
    }

    @FXML
    private void handleAbout() {
        String about = "University Student Management System v2.0\n\n" +
                "A comprehensive JavaFX application for managing university operations including:\n" +
                "• Student registration and management\n" +
                "• Course creation and enrollment\n" +
                "• Instructor assignment\n" +
                "• Grade tracking and GPA calculation\n\n" +
                "© 2026 University Management System";
        showAlert("About", about);
    }

    public void updateStatistics() {
        statsLabel.setText(String.format(
                "Students: %d | Courses: %d | Instructors: %d",
                manager.getTotalStudents(),
                manager.getTotalCourses(),
                manager.getTotalInstructors()
        ));
    }

    private void updateStatus(String message) {
        Platform.runLater(() -> statusLabel.setText(message));
    }

    private boolean confirmAction(String message) {
        // TODO: Implement proper confirmation dialog
        return true;
    }

    private void showAlert(String title, String message) {
        // TODO: Implement proper alert dialog
        System.out.println(title + ": " + message);
    }
}
