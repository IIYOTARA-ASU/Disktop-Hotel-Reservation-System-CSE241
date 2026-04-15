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
        
    	
    	User user = new User();
    	DataBase.demoFill();
        Parent guestroot = FXMLLoader.load(getClass().getResource("/guestscenebuilder.fxml"));
        Parent receproot = FXMLLoader.load(getClass().getResource("/Receptionists.fxml"));
        Parent theGoatRoot = FXMLLoader.load(getClass().getResource("/theGoat.fxml"));
        Parent adminAmenitiesRoot = FXMLLoader.load(getClass().getResource("/adminAmenities.fxml"));
        Scene guest=new Scene(guestroot);
        Scene sceneReceptionist=new Scene(receproot);
        Scene thegoat = new Scene(theGoatRoot);
        Scene adminAmenities = new Scene(adminAmenitiesRoot);
        Pane loginroot = new LoginPage(user,primaryStage,guest,sceneReceptionist);
        
        double x = 70;
        getClass().getResource("/Style.css");
        primaryStage.setResizable(false);
        String css = this.getClass().getResource("/Style.css").toExternalForm();
        String recep=this.getClass().getResource("/hadi.css").toExternalForm();
        
        Scene scene = new Scene(loginroot, 16 * x, 9 * x - 2);

        guest.getStylesheets().add(css);
        sceneReceptionist.getStylesheets().add(recep);
        scene.getStylesheets().add(css);
        // root.getChildren().add(btn);

        // Create a scene with the layout pane, setting dimensions


        // Configure the stage (the main window)
        primaryStage.setTitle("Hello HADI!");
        
        scene.getStylesheets().add(css);
        primaryStage.setScene(thegoat);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
