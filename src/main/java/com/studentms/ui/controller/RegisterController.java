package com.studentms.ui.controller;

import com.studentms.backend.auth.UserStore;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField     fullNameField;
    @FXML private TextField     usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleCombo;
    @FXML private TextField     linkedIdField;
    @FXML private Label         messageLabel;

    @FXML
    private void initialize() {
        roleCombo.setItems(FXCollections.observableArrayList("STUDENT", "INSTRUCTOR", "ADMIN"));
        roleCombo.setValue("STUDENT");
    }

    @FXML
    private void handleRegister() {
        String fullName = fullNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String role     = roleCombo.getValue();
        String linkedId = linkedIdField.getText().trim();

        String error = UserStore.register(username, password, role, fullName, linkedId);
        if (error != null) {
            messageLabel.setStyle("-fx-text-fill: #e53935; -fx-font-size: 12px;");
            messageLabel.setText(error);
            return;
        }

        messageLabel.setStyle("-fx-text-fill: #2e7d32; -fx-font-size: 12px;");
        messageLabel.setText("Account created! Redirecting to login...");

        javafx.animation.PauseTransition pause =
                new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1.2));
        pause.setOnFinished(e -> goToLogin());
        pause.play();
    }

    @FXML
    private void goToLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 650));
        } catch (Exception e) {
            messageLabel.setText("Error loading login page.");
        }
    }
}
