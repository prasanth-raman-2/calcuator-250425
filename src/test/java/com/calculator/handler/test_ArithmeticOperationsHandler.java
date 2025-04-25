package com.calculator.handler;

import com.calculator.model.CalculationResult;
import com.calculator.model.Operation;
import com.calculator.state.CalculatorState;
import com.calculator.state.CalculatorStateManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("ArithmeticOperationsHandler Tests")
class test_ArithmeticOperationsHandler {

    @Mock
    private CalculatorStateManager stateManager;

    private ArithmeticOperationsHandler handler;
    private CalculatorState calculatorState;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        handler = new ArithmeticOperationsHandler(stateManager);
        calculatorState = new CalculatorState();
        when(stateManager.getState()).thenReturn(calculatorState);
    }

    @Nested
    @DisplayName("Digit Processing Tests")
    class DigitProcessingTests {

        @Test
        @DisplayName("Process valid digit")
        void processValidDigit() {
            String digit = "5";
            when(stateManager.processDigit(digit)).thenReturn(digit);
            
            String result = handler.processDigit(digit);
            
            assertEquals(digit, result);
            verify(stateManager).processDigit(digit);
        }

        @ParameterizedTest
        @CsvSource({"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."})
        @DisplayName("Process all valid digits and decimal point")
        void processAllValidDigits(String digit) {
            when(stateManager.processDigit(digit)).thenReturn(digit);
            
            String result = handler.processDigit(digit);
            
            assertEquals(digit, result);
            verify(stateManager).processDigit(digit);
        }
    }

    @Nested
    @DisplayName("Operation Processing Tests")
    class OperationProcessingTests {

        @ParameterizedTest
        @CsvSource({"+", "-", "*", "/"})
        @DisplayName("Process valid operations")
        void processValidOperations(String operationSymbol) {
            BigDecimal expectedValue = new BigDecimal("10");
            when(stateManager.processOperation(any(Operation.class)))
                .thenReturn(CalculationResult.success(expectedValue));
            
            CalculationResult result = handler.processOperation(operationSymbol);
            
            assertTrue(result.isSuccess());
            assertEquals(expectedValue, result.getValue());
            verify(stateManager).processOperation(Operation.fromSymbol(operationSymbol));
        }

        @Test
        @DisplayName("Process invalid operation")
        void processInvalidOperation() {
            CalculationResult result = handler.processOperation("%");
            
            assertFalse(result.isSuccess());
            assertEquals("Invalid operation", result.getError());
            verify(stateManager, never()).processOperation(any(Operation.class));
        }
    }

    @Nested
    @DisplayName("Equals Operation Tests")
    class EqualsOperationTests {

        @Test
        @DisplayName("Process equals with successful calculation")
        void processEqualsSuccessful() {
            BigDecimal expectedValue = new BigDecimal("15");
            when(stateManager.executeOperation())
                .thenReturn(CalculationResult.success(expectedValue));
            
            CalculationResult result = handler.processEquals();
            
            assertTrue(result.isSuccess());
            assertEquals(expectedValue, result.getValue());
            assertEquals(expectedValue.toString(), calculatorState.getCurrentInput());
            verify(stateManager).executeOperation();
        }

        @Test
        @DisplayName("Process equals with error")
        void processEqualsError() {
            String errorMessage = "Division by zero";
            when(stateManager.executeOperation())
                .thenReturn(CalculationResult.error(errorMessage));
            
            CalculationResult result = handler.processEquals();
            
            assertFalse(result.isSuccess());
            assertEquals(errorMessage, result.getError());
            verify(stateManager).executeOperation();
        }
    }

    @Nested
    @DisplayName("Clear and Backspace Tests")
    class ClearAndBackspaceTests {

        @Test
        @DisplayName("Clear calculator state")
        void clearCalculatorState() {
            handler.clear();
            
            verify(stateManager).clear();
        }

        @Test
        @DisplayName("Backspace with multiple digits")
        void backspaceMultipleDigits() {
            calculatorState.setCurrentInput("123");
            
            String result = handler.backspace();
            
            assertEquals("12", result);
            assertEquals("12", calculatorState.getCurrentInput());
        }

        @Test
        @DisplayName("Backspace with single digit returns zero")
        void backspaceSingleDigit() {
            calculatorState.setCurrentInput("5");
            
            String result = handler.backspace();
            
            assertEquals("0", result);
            assertEquals("0", calculatorState.getCurrentInput());
        }

        @Test
        @DisplayName("Backspace with zero returns zero")
        void backspaceZero() {
            calculatorState.setCurrentInput("0");
            
            String result = handler.backspace();
            
            assertEquals("0", result);
            assertEquals("0", calculatorState.getCurrentInput());
        }
    }

    @Nested
    @DisplayName("Integration Scenario Tests")
    class IntegrationScenarioTests {

        @Test
        @DisplayName("Complete calculation scenario")
        void completeCalculationScenario() {
            // Setup initial state
            when(stateManager.processDigit("5")).thenReturn("5");
            when(stateManager.processOperation(Operation.ADD))
                .thenReturn(CalculationResult.success(new BigDecimal("5")));
            when(stateManager.processDigit("3")).thenReturn("3");
            when(stateManager.executeOperation())
                .thenReturn(CalculationResult.success(new BigDecimal("8")));

            // Perform calculation steps
            handler.processDigit("5");
            handler.processOperation("+");
            handler.processDigit("3");
            CalculationResult result = handler.processEquals();

            // Verify results
            assertTrue(result.isSuccess());
            assertEquals(new BigDecimal("8"), result.getValue());
            verify(stateManager).processDigit("5");
            verify(stateManager).processOperation(Operation.ADD);
            verify(stateManager).processDigit("3");
            verify(stateManager).executeOperation();
        }

        @Test
        @DisplayName("Error handling scenario")
        void errorHandlingScenario() {
            // Setup error scenario
            when(stateManager.processDigit("5")).thenReturn("5");
            when(stateManager.processOperation(Operation.DIVIDE))
                .thenReturn(CalculationResult.success(new BigDecimal("5")));
            when(stateManager.processDigit("0")).thenReturn("0");
            when(stateManager.executeOperation())
                .thenReturn(CalculationResult.error("Division by zero"));

            // Perform calculation steps
            handler.processDigit("5");
            handler.processOperation("/");
            handler.processDigit("0");
            CalculationResult result = handler.processEquals();

            // Verify error handling
            assertFalse(result.isSuccess());
            assertEquals("Division by zero", result.getError());
            verify(stateManager).processDigit("5");
            verify(stateManager).processOperation(Operation.DIVIDE);
            verify(stateManager).processDigit("0");
            verify(stateManager).executeOperation();
        }
    }

    @Nested
    @DisplayName("Performance Tests")
    class PerformanceTests {

        @Test
        @DisplayName("Rapid calculation sequence")
        void rapidCalculationSequence() {
            when(stateManager.processDigit(anyString())).thenReturn("1");
            when(stateManager.processOperation(any())).thenReturn(CalculationResult.success(BigDecimal.ONE));
            when(stateManager.executeOperation()).thenReturn(CalculationResult.success(BigDecimal.ONE));

            long startTime = System.currentTimeMillis();
            
            // Perform 1000 rapid calculations
            for (int i = 0; i < 1000; i++) {
                handler.processDigit("1");
                handler.processOperation("+");
                handler.processDigit("1");
                handler.processEquals();
            }

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            // Verify performance (should complete within 5 seconds)
            assertTrue(duration < 5000, "Rapid calculation sequence took too long: " + duration + "ms");
        }
    }
}
