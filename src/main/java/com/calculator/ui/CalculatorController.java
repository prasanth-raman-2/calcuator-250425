package com.calculator.ui;

import com.calculator.handler.ArithmeticOperationsHandler;
import com.calculator.handler.ErrorStateHandler;
import com.calculator.handler.KeyboardInputHandler;
import com.calculator.model.CalculationResult;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import org.springframework.stereotype.Controller;

/**
 * PUBLIC_INTERFACE
 * Controller for the calculator UI, handling user interactions and display updates.
 */
@Controller
public class CalculatorController {
    @FXML
    private TextField display;

    private final ArithmeticOperationsHandler operationsHandler;
    private final KeyboardInputHandler keyboardHandler;
    private final ErrorStateHandler errorHandler;

    public CalculatorController(
        ArithmeticOperationsHandler operationsHandler,
        KeyboardInputHandler keyboardHandler,
        ErrorStateHandler errorHandler
    ) {
        this.operationsHandler = operationsHandler;
        this.keyboardHandler = keyboardHandler;
        this.errorHandler = errorHandler;
    }

    @FXML
    public void initialize() {
        display.setText("0");
        setupKeyboardHandling();
    }

    private void setupKeyboardHandling() {
        display.getParent().setOnKeyTyped(this::handleKeyInput);
    }

    @FXML
    public void handleDigit() {
        Button button = (Button) display.getScene().getFocusOwner();
        String digit = button.getText();
        updateDisplay(operationsHandler.processDigit(digit));
    }

    @FXML
    public void handleOperation() {
        Button button = (Button) display.getScene().getFocusOwner();
        String operation = button.getText();
        if (operation.equals("×")) operation = "*";
        if (operation.equals("÷")) operation = "/";
        
        processOperation(operation);
    }

    @FXML
    public void handleEquals() {
        processEquals();
    }

    @FXML
    public void handleClear() {
        operationsHandler.clear();
        updateDisplay("0");
    }

    @FXML
    public void handleBackspace() {
        updateDisplay(operationsHandler.backspace());
    }

    private void handleKeyInput(KeyEvent event) {
        CalculationResult result = keyboardHandler.processKeyEvent(event);
        if (result.isSuccess() && result.getValue() != null) {
            updateDisplay(result.getDisplayValue());
        } else if (!result.isSuccess()) {
            errorHandler.showError(display, result.getError());
        }
    }

    private void processOperation(String operation) {
        CalculationResult result = operationsHandler.processOperation(operation);
        if (!result.isSuccess()) {
            errorHandler.showError(display, result.getError());
        } else if (result.getValue() != null) {
            updateDisplay(result.getDisplayValue());
        }
    }

    private void processEquals() {
        CalculationResult result = operationsHandler.processEquals();
        if (result.isSuccess()) {
            updateDisplay(result.getDisplayValue());
        } else {
            errorHandler.showError(display, result.getError());
        }
    }

    private void updateDisplay(String value) {
        if (!errorHandler.isInErrorState(display)) {
            display.setText(value);
        }
    }
}
