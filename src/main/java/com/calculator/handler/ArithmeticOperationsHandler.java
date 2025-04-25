package com.calculator.handler;

import com.calculator.model.CalculationResult;
import com.calculator.model.Operation;
import com.calculator.state.CalculatorStateManager;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

/**
 * PUBLIC_INTERFACE
 * Handler for processing arithmetic operations in the calculator.
 */
@Component
public class ArithmeticOperationsHandler {
    private final CalculatorStateManager stateManager;

    public ArithmeticOperationsHandler(CalculatorStateManager stateManager) {
        this.stateManager = stateManager;
    }

    /**
     * PUBLIC_INTERFACE
     * Process a digit input.
     *
     * @param digit the digit to process
     * @return the updated display value
     */
    public String processDigit(String digit) {
        return stateManager.processDigit(digit);
    }

    /**
     * PUBLIC_INTERFACE
     * Process an operation input.
     *
     * @param operationSymbol the operation symbol
     * @return the result of any intermediate calculation
     */
    public CalculationResult processOperation(String operationSymbol) {
        try {
            Operation operation = Operation.fromSymbol(operationSymbol);
            return stateManager.processOperation(operation);
        } catch (IllegalArgumentException e) {
            return CalculationResult.error("Invalid operation");
        }
    }

    /**
     * PUBLIC_INTERFACE
     * Process equals operation and get the final result.
     *
     * @return the calculation result
     */
    public CalculationResult processEquals() {
        CalculationResult result = stateManager.executeOperation();
        if (result.isSuccess()) {
            stateManager.getState().setCurrentInput(result.getDisplayValue());
            stateManager.getState().setFirstOperand(result.getValue());
            stateManager.getState().setCurrentOperation(null);
        }
        return result;
    }

    /**
     * PUBLIC_INTERFACE
     * Clear the calculator state.
     */
    public void clear() {
        stateManager.clear();
    }

    /**
     * PUBLIC_INTERFACE
     * Remove the last digit from the current input.
     *
     * @return the updated display value
     */
    public String backspace() {
        String currentInput = stateManager.getState().getCurrentInput();
        if (currentInput.length() > 1) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
        } else {
            currentInput = "0";
        }
        stateManager.getState().setCurrentInput(currentInput);
        return currentInput;
    }
}
