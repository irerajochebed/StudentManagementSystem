package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.manager.StudentManagementManager;
import com.university.ui.component.StatusLabel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * DashboardController — shows live system statistics.
 * UI only: reads from backend manager and displays numbers.
 */
public class DashboardController implements Initializable {

    @FXML private Label   totalStudentsLabel;
    @FXML private Label   totalCoursesLabel;
    @FXML private Label   totalInstructorsLabel;
    @FXML private Label   avgGPALabel;
    @FXML private TextArea activityTextArea;
    @FXML private Label   statusLabel;

    private StudentManagementManager manager;
    private StatusLabel status;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        manager = StudentManagementApp.getManager();
        status  = new StatusLabel(statusLabel);
        loadStats();
        log("System started — ready for operations.");
    }

    @FXML
    private void handleRefresh() {
        // Platform.runLater ensures UI update is on the JavaFX Application Thread
        Platform.runLater(() -> {
            loadStats();
            log("Dashboard refreshed at "
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            status.success("Dashboard refreshed.");
        });
    }

    @FXML
    private void handleClearLog() {
        activityTextArea.clear();
    }

    // ── Helpers ───────────────────────────────────────────────────
    private void loadStats() {
        totalStudentsLabel.setText(String.valueOf(manager.getTotalStudents()));
        totalCoursesLabel.setText(String.valueOf(manager.getTotalCourses()));
        totalInstructorsLabel.setText(String.valueOf(manager.getTotalInstructors()));
        avgGPALabel.setText(String.format("%.2f", manager.getAverageStudentGPA()));
    }

    private void log(String message) {
        activityTextArea.appendText("• " + message + "\n");
    }
}
