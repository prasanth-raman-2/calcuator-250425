package com.calculator.service.impl;

import com.calculator.service.CalculationService;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Implementation of the CalculationService interface.
 * Provides precise arithmetic calculations with proper error handling.
 */
@Service
public class CalculationServiceImpl implements CalculationService {

    /**
     * The scale to use for decimal operations
     */
    private static final int DECIMAL_SCALE = 10;

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal add(BigDecimal a, BigDecimal b) {
        validateInputs(a, b);
        return a.add(b).stripTrailingZeros();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal subtract(BigDecimal a, BigDecimal b) {
        validateInputs(a, b);
        return a.subtract(b).stripTrailingZeros();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal multiply(BigDecimal a, BigDecimal b) {
        validateInputs(a, b);
        return a.multiply(b).stripTrailingZeros();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal divide(BigDecimal a, BigDecimal b) {
        validateInputs(a, b);
        if (BigDecimal.ZERO.compareTo(b) == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
        return a.divide(b, DECIMAL_SCALE, RoundingMode.HALF_UP).stripTrailingZeros();
    }

    /**
     * Validates that the input parameters are not null.
     *
     * @param a the first number
     * @param b the second number
     * @throws IllegalArgumentException if either parameter is null
     */
    private void validateInputs(BigDecimal a, BigDecimal b) {
        Objects.requireNonNull(a, "First operand cannot be null");
        Objects.requireNonNull(b, "Second operand cannot be null");
    }
}
