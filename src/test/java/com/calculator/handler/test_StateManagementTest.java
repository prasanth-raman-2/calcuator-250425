package com.calculator.handler;

import com.calculator.model.Operation;
import com.calculator.state.CalculatorState;
import com.calculator.state.CalculatorStateManager;
import com.calculator.service.CalculationService;
import com.calculator.service.ValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("State Management Tests")
class test_StateManagementTest {

    @Mock
    private CalculationService calculationService;
    
    @Mock
    private ValidationService validationService;
    
    private CalculatorStateManager stateManager;
    private CalculatorState state;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stateManager = new CalculatorStateManager(calculationService, validationService);
        state = stateManager.getState();
    }

    @Nested
    @DisplayName("State Initialization Tests")
    class StateInitializationTests {

        @Test
        @DisplayName("Initial state should be properly set")
        void initialStateTest() {
            assertNull(state.getFirstOperand());
            assertNull(state.getSecondOperand());
            assertNull(state.getCurrentOperation());
            assertTrue(state.isNewNumber());
            assertEquals("0", state.getCurrentInput());
        }

        @Test
        @DisplayName("Clear state resets all values")
        void clearStateTest() {
            // Set some values first
            state.setFirstOperand(new BigDecimal("10"));
            state.setCurrentOperation(Operation.ADD);
            state.setCurrentInput("10");

            stateManager.clear();

            assertNull(state.getFirstOperand());
            assertNull(state.getCurrentOperation());
            assertEquals("0", state.getCurrentInput());
            assertTrue(state.isNewNumber());
        }
    }

    @Nested
    @DisplayName("Input Processing Tests")
    class InputProcessingTests {

        @Test
        @DisplayName("Process first digit input")
        void processFirstDigitTest() {
            String result = stateManager.processDigit("5");

            assertEquals("5", result);
            assertEquals("5", state.getCurrentInput());
            assertFalse(state.isNewNumber());
        }

        @Test
        @DisplayName("Process multiple digit input")
        void processMultipleDigitsTest() {
            stateManager.processDigit("1");
            stateManager.processDigit("2");
            String result = stateManager.processDigit("3");

            assertEquals("123", result);
            assertEquals("123", state.getCurrentInput());
        }

        @Test
        @DisplayName("Process decimal point")
        void processDecimalPointTest() {
            stateManager.processDigit("1");
            stateManager.processDigit(".");
            String result = stateManager.processDigit("5");

            assertEquals("1.5", result);
            assertEquals("1.5", state.getCurrentInput());
        }
    }

    @Nested
    @DisplayName("Operation Processing Tests")
    class OperationProcessingTests {

        @Test
        @DisplayName("Process first operation")
        void processFirstOperationTest() {
            state.setCurrentInput("5");
            when(validationService.validateInput(any())).thenReturn(void);

            stateManager.processOperation(Operation.ADD);

            assertEquals(new BigDecimal("5"), state.getFirstOperand());
            assertEquals(Operation.ADD, state.getCurrentOperation());
            assertTrue(state.isNewNumber());
        }

        @Test
        @DisplayName("Process chained operations")
        void processChainedOperationsTest() {
            // Setup initial state
            state.setFirstOperand(new BigDecimal("5"));
            state.setCurrentOperation(Operation.ADD);
            state.setCurrentInput("3");
            state.setNewNumber(false);

            when(calculationService.add(any(), any()))
                .thenReturn(new BigDecimal("8"));
            when(validationService.validateCalculation(any(), any(), anyString()))
                .thenReturn(void);

            stateManager.processOperation(Operation.MULTIPLY);

            assertEquals(new BigDecimal("8"), state.getFirstOperand());
            assertEquals(Operation.MULTIPLY, state.getCurrentOperation());
            assertTrue(state.isNewNumber());
        }
    }

    @Nested
    @DisplayName("History Tracking Tests")
    class HistoryTrackingTests {

        @Test
        @DisplayName("Add calculation to history")
        void addToHistoryTest() {
            state.setFirstOperand(new BigDecimal("5"));
            state.setCurrentOperation(Operation.ADD);
            state.setCurrentInput("3");

            when(calculationService.add(any(), any()))
                .thenReturn(new BigDecimal("8"));
            when(validationService.validateCalculation(any(), any(), anyString()))
                .thenReturn(void);

            stateManager.executeOperation();

            assertFalse(state.getHistory().isEmpty());
            assertEquals("5 + 3 = 8", state.getHistory().peek());
        }

        @Test
        @DisplayName("History size limit")
        void historySizeLimitTest() {
            // Add more than 100 entries
            for (int i = 0; i < 105; i++) {
                state.addToHistory("Entry " + i);
            }

            assertTrue(state.getHistory().size() <= 100);
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {

        @Test
        @DisplayName("Handle invalid number format")
        void handleInvalidNumberFormatTest() {
            state.setCurrentInput("invalid");

            var result = stateManager.executeOperation();

            assertFalse(result.isSuccess());
            assertEquals("Invalid number format", result.getError());
        }

        @Test
        @DisplayName("Handle division by zero")
        void handleDivisionByZeroTest() {
            state.setFirstOperand(new BigDecimal("10"));
            state.setCurrentOperation(Operation.DIVIDE);
            state.setCurrentInput("0");

            when(validationService.validateCalculation(any(), any(), anyString()))
                .thenThrow(new ArithmeticException("Division by zero"));

            var result = stateManager.executeOperation();

            assertFalse(result.isSuccess());
            assertEquals("Division by zero", result.getError());
        }
    }
}
