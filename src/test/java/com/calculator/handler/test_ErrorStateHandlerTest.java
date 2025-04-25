package com.calculator.handler;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ErrorStateHandler Tests")
class test_ErrorStateHandlerTest {

    private ErrorStateHandler errorHandler;
    private TextField display;
    private static final String ERROR_STYLE_CLASS = "error";

    @BeforeAll
    static void setupJFX() {
        // Initialize JavaFX toolkit
        new JFXPanel();
    }

    @BeforeEach
    void setUp() {
        errorHandler = new ErrorStateHandler();
        // Run on JavaFX thread
        Platform.runLater(() -> display = new TextField());
        waitForFxThread();
    }

    private void waitForFxThread() {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> latch.countDown());
        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            fail("JavaFX thread didn't complete in time");
        }
    }

    @Nested
    @DisplayName("Error Display Tests")
    class ErrorDisplayTests {

        @Test
        @DisplayName("Show error message")
        void showErrorMessage() {
            String errorMessage = "Division by zero";
            
            Platform.runLater(() -> errorHandler.showError(display, errorMessage));
            waitForFxThread();

            assertTrue(display.getStyleClass().contains(ERROR_STYLE_CLASS));
            assertEquals(errorMessage, display.getText());
        }

        @Test
        @DisplayName("Clear error state")
        void clearErrorState() {
            Platform.runLater(() -> {
                errorHandler.showError(display, "Error");
                errorHandler.clearError(display);
            });
            waitForFxThread();

            assertFalse(display.getStyleClass().contains(ERROR_STYLE_CLASS));
            assertEquals("0", display.getText());
        }

        @Test
        @DisplayName("Auto-clear error after timeout")
        void autoClearError() throws InterruptedException {
            Platform.runLater(() -> errorHandler.showError(display, "Error"));
            waitForFxThread();
            
            // Wait for auto-clear (3 seconds + buffer)
            Thread.sleep(3500);
            waitForFxThread();

            assertFalse(display.getStyleClass().contains(ERROR_STYLE_CLASS));
        }
    }

    @Nested
    @DisplayName("Error State Detection Tests")
    class ErrorStateDetectionTests {

        @Test
        @DisplayName("Detect error state")
        void detectErrorState() {
            Platform.runLater(() -> {
                errorHandler.showError(display, "Error");
            });
            waitForFxThread();

            assertTrue(errorHandler.isInErrorState(display));
        }

        @Test
        @DisplayName("Detect non-error state")
        void detectNonErrorState() {
            Platform.runLater(() -> {
                display.setText("123");
            });
            waitForFxThread();

            assertFalse(errorHandler.isInErrorState(display));
        }
    }

    @Nested
    @DisplayName("Multiple Error Handling Tests")
    class MultipleErrorTests {

        @Test
        @DisplayName("Handle multiple consecutive errors")
        void handleConsecutiveErrors() {
            Platform.runLater(() -> {
                errorHandler.showError(display, "First Error");
                errorHandler.showError(display, "Second Error");
            });
            waitForFxThread();

            assertTrue(display.getStyleClass().contains(ERROR_STYLE_CLASS));
            assertEquals("Second Error", display.getText());
        }

        @Test
        @DisplayName("Clear error preserves valid input")
        void clearErrorPreservesValidInput() {
            Platform.runLater(() -> {
                display.setText("123");
                errorHandler.showError(display, "Error");
                errorHandler.clearError(display);
            });
            waitForFxThread();

            assertFalse(display.getStyleClass().contains(ERROR_STYLE_CLASS));
            assertEquals("0", display.getText());
        }
    }

    @Nested
    @DisplayName("Performance Tests")
    class PerformanceTests {

        @Test
        @DisplayName("Rapid error state changes")
        void rapidErrorStateChanges() {
            long startTime = System.currentTimeMillis();
            
            Platform.runLater(() -> {
                for (int i = 0; i < 100; i++) {
                    errorHandler.showError(display, "Error " + i);
                    errorHandler.clearError(display);
                }
            });
            waitForFxThread();
            
            long duration = System.currentTimeMillis() - startTime;
            assertTrue(duration < 1000, "Rapid error state changes should complete within 1 second");
        }
    }

    @Nested
    @DisplayName("Edge Cases")
    class EdgeCaseTests {

        @Test
        @DisplayName("Handle empty error message")
        void handleEmptyErrorMessage() {
            Platform.runLater(() -> errorHandler.showError(display, ""));
            waitForFxThread();

            assertTrue(display.getStyleClass().contains(ERROR_STYLE_CLASS));
            assertEquals("", display.getText());
        }

        @Test
        @DisplayName("Handle null error message")
        void handleNullErrorMessage() {
            Platform.runLater(() -> errorHandler.showError(display, null));
            waitForFxThread();

            assertTrue(display.getStyleClass().contains(ERROR_STYLE_CLASS));
            assertNull(display.getText());
        }
    }
}
