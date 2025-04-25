package com.calculator.state;

import com.calculator.model.CalculationResult;
import com.calculator.model.Operation;
import com.calculator.service.CalculationService;
import com.calculator.service.ValidationService;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

/**
 * PUBLIC_INTERFACE
 * Component responsible for managing calculator state and executing operations.
 */
@Component
public class CalculatorStateManager {
    private final CalculatorState state;
    private final CalculationService calculationService;
    private final ValidationService validationService;

    public CalculatorStateManager(CalculationService calculationService, ValidationService validationService) {
        this.state = new CalculatorState();
        this.calculationService = calculationService;
        this.validationService = validationService;
    }

    /**
     * PUBLIC_INTERFACE
     * Process a digit input.
     *
     * @param digit the digit to process
     * @return the updated display value
     */
    public String processDigit(String digit) {
        if (state.isNewNumber()) {
            state.setCurrentInput(digit);
            state.setNewNumber(false);
        } else {
            if (state.getCurrentInput().equals("0") && !digit.equals(".")) {
                state.setCurrentInput(digit);
            } else {
                state.setCurrentInput(state.getCurrentInput() + digit);
            }
        }
        return state.getCurrentInput();
    }

    /**
     * PUBLIC_INTERFACE
     * Process an operation input.
     *
     * @param operation the operation to process
     * @return the result of any intermediate calculation
     */
    public CalculationResult processOperation(Operation operation) {
        try {
            if (state.getFirstOperand() == null) {
                state.setFirstOperand(new BigDecimal(state.getCurrentInput()));
            } else if (!state.isNewNumber()) {
                CalculationResult result = executeOperation();
                if (!result.isSuccess()) {
                    return result;
                }
                state.setFirstOperand(result.getValue());
            }
            state.setCurrentOperation(operation);
            state.setNewNumber(true);
            return CalculationResult.success(state.getFirstOperand());
        } catch (NumberFormatException e) {
            return CalculationResult.error("Invalid number format");
        }
    }

    /**
     * PUBLIC_INTERFACE
     * Execute the current operation.
     *
     * @return the result of the calculation
     */
    public CalculationResult executeOperation() {
        try {
            if (state.getCurrentOperation() == null || state.getFirstOperand() == null) {
                BigDecimal value = new BigDecimal(state.getCurrentInput());
                validationService.validateInput(value);
                return CalculationResult.success(value);
            }

            BigDecimal secondOperand = new BigDecimal(state.getCurrentInput());
            validationService.validateCalculation(
                state.getFirstOperand(),
                secondOperand,
                state.getCurrentOperation().getSymbol()
            );

            BigDecimal result;
            switch (state.getCurrentOperation()) {
                case ADD:
                    result = calculationService.add(state.getFirstOperand(), secondOperand);
                    break;
                case SUBTRACT:
                    result = calculationService.subtract(state.getFirstOperand(), secondOperand);
                    break;
                case MULTIPLY:
                    result = calculationService.multiply(state.getFirstOperand(), secondOperand);
                    break;
                case DIVIDE:
                    result = calculationService.divide(state.getFirstOperand(), secondOperand);
                    break;
                default:
                    return CalculationResult.error("Unsupported operation");
            }

            String historyEntry = String.format("%s %s %s = %s",
                state.getFirstOperand(),
                state.getCurrentOperation().getSymbol(),
                secondOperand,
                result
            );
            state.addToHistory(historyEntry);
            state.setNewNumber(true);
            return CalculationResult.success(result);

        } catch (NumberFormatException e) {
            return CalculationResult.error("Invalid number format");
        } catch (ArithmeticException e) {
            return CalculationResult.error("Division by zero");
        } catch (IllegalArgumentException e) {
            return CalculationResult.error(e.getMessage());
        }
    }

    /**
     * PUBLIC_INTERFACE
     * Clear the calculator state.
     */
    public void clear() {
        state.clear();
    }

    /**
     * PUBLIC_INTERFACE
     * Get the current calculator state.
     *
     * @return the current state
     */
    public CalculatorState getState() {
        return state;
    }
}
