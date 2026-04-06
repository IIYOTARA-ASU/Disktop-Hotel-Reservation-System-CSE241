package com.mycompany.desktophotelreservationsystem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


    public class GUI_testing extends Application {

        @Override
        public void start(Stage primaryStage) {
            // Create a button and set its text
            Button btn = new Button();
            btn.setText("Say 'Hello World'");

            // Define an action for the button
            btn.setOnAction(event -> System.out.println("Hello World!"));
            
            btn.setStyle("--fx--background-color:Color.Red;");
            // Add the button to a layout pane
            StackPane root = new StackPane();
            root.getChildren().add(btn);

            // Create a scene with the layout pane, setting dimensions
            Scene scene = new Scene(root, 600, 400);

            // Configure the stage (the main window)
            primaryStage.setTitle("Hello HADI!");
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

