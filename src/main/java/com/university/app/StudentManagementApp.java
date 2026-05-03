package com.university.app;

import com.university.backend.manager.StudentManagementManager;
import com.university.util.FileIOHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main JavaFX Application entry point for the Student Management System.
 * Initializes the application, loads the main scene, and manages the stage.
 */
public class StudentManagementApp extends Application {

    private static StudentManagementManager manager;
    private static FileIOHandler fileIOHandler;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Initialize backend
        manager = new StudentManagementManager();
        fileIOHandler = new FileIOHandler();

        // Load existing data
        fileIOHandler.loadDataIntoManager(manager);

        // Load FXML and create scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindow.fxml"));
        javafx.scene.Parent root = loader.load();

        // Setup scene and stage
        Scene scene = new Scene(root);
        primaryStage.setTitle("University Student Management System v2.0");
        primaryStage.setWidth(1200);
        primaryStage.setHeight(700);
        primaryStage.setScene(scene);

        // Add CSS styling
        String css = getClass().getResource("/css/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.show();
    }

    @Override
    public void stop() {
        // Save data before closing
        if (manager != null && fileIOHandler != null) {
            fileIOHandler.saveManager(manager);
        }
    }

    public static StudentManagementManager getManager() {
        return manager;
    }

    public static FileIOHandler getFileIOHandler() {
        return fileIOHandler;
    }

    /** Resets the manager to a fresh empty state (used by Clear All). */
    public static void resetManager() {
        manager = new StudentManagementManager();
        if (fileIOHandler != null) fileIOHandler.saveManager(manager);
    }
}
