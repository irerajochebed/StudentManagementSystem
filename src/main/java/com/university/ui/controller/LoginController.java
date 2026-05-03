package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter both username and password");
            return;
        }

        if (StudentManagementApp.getAuthManager().login(username, password)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
                Parent root = loader.load();
                
                Stage stage = (Stage) usernameField.getScene().getWindow();
                Scene scene = new Scene(root);
                String css = getClass().getResource("/css/styles.css").toExternalForm();
                scene.getStylesheets().add(css);
                
                stage.setScene(scene);
                stage.setTitle("University Student Management System v2.0");
                stage.setWidth(1200);
                stage.setHeight(700);
            } catch (Exception e) {
                showError("Error loading main window: " + e.getMessage());
            }
        } else {
            showError("Invalid username or password");
        }
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Register.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(root);
            String css = getClass().getResource("/css/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
        } catch (Exception e) {
            showError("Error loading registration: " + e.getMessage());
        }
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
