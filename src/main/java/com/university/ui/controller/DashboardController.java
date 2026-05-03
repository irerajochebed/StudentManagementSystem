package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.manager.StudentManagementManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the Dashboard Tab.
 * Displays system statistics and overview.
 */
public class DashboardController implements Initializable {

    @FXML private Label totalStudentsLabel;
    @FXML private Label totalCoursesLabel;
    @FXML private Label totalInstructorsLabel;
    @FXML private Label avgGPALabel;
    @FXML private TextArea activityTextArea;

    private StudentManagementManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        manager = StudentManagementApp.getManager();
        loadDashboardData();
    }

    private void loadDashboardData() {
        totalStudentsLabel.setText(String.valueOf(manager.getTotalStudents()));
        totalCoursesLabel.setText(String.valueOf(manager.getTotalCourses()));
        totalInstructorsLabel.setText(String.valueOf(manager.getTotalInstructors()));
        avgGPALabel.setText(String.format("%.2f", manager.getAverageStudentGPA()));

        activityTextArea.appendText("System initialized\n");
        activityTextArea.appendText("Ready for operations\n");
    }

    @FXML
    private void handleRefresh() {
        loadDashboardData();
        activityTextArea.appendText("\n[Refreshed at " + java.time.LocalDateTime.now() + "]\n");
    }

    @FXML
    private void handleClearLog() {
        activityTextArea.clear();
    }
}
