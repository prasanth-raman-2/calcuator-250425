package com.calculator.service.impl;

import com.calculator.service.ValidationService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Set;

/**
 * Implementation of the ValidationService interface.
 * Provides comprehensive validation for calculator inputs and operations.
 */
@Service
public class ValidationServiceImpl implements ValidationService {

    /**
     * Maximum number of decimal places allowed in input numbers
     */
    private static final int MAX_DECIMAL_PLACES = 10;

    /**
     * Maximum absolute value allowed for input numbers
     */
    private static final BigDecimal MAX_VALUE = new BigDecimal("1E+20");

    /**
     * Set of supported operations
     */
    private static final Set<String> SUPPORTED_OPERATIONS = Set.of("+", "-", "*", "/");

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateNumberRange(BigDecimal number) {
        if (number == null) {
            throw new IllegalArgumentException("Number cannot be null");
        }

        BigDecimal absValue = number.abs();
        if (absValue.compareTo(MAX_VALUE) > 0) {
            throw new IllegalArgumentException(
                "Number is too large. Maximum absolute value allowed is " + MAX_VALUE
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateDecimalPrecision(BigDecimal number) {
        if (number == null) {
            throw new IllegalArgumentException("Number cannot be null");
        }

        int scale = number.scale();
        if (scale > MAX_DECIMAL_PLACES) {
            throw new IllegalArgumentException(
                "Number has too many decimal places. Maximum allowed is " + MAX_DECIMAL_PLACES
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateOperation(String operation) {
        if (operation == null || operation.trim().isEmpty()) {
            throw new IllegalArgumentException("Operation cannot be null or empty");
        }

        if (!SUPPORTED_OPERATIONS.contains(operation)) {
            throw new IllegalArgumentException(
                "Unsupported operation: " + operation + ". Supported operations are: " +
                String.join(", ", SUPPORTED_OPERATIONS)
            );
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateInput(BigDecimal number) {
        validateNumberRange(number);
        validateDecimalPrecision(number);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateCalculation(BigDecimal a, BigDecimal b, String operation) {
        validateInput(a);
        validateInput(b);
        validateOperation(operation);

        if ("/".equals(operation) && BigDecimal.ZERO.compareTo(b) == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
    }
}
