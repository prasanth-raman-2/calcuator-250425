# Calculator Application

A modern, feature-rich calculator application built with Java and JavaFX, providing a responsive and accessible user interface with support for basic arithmetic operations.

## Features

- Basic Arithmetic Operations:
  - Addition
  - Subtraction
  - Multiplication
  - Division
- Decimal Number Support
- Error Handling:
  - Division by zero protection
  - Invalid input validation
  - Clear error state display
- Keyboard Navigation:
  - Full keyboard support for all operations
  - Number pad compatibility
  - Keyboard shortcuts
- Accessibility Features:
  - High contrast UI elements
  - Clear focus indicators
  - Proper touch target sizes
  - WCAG 2.1 compliant
- Responsive Design:
  - Consistent layout across different screen sizes
  - Proper button spacing
  - Visual feedback for all actions
- Calculation History

## Requirements

- Java Development Kit (JDK) 17 or higher
- Maven 3.6 or higher
- Operating Systems:
  - Windows 10/11
  - macOS 10.15+
  - Linux (with GUI support)

## Setting Up the Development Environment

1. Install Java 17:
   ```bash
   # On Ubuntu/Debian
   sudo apt-get update
   sudo apt-get install openjdk-17-jdk

   # On macOS with Homebrew
   brew install openjdk@17

   # Verify installation
   java -version
   ```

2. Install Maven:
   ```bash
   # On Ubuntu/Debian
   sudo apt-get install maven

   # On macOS with Homebrew
   brew install maven

   # Verify installation
   mvn -version
   ```

3. Clone the repository:
   ```bash
   git clone <repository-url>
   cd calculator-app
   ```

## Building the Application

1. Clean and build the project:
   ```bash
   mvn clean install
   ```

2. Run the tests:
   ```bash
   mvn test
   ```

3. Create an executable JAR:
   ```bash
   mvn package
   ```

## Running the Application

### Using Maven

```bash
mvn javafx:run
```

### Using the JAR file

```bash
java -jar target/calculator-app-1.0.0-SNAPSHOT.jar
```

## Usage Guide

### Basic Operations

1. Number Input:
   - Click number buttons (0-9) or use keyboard
   - Use decimal point (.) for decimal numbers
   - Keyboard number pad is supported

2. Arithmetic Operations:
   - Addition: Click '+' or press '+' key
   - Subtraction: Click '-' or press '-' key
   - Multiplication: Click '×' or press '*' key
   - Division: Click '÷' or press '/' key

3. Other Operations:
   - Clear: Click 'C' button or press 'ESC' key
   - Backspace: Click '⌫' button or press 'Backspace' key
   - Calculate: Click '=' button or press 'Enter' key

### Keyboard Shortcuts

- Numbers: 0-9 keys or number pad
- Operations: +, -, *, /
- Decimal Point: . (period)
- Clear: ESC
- Backspace: Backspace key
- Calculate: Enter

## Development Guide

### Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── calculator/
│   │           ├── CalculatorApplication.java
│   │           ├── handler/
│   │           ├── model/
│   │           ├── service/
│   │           ├── state/
│   │           └── ui/
│   └── resources/
│       ├── css/
│       │   └── calculator.css
│       └── fxml/
│           └── CalculatorView.fxml
└── test/
    └── java/
        └── com/
            └── calculator/
                └── handler/
```

### Key Components

- `CalculatorApplication`: Main application class
- `ArithmeticOperationsHandler`: Handles arithmetic operations
- `CalculatorController`: UI controller for JavaFX
- `CalculationService`: Core calculation logic
- `ValidationService`: Input validation
- `CalculatorState`: State management
- `ErrorStateHandler`: Error handling and display

### Building for Production

1. Set production profile:
   ```bash
   mvn clean package -P production
   ```

2. Create a native installer (requires JPackage):
   ```bash
   mvn javafx:jlink jpackage:jpackage
   ```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Run tests: `mvn test`
5. Submit a pull request

## Testing

### Running Tests

- All tests:
  ```bash
  mvn test
  ```

- Specific test:
  ```bash
  mvn test -Dtest=test_CalculationServiceImpl
  ```

### Test Coverage

Generate coverage report:
```bash
mvn jacoco:report
```

View the report in `target/site/jacoco/index.html`

## Troubleshooting

### Common Issues

1. JavaFX not found:
   ```bash
   # Add JavaFX modules explicitly
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -jar calculator-app.jar
   ```

2. Maven build fails:
   - Ensure JAVA_HOME is set correctly
   - Run `mvn clean` and try again
   - Check for version conflicts in pom.xml

### Error Messages

- "Division by zero": Attempted to divide by zero
- "Invalid number format": Input number is not in valid format
- "Number too large": Input exceeds maximum allowed value

## License

This project is licensed under the MIT License - see the LICENSE file for details.
