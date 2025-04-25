package com.calculator.model;

import java.math.BigDecimal;

/**
 * PUBLIC_INTERFACE
 * Class representing the result of a calculation, including any error states.
 */
public class CalculationResult {
    private final BigDecimal value;
    private final String error;
    private final boolean success;

    private CalculationResult(BigDecimal value, String error, boolean success) {
        this.value = value;
        this.error = error;
        this.success = success;
    }

    /**
     * PUBLIC_INTERFACE
     * Create a successful calculation result.
     *
     * @param value the calculated value
     * @return a successful CalculationResult
     */
    public static CalculationResult success(BigDecimal value) {
        return new CalculationResult(value, null, true);
    }

    /**
     * PUBLIC_INTERFACE
     * Create an error calculation result.
     *
     * @param error the error message
     * @return an error CalculationResult
     */
    public static CalculationResult error(String error) {
        return new CalculationResult(null, error, false);
    }

    /**
     * PUBLIC_INTERFACE
     * Get the calculated value.
     *
     * @return the value, or null if this is an error result
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * PUBLIC_INTERFACE
     * Get the error message.
     *
     * @return the error message, or null if this is a successful result
     */
    public String getError() {
        return error;
    }

    /**
     * PUBLIC_INTERFACE
     * Check if the calculation was successful.
     *
     * @return true if successful, false if an error occurred
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * PUBLIC_INTERFACE
     * Get the string representation of the result.
     *
     * @return the value as a string, or the error message if this is an error result
     */
    public String getDisplayValue() {
        return isSuccess() ? value.stripTrailingZeros().toPlainString() : error;
    }
}
