package com.calculator.model;

/**
 * PUBLIC_INTERFACE
 * Enum representing the basic arithmetic operations supported by the calculator.
 */
public enum Operation {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    /**
     * PUBLIC_INTERFACE
     * Get the symbol representing this operation.
     *
     * @return the operation symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * PUBLIC_INTERFACE
     * Get an Operation from its symbol.
     *
     * @param symbol the operation symbol
     * @return the corresponding Operation
     * @throws IllegalArgumentException if symbol doesn't match any operation
     */
    public static Operation fromSymbol(String symbol) {
        for (Operation op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }
        throw new IllegalArgumentException("Unsupported operation symbol: " + symbol);
    }
}
