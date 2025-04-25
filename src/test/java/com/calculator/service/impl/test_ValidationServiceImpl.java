package com.calculator.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ValidationServiceImpl Tests")
class test_ValidationServiceImpl {

    private ValidationServiceImpl validationService;

    @BeforeEach
    void setUp() {
        validationService = new ValidationServiceImpl();
    }

    @Nested
    @DisplayName("Number Range Validation Tests")
    class NumberRangeValidationTests {
        @Test
        @DisplayName("Valid number within range")
        void validNumberWithinRange() {
            BigDecimal validNumber = new BigDecimal("1000.50");
            assertDoesNotThrow(() -> validationService.validateNumberRange(validNumber));
        }

        @Test
        @DisplayName("Number exceeding maximum value throws exception")
        void numberExceedingMaxValue() {
            BigDecimal tooLarge = new BigDecimal("1E+21");
            assertThrows(IllegalArgumentException.class, () ->
                validationService.validateNumberRange(tooLarge)
            );
        }

        @Test
        @DisplayName("Null number throws exception")
        void nullNumberThrowsException() {
            assertThrows(IllegalArgumentException.class, () ->
                validationService.validateNumberRange(null)
            );
        }

        @Test
        @DisplayName("Negative number within range")
        void negativeNumberWithinRange() {
            BigDecimal negativeNumber = new BigDecimal("-1000.50");
            assertDoesNotThrow(() -> validationService.validateNumberRange(negativeNumber));
        }
    }

    @Nested
    @DisplayName("Decimal Precision Validation Tests")
    class DecimalPrecisionValidationTests {
        @Test
        @DisplayName("Number with acceptable decimal places")
        void acceptableDecimalPlaces() {
            BigDecimal number = new BigDecimal("123.4567890");
            assertDoesNotThrow(() -> validationService.validateDecimalPrecision(number));
        }

        @Test
        @DisplayName("Number with too many decimal places throws exception")
        void tooManyDecimalPlaces() {
            BigDecimal number = new BigDecimal("123.45678901234");
            assertThrows(IllegalArgumentException.class, () ->
                validationService.validateDecimalPrecision(number)
            );
        }

        @Test
        @DisplayName("Integer number (no decimals)")
        void integerNumber() {
            BigDecimal number = new BigDecimal("12345");
            assertDoesNotThrow(() -> validationService.validateDecimalPrecision(number));
        }
    }

    @Nested
    @DisplayName("Operation Validation Tests")
    class OperationValidationTests {
        @Test
        @DisplayName("Valid operations")
        void validOperations() {
            String[] validOps = {"+", "-", "*", "/"};
            for (String op : validOps) {
                assertDoesNotThrow(() -> validationService.validateOperation(op));
            }
        }

        @Test
        @DisplayName("Invalid operation throws exception")
        void invalidOperation() {
            assertThrows(IllegalArgumentException.class, () ->
                validationService.validateOperation("%")
            );
        }

        @Test
        @DisplayName("Null operation throws exception")
        void nullOperation() {
            assertThrows(IllegalArgumentException.class, () ->
                validationService.validateOperation(null)
            );
        }

        @Test
        @DisplayName("Empty operation throws exception")
        void emptyOperation() {
            assertThrows(IllegalArgumentException.class, () ->
                validationService.validateOperation("")
            );
        }
    }

    @Nested
    @DisplayName("Input Validation Tests")
    class InputValidationTests {
        @Test
        @DisplayName("Valid input number passes all checks")
        void validInputNumber() {
            BigDecimal number = new BigDecimal("123.456");
            assertDoesNotThrow(() -> validationService.validateInput(number));
        }

        @Test
        @DisplayName("Null input throws exception")
        void nullInput() {
            assertThrows(IllegalArgumentException.class, () ->
                validationService.validateInput(null)
            );
        }
    }

    @Nested
    @DisplayName("Calculation Validation Tests")
    class CalculationValidationTests {
        @Test
        @DisplayName("Valid calculation parameters")
        void validCalculationParameters() {
            BigDecimal a = new BigDecimal("123.45");
            BigDecimal b = new BigDecimal("67.89");
            String operation = "+";
            assertDoesNotThrow(() -> validationService.validateCalculation(a, b, operation));
        }

        @Test
        @DisplayName("Division by zero check")
        void divisionByZeroCheck() {
            BigDecimal a = new BigDecimal("123.45");
            BigDecimal b = BigDecimal.ZERO;
            String operation = "/";
            assertThrows(ArithmeticException.class, () ->
                validationService.validateCalculation(a, b, operation)
            );
        }

        @Test
        @DisplayName("Invalid operation throws exception")
        void invalidOperationInCalculation() {
            BigDecimal a = new BigDecimal("123.45");
            BigDecimal b = new BigDecimal("67.89");
            String operation = "%";
            assertThrows(IllegalArgumentException.class, () ->
                validationService.validateCalculation(a, b, operation)
            );
        }

        @Test
        @DisplayName("Numbers outside range throws exception")
        void numbersOutsideRange() {
            BigDecimal a = new BigDecimal("1E+21");
            BigDecimal b = new BigDecimal("67.89");
            String operation = "+";
            assertThrows(IllegalArgumentException.class, () ->
                validationService.validateCalculation(a, b, operation)
            );
        }
    }
}
