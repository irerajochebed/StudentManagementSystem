package com.studentms.ui.controller;

import com.studentms.backend.auth.UserStore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField     usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label         errorLabel;
    @FXML private Button        loginBtn;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter username and password.");
            return;
        }

        UserStore.User user = UserStore.login(username, password);
        if (user == null) {
            errorLabel.setText("Invalid username or password.");
            passwordField.clear();
            return;
        }
        openDashboard(user);
    }

    @FXML
    private void goToRegister() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/register.fxml"));
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setScene(new Scene(root, 900, 650));
        } catch (Exception e) {
            errorLabel.setText("Error loading register page.");
        }
    }

    private void openDashboard(UserStore.User user) {
        try {
            String fxml = switch (user.getRole()) {
                case "STUDENT"    -> "/student_dashboard.fxml";
                case "INSTRUCTOR" -> "/instructor_dashboard.fxml";
                default           -> "/main.fxml";
            };

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();

            switch (user.getRole()) {
                case "STUDENT"    -> ((StudentDashboardController)    loader.getController()).initUser(user);
                case "INSTRUCTOR" -> ((InstructorDashboardController) loader.getController()).initUser(user);
                default           -> ((MainController)                loader.getController()).initUser(user);
            }

            Stage stage = (Stage) loginBtn.getScene().getWindow();
            Scene scene = new Scene(root, 1000, 700);
            scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Student Management System — " + user.getRole());
        } catch (Exception e) {
            errorLabel.setText("Error loading dashboard: " + e.getMessage());
        }
    }
}
