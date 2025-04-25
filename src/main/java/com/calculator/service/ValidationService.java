package com.calculator.service;

import java.math.BigDecimal;

/**
 * Service interface for validating calculator inputs.
 * Provides methods for validating numbers and operations
 * to ensure they meet the calculator's requirements.
 */
public interface ValidationService {

    /**
     * PUBLIC_INTERFACE
     * Validates that a number is within the acceptable range for calculations.
     *
     * @param number the number to validate
     * @throws IllegalArgumentException if the number is outside the acceptable range
     */
    void validateNumberRange(BigDecimal number);

    /**
     * PUBLIC_INTERFACE
     * Validates the decimal places in a number to ensure it meets precision requirements.
     *
     * @param number the number to validate
     * @throws IllegalArgumentException if the number has too many decimal places
     */
    void validateDecimalPrecision(BigDecimal number);

    /**
     * PUBLIC_INTERFACE
     * Validates that the operation type is supported.
     *
     * @param operation the operation to validate (e.g., "+", "-", "*", "/")
     * @throws IllegalArgumentException if the operation is not supported
     */
    void validateOperation(String operation);

    /**
     * PUBLIC_INTERFACE
     * Validates a number for use in calculations, including range and precision checks.
     *
     * @param number the number to validate
     * @throws IllegalArgumentException if the number fails any validation check
     */
    void validateInput(BigDecimal number);

    /**
     * PUBLIC_INTERFACE
     * Validates two numbers and an operation for a calculation.
     *
     * @param a the first number
     * @param b the second number
     * @param operation the operation to perform
     * @throws IllegalArgumentException if any parameter fails validation
     */
    void validateCalculation(BigDecimal a, BigDecimal b, String operation);
}
