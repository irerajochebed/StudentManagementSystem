package com.university.ui.controller;

import com.university.app.StudentManagementApp;
import com.university.backend.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private TextField linkedIdField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("ADMIN", "INSTRUCTOR", "STUDENT");
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String roleStr = roleComboBox.getValue();
        String linkedId = linkedIdField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || roleStr == null) {
            showMessage("Please fill all required fields", false);
            return;
        }

        if (!password.equals(confirmPassword)) {
            showMessage("Passwords do not match", false);
            return;
        }

        if (password.length() < 4) {
            showMessage("Password must be at least 4 characters", false);
            return;
        }

        User.UserRole role = User.UserRole.valueOf(roleStr);
        
        if (StudentManagementApp.getAuthManager().register(username, password, role, linkedId.isEmpty() ? null : linkedId)) {
            showMessage("Registration successful! Redirecting to login...", true);
            
            javafx.application.Platform.runLater(() -> {
                try {
                    Thread.sleep(1500);
                    handleBackToLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            showMessage("Username already exists", false);
        }
    }

    @FXML
    private void handleBackToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(root);
            String css = getClass().getResource("/css/styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setScene(scene);
        } catch (Exception e) {
            showMessage("Error loading login: " + e.getMessage(), false);
        }
    }

    private void showMessage(String message, boolean success) {
        messageLabel.setText(message);
        messageLabel.setTextFill(success ? javafx.scene.paint.Color.GREEN : javafx.scene.paint.Color.RED);
        messageLabel.setVisible(true);
    }
}
