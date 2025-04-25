package com.calculator.handler;

import com.calculator.model.CalculationResult;
import javafx.scene.input.KeyEvent;
import org.springframework.stereotype.Component;

/**
 * PUBLIC_INTERFACE
 * Handler for processing keyboard input in the calculator.
 */
@Component
public class KeyboardInputHandler {
    private final ArithmeticOperationsHandler operationsHandler;

    public KeyboardInputHandler(ArithmeticOperationsHandler operationsHandler) {
        this.operationsHandler = operationsHandler;
    }

    /**
     * PUBLIC_INTERFACE
     * Process a keyboard event and return the appropriate result.
     *
     * @param event the keyboard event to process
     * @return the result of processing the keyboard input
     */
    public CalculationResult processKeyEvent(KeyEvent event) {
        String key = event.getText();

        if (key.isEmpty()) {
            return handleSpecialKey(event);
        }

        // Handle numeric keys and decimal point
        if (isDigitOrDecimal(key)) {
            operationsHandler.processDigit(key);
            return CalculationResult.success(null);
        }

        // Handle operation keys
        if (isOperationKey(key)) {
            return operationsHandler.processOperation(mapOperationKey(key));
        }

        return CalculationResult.error("Invalid input");
    }

    private boolean isDigitOrDecimal(String key) {
        return key.matches("[0-9.]");
    }

    private boolean isOperationKey(String key) {
        return "+-*/".contains(key);
    }

    private String mapOperationKey(String key) {
        if (key.equals("*")) return "*";
        if (key.equals("/")) return "/";
        return key;
    }

    private CalculationResult handleSpecialKey(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                return operationsHandler.processEquals();
            case BACK_SPACE:
                operationsHandler.backspace();
                return CalculationResult.success(null);
            case ESCAPE:
                operationsHandler.clear();
                return CalculationResult.success(null);
            default:
                return CalculationResult.success(null);
        }
    }
}
