package com.calculator.handler;

import com.calculator.model.CalculationResult;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@DisplayName("KeyboardNavigationTest Tests")
class test_KeyboardNavigationTest {

    @Mock
    private ArithmeticOperationsHandler operationsHandler;
    
    private KeyboardInputHandler keyboardHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        keyboardHandler = new KeyboardInputHandler(operationsHandler);
    }

    @Nested
    @DisplayName("Numeric Input Tests")
    class NumericInputTests {

        @Test
        @DisplayName("Process valid numeric key input")
        void processValidNumericInput() {
            // Create a key event for numeric input
            KeyEvent event = new KeyEvent(KeyEvent.KEY_TYPED, "5", "5", KeyCode.DIGIT5, false, false, false, false);
            when(operationsHandler.processDigit("5"))
                .thenReturn("5");

            CalculationResult result = keyboardHandler.processKeyEvent(event);

            assertTrue(result.isSuccess());
            verify(operationsHandler).processDigit("5");
        }

        @Test
        @DisplayName("Process decimal point input")
        void processDecimalPoint() {
            KeyEvent event = new KeyEvent(KeyEvent.KEY_TYPED, ".", ".", KeyCode.PERIOD, false, false, false, false);
            when(operationsHandler.processDigit("."))
                .thenReturn("0.");

            CalculationResult result = keyboardHandler.processKeyEvent(event);

            assertTrue(result.isSuccess());
            verify(operationsHandler).processDigit(".");
        }
    }

    @Nested
    @DisplayName("Operation Input Tests")
    class OperationInputTests {

        @Test
        @DisplayName("Process addition operation key")
        void processAdditionKey() {
            KeyEvent event = new KeyEvent(KeyEvent.KEY_TYPED, "+", "+", KeyCode.PLUS, false, false, false, false);
            when(operationsHandler.processOperation("+"))
                .thenReturn(CalculationResult.success(new BigDecimal("5")));

            CalculationResult result = keyboardHandler.processKeyEvent(event);

            assertTrue(result.isSuccess());
            verify(operationsHandler).processOperation("+");
        }

        @Test
        @DisplayName("Process multiplication operation key")
        void processMultiplicationKey() {
            KeyEvent event = new KeyEvent(KeyEvent.KEY_TYPED, "*", "*", KeyCode.MULTIPLY, false, false, false, false);
            when(operationsHandler.processOperation("*"))
                .thenReturn(CalculationResult.success(new BigDecimal("10")));

            CalculationResult result = keyboardHandler.processKeyEvent(event);

            assertTrue(result.isSuccess());
            verify(operationsHandler).processOperation("*");
        }
    }

    @Nested
    @DisplayName("Special Key Tests")
    class SpecialKeyTests {

        @Test
        @DisplayName("Process Enter key for equals operation")
        void processEnterKey() {
            KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ENTER, false, false, false, false);
            when(operationsHandler.processEquals())
                .thenReturn(CalculationResult.success(new BigDecimal("15")));

            CalculationResult result = keyboardHandler.processKeyEvent(event);

            assertTrue(result.isSuccess());
            verify(operationsHandler).processEquals();
        }

        @Test
        @DisplayName("Process Escape key for clear operation")
        void processEscapeKey() {
            KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.ESCAPE, false, false, false, false);
            doNothing().when(operationsHandler).clear();

            CalculationResult result = keyboardHandler.processKeyEvent(event);

            assertTrue(result.isSuccess());
            verify(operationsHandler).clear();
        }

        @Test
        @DisplayName("Process Backspace key")
        void processBackspaceKey() {
            KeyEvent event = new KeyEvent(KeyEvent.KEY_PRESSED, "", "", KeyCode.BACK_SPACE, false, false, false, false);
            when(operationsHandler.backspace()).thenReturn("12");

            CalculationResult result = keyboardHandler.processKeyEvent(event);

            assertTrue(result.isSuccess());
            verify(operationsHandler).backspace();
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Process invalid key input")
        void processInvalidKey() {
            KeyEvent event = new KeyEvent(KeyEvent.KEY_TYPED, "@", "@", KeyCode.UNDEFINED, false, false, false, false);

            CalculationResult result = keyboardHandler.processKeyEvent(event);

            assertTrue(result.isSuccess(), "Invalid input should be ignored without error");
            verify(operationsHandler, never()).processDigit(anyString());
            verify(operationsHandler, never()).processOperation(anyString());
        }
    }

    @Nested
    @DisplayName("Performance Tests")
    class PerformanceTests {

        @Test
        @DisplayName("Rapid key input processing")
        void rapidKeyInputProcessing() {
            when(operationsHandler.processDigit(anyString())).thenReturn("0");
            
            long startTime = System.currentTimeMillis();
            
            // Simulate rapid key inputs
            for (int i = 0; i < 100; i++) {
                KeyEvent event = new KeyEvent(KeyEvent.KEY_TYPED, 
                    String.valueOf(i % 10), 
                    String.valueOf(i % 10), 
                    KeyCode.DIGIT0, 
                    false, false, false, false);
                keyboardHandler.processKeyEvent(event);
            }
            
            long duration = System.currentTimeMillis() - startTime;
            assertTrue(duration < 1000, "Rapid key processing should complete within 1 second");
        }
    }
}
