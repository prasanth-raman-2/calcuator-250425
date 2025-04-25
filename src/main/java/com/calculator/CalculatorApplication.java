package com.calculator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Main application class that combines Spring Boot and JavaFX functionality.
 * This class initializes both the Spring context and JavaFX UI.
 */
@SpringBootApplication
public class CalculatorApplication extends Application {

    private ConfigurableApplicationContext springContext;

    /**
     * PUBLIC_INTERFACE
     * Main entry point for the application.
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initialize Spring context before JavaFX starts.
     */
    @Override
    public void init() {
        springContext = SpringApplication.run(CalculatorApplication.class);
    }

    /**
     * Start method called by the JavaFX runtime.
     * Sets up the primary stage and initializes the UI.
     * @param primaryStage the primary stage for the application
     */
    @Override
    public void start(Stage primaryStage) {
        // Load the FXML scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/CalculatorView.fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        
        // Load CSS styles
        scene.getStylesheets().add(getClass().getResource("/css/calculator.css").toExternalForm());
        
        // Set up the stage
        primaryStage.setTitle("Calculator");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Stop method called by the JavaFX runtime when the application is shutting down.
     * Ensures proper cleanup of Spring context.
     */
    @Override
    public void stop() {
        springContext.close();
        Platform.exit();
    }
}
