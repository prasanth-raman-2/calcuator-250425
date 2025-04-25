package com.calculator.state;

import com.calculator.model.Operation;
import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * PUBLIC_INTERFACE
 * Class representing the current state of the calculator,
 * including input numbers, operation, and history.
 */
public class CalculatorState {
    private BigDecimal firstOperand;
    private BigDecimal secondOperand;
    private Operation currentOperation;
    private boolean newNumber;
    private String currentInput;
    private final Deque<String> history;

    public CalculatorState() {
        this.currentInput = "0";
        this.newNumber = true;
        this.history = new ArrayDeque<>();
    }

    /**
     * PUBLIC_INTERFACE
     * Get the first operand.
     *
     * @return the first operand
     */
    public BigDecimal getFirstOperand() {
        return firstOperand;
    }

    /**
     * PUBLIC_INTERFACE
     * Set the first operand.
     *
     * @param firstOperand the first operand
     */
    public void setFirstOperand(BigDecimal firstOperand) {
        this.firstOperand = firstOperand;
    }

    /**
     * PUBLIC_INTERFACE
     * Get the second operand.
     *
     * @return the second operand
     */
    public BigDecimal getSecondOperand() {
        return secondOperand;
    }

    /**
     * PUBLIC_INTERFACE
     * Set the second operand.
     *
     * @param secondOperand the second operand
     */
    public void setSecondOperand(BigDecimal secondOperand) {
        this.secondOperand = secondOperand;
    }

    /**
     * PUBLIC_INTERFACE
     * Get the current operation.
     *
     * @return the current operation
     */
    public Operation getCurrentOperation() {
        return currentOperation;
    }

    /**
     * PUBLIC_INTERFACE
     * Set the current operation.
     *
     * @param currentOperation the current operation
     */
    public void setCurrentOperation(Operation currentOperation) {
        this.currentOperation = currentOperation;
    }

    /**
     * PUBLIC_INTERFACE
     * Check if starting a new number input.
     *
     * @return true if starting a new number, false otherwise
     */
    public boolean isNewNumber() {
        return newNumber;
    }

    /**
     * PUBLIC_INTERFACE
     * Set whether starting a new number input.
     *
     * @param newNumber true if starting a new number, false otherwise
     */
    public void setNewNumber(boolean newNumber) {
        this.newNumber = newNumber;
    }

    /**
     * PUBLIC_INTERFACE
     * Get the current input string.
     *
     * @return the current input
     */
    public String getCurrentInput() {
        return currentInput;
    }

    /**
     * PUBLIC_INTERFACE
     * Set the current input string.
     *
     * @param currentInput the current input
     */
    public void setCurrentInput(String currentInput) {
        this.currentInput = currentInput;
    }

    /**
     * PUBLIC_INTERFACE
     * Add an entry to the calculation history.
     *
     * @param entry the history entry to add
     */
    public void addToHistory(String entry) {
        history.push(entry);
        if (history.size() > 100) { // Limit history size
            history.removeLast();
        }
    }

    /**
     * PUBLIC_INTERFACE
     * Get the calculation history.
     *
     * @return the calculation history
     */
    public Deque<String> getHistory() {
        return new ArrayDeque<>(history);
    }

    /**
     * PUBLIC_INTERFACE
     * Clear the current calculation state.
     */
    public void clear() {
        this.firstOperand = null;
        this.secondOperand = null;
        this.currentOperation = null;
        this.currentInput = "0";
        this.newNumber = true;
    }
}
