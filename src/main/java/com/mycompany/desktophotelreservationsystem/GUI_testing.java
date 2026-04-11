package com.mycompany.desktophotelreservationsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;

import Screens.LoginPage;

public class GUI_testing extends Application {
	
    @Override
    public void start(Stage primaryStage) throws IOException {
        Color darkSlateGray = Color.rgb(37, 68, 65);
        Color seaGrass = Color.rgb(67, 170, 139);
        Color beige = Color.rgb(222, 210, 174);

        // Create a button and set its text
        // Button btn = new Button();
        // btn.setText("Say 'Hello World'");

        // Define an action for the button
        // btn.setOnAction(event -> System.out.println("Hello World!"));

        // btn.setStyle("--fx--background-color:Color.Red;");
        // Add the button to a layout pane
        Pane root = new LoginPage();
        Parent gusetroot = FXMLLoader.load(getClass().getResource("/scenebuilder.fxml"));
        // root.getChildren().add(btn);

        // Create a scene with the layout pane, setting dimensions
        double x = 70;
        getClass().getResource("/Style.css");
        primaryStage.setResizable(false);
        String css = this.getClass().getResource("/Style.css").toExternalForm();
        Scene scene = new Scene(root, 16 * x, 9 * x - 2);
        Scene guest=new Scene(gusetroot);
        scene.getStylesheets().add(css);

        // Configure the stage (the main window)
        primaryStage.setTitle("Hello HADI!");

        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
