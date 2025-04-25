package com.calculator.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CalculationServiceImpl Tests")
class test_CalculationServiceImpl {

    private CalculationServiceImpl calculationService;

    @BeforeEach
    void setUp() {
        calculationService = new CalculationServiceImpl();
    }

    @Nested
    @DisplayName("Addition Tests")
    class AdditionTests {
        @Test
        @DisplayName("Add two positive numbers")
        void addPositiveNumbers() {
            BigDecimal result = calculationService.add(
                new BigDecimal("5.5"), 
                new BigDecimal("3.3")
            );
            assertEquals(new BigDecimal("8.8"), result);
        }

        @Test
        @DisplayName("Add positive and negative numbers")
        void addPositiveAndNegativeNumbers() {
            BigDecimal result = calculationService.add(
                new BigDecimal("10"), 
                new BigDecimal("-5")
            );
            assertEquals(new BigDecimal("5"), result);
        }

        @Test
        @DisplayName("Add numbers with different decimal places")
        void addNumbersWithDifferentDecimalPlaces() {
            BigDecimal result = calculationService.add(
                new BigDecimal("1.5"), 
                new BigDecimal("2.25")
            );
            assertEquals(new BigDecimal("3.75"), result);
        }
    }

    @Nested
    @DisplayName("Subtraction Tests")
    class SubtractionTests {
        @Test
        @DisplayName("Subtract two positive numbers")
        void subtractPositiveNumbers() {
            BigDecimal result = calculationService.subtract(
                new BigDecimal("10.5"), 
                new BigDecimal("3.2")
            );
            assertEquals(new BigDecimal("7.3"), result);
        }

        @Test
        @DisplayName("Subtract leading to negative result")
        void subtractToNegativeResult() {
            BigDecimal result = calculationService.subtract(
                new BigDecimal("5"), 
                new BigDecimal("8")
            );
            assertEquals(new BigDecimal("-3"), result);
        }

        @Test
        @DisplayName("Subtract numbers with different decimal places")
        void subtractNumbersWithDifferentDecimalPlaces() {
            BigDecimal result = calculationService.subtract(
                new BigDecimal("5.5"), 
                new BigDecimal("2.25")
            );
            assertEquals(new BigDecimal("3.25"), result);
        }
    }

    @Nested
    @DisplayName("Multiplication Tests")
    class MultiplicationTests {
        @Test
        @DisplayName("Multiply two positive numbers")
        void multiplyPositiveNumbers() {
            BigDecimal result = calculationService.multiply(
                new BigDecimal("4.5"), 
                new BigDecimal("2")
            );
            assertEquals(new BigDecimal("9.0"), result);
        }

        @Test
        @DisplayName("Multiply positive and negative numbers")
        void multiplyPositiveAndNegativeNumbers() {
            BigDecimal result = calculationService.multiply(
                new BigDecimal("3"), 
                new BigDecimal("-4")
            );
            assertEquals(new BigDecimal("-12"), result);
        }

        @Test
        @DisplayName("Multiply by zero")
        void multiplyByZero() {
            BigDecimal result = calculationService.multiply(
                new BigDecimal("5.5"), 
                BigDecimal.ZERO
            );
            assertEquals(BigDecimal.ZERO, result);
        }
    }

    @Nested
    @DisplayName("Division Tests")
    class DivisionTests {
        @Test
        @DisplayName("Divide two positive numbers")
        void dividePositiveNumbers() {
            BigDecimal result = calculationService.divide(
                new BigDecimal("10"), 
                new BigDecimal("2")
            );
            assertEquals(new BigDecimal("5"), result);
        }

        @Test
        @DisplayName("Divide with decimal result")
        void divideWithDecimalResult() {
            BigDecimal result = calculationService.divide(
                new BigDecimal("10"), 
                new BigDecimal("3")
            );
            assertEquals(0, new BigDecimal("3.3333333333").compareTo(result));
        }

        @Test
        @DisplayName("Division by zero throws ArithmeticException")
        void divisionByZeroThrowsException() {
            assertThrows(ArithmeticException.class, () ->
                calculationService.divide(new BigDecimal("10"), BigDecimal.ZERO)
            );
        }

        @Test
        @DisplayName("Divide negative by positive number")
        void divideNegativeByPositive() {
            BigDecimal result = calculationService.divide(
                new BigDecimal("-10"), 
                new BigDecimal("2")
            );
            assertEquals(new BigDecimal("-5"), result);
        }
    }

    @Nested
    @DisplayName("Error Handling Tests")
    class ErrorHandlingTests {
        @Test
        @DisplayName("Addition with null first parameter throws exception")
        void addWithNullFirstParam() {
            assertThrows(IllegalArgumentException.class, () ->
                calculationService.add(null, new BigDecimal("5"))
            );
        }

        @Test
        @DisplayName("Addition with null second parameter throws exception")
        void addWithNullSecondParam() {
            assertThrows(IllegalArgumentException.class, () ->
                calculationService.add(new BigDecimal("5"), null)
            );
        }

        @Test
        @DisplayName("Multiplication with null parameters throws exception")
        void multiplyWithNullParams() {
            assertThrows(IllegalArgumentException.class, () ->
                calculationService.multiply(null, null)
            );
        }
    }
}
