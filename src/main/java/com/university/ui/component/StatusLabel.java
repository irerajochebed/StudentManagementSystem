package com.university.ui.component;

import javafx.application.Platform;
import javafx.scene.control.Label;

/**
 * Reusable UI component that shows success / error / info messages.
 * All controllers use this instead of duplicating setStatus logic.
 *
 * This is a custom JavaFX component — part of the /ui/component layer.
 */
public class StatusLabel {

    private final Label label;

    public StatusLabel(Label label) {
        this.label = label;
    }

    /** Show a green success message. */
    public void success(String message) {
        Platform.runLater(() -> {
            label.setText("✓ " + message);
            label.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        });
    }

    /** Show a red error message. */
    public void error(String message) {
        Platform.runLater(() -> {
            label.setText("✗ " + message);
            label.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        });
    }

    /** Show a blue info message. */
    public void info(String message) {
        Platform.runLater(() -> {
            label.setText("ℹ " + message);
            label.setStyle("-fx-text-fill: #2980b9; -fx-font-weight: bold;");
        });
    }

    /** Clear the label. */
    public void clear() {
        Platform.runLater(() -> {
            label.setText("Ready");
            label.setStyle("-fx-text-fill: #7f8c8d;");
        });
    }
}
