package com.calculator.handler;

import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

/**
 * PUBLIC_INTERFACE
 * Handler for managing error states in the calculator UI.
 */
@Component
public class ErrorStateHandler {
    private static final String ERROR_STYLE_CLASS = "error";
    private static final long ERROR_DISPLAY_DURATION = 3000; // 3 seconds

    /**
     * PUBLIC_INTERFACE
     * Display an error state in the calculator display.
     *
     * @param display the display TextField
     * @param errorMessage the error message to display
     */
    public void showError(TextField display, String errorMessage) {
        if (!display.getStyleClass().contains(ERROR_STYLE_CLASS)) {
            display.getStyleClass().add(ERROR_STYLE_CLASS);
        }
        display.setText(errorMessage);

        // Clear error state after a delay
        new Thread(() -> {
            try {
                Thread.sleep(ERROR_DISPLAY_DURATION);
                javafx.application.Platform.runLater(() -> clearError(display));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * PUBLIC_INTERFACE
     * Clear the error state from the calculator display.
     *
     * @param display the display TextField
     */
    public void clearError(TextField display) {
        display.getStyleClass().remove(ERROR_STYLE_CLASS);
        if (display.getText().startsWith("Error")) {
            display.setText("0");
        }
    }

    /**
     * PUBLIC_INTERFACE
     * Check if the calculator is in an error state.
     *
     * @param display the display TextField
     * @return true if in error state, false otherwise
     */
    public boolean isInErrorState(TextField display) {
        return display.getStyleClass().contains(ERROR_STYLE_CLASS);
    }
}
