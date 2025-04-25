package com.calculator.service;

import java.math.BigDecimal;

/**
 * Service interface for handling basic arithmetic calculations.
 * Provides methods for addition, subtraction, multiplication, and division
 * with support for both integer and decimal operations.
 */
public interface CalculationService {

    /**
     * PUBLIC_INTERFACE
     * Adds two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the sum of the two numbers
     */
    BigDecimal add(BigDecimal a, BigDecimal b);

    /**
     * PUBLIC_INTERFACE
     * Subtracts the second number from the first number.
     *
     * @param a the number to subtract from
     * @param b the number to subtract
     * @return the difference between the two numbers
     */
    BigDecimal subtract(BigDecimal a, BigDecimal b);

    /**
     * PUBLIC_INTERFACE
     * Multiplies two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the product of the two numbers
     */
    BigDecimal multiply(BigDecimal a, BigDecimal b);

    /**
     * PUBLIC_INTERFACE
     * Divides the first number by the second number.
     *
     * @param a the dividend
     * @param b the divisor
     * @return the quotient of the division
     * @throws ArithmeticException if the divisor is zero
     */
    BigDecimal divide(BigDecimal a, BigDecimal b);
}
