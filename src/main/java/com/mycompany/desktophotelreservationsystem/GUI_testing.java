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

public class GUI_testing extends Application {

    public Rectangle makeRect(double width, double height, double x, double y, Color stroke, Color fill) {
        Rectangle rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setStroke(stroke);
        rectangle.setFill(fill);
        return rectangle;
    }

    public Rectangle makeRoundRect(double width, double height, double x, double y, Color stroke, Color fill,
            double arcWidth, double arcHeight) {
        Rectangle rectangle = makeRect(width, height, x, y, stroke, fill);
        rectangle.setArcWidth(arcWidth);
        rectangle.setArcHeight(arcHeight);
        return rectangle;
    }
    
    public Text makeText(String textString, String fontFamily, int fontSize, FontWeight fontWeight, Color color, double x, double y) {
    	Text text = new Text(textString);
    	Font font = new Font(fontSize);
    	text.setFont(Font.font(fontFamily,fontWeight,fontSize));
       	text.setFill(color);
    	text.setX(x);
    	text.setY(y);
		return text;
    }
    
    public TextField makeTextField(String promptText,Double width, Double height,Double x,Double y,boolean password) {
    	TextField textField;
    	if(password) {
    		textField = new PasswordField();
    	}else {
    		textField = new TextField();
    	}
    	textField.setPromptText(promptText);
    	textField.setPrefWidth(width);
    	textField.setPrefHeight(height);
    	textField.relocate(x,y);
    	return textField;
    }
    
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
        Pane root = new Pane();
        Parent guestroot = FXMLLoader.load(getClass().getResource("/guestscenebuilder.fxml"));
        Parent receproot = FXMLLoader.load(getClass().getResource("/Receptionists.fxml"));
        // root.getChildren().add(btn);

        // Create a scene with the layout pane, setting dimensions
        double x = 70;
        getClass().getResource("/Style.css");
        primaryStage.setResizable(false);
        String css = this.getClass().getResource("/Style.css").toExternalForm();
        String recep=this.getClass().getResource("/hadi.css").toExternalForm();
        Scene scene = new Scene(root, 16 * x, 9 * x - 2);
        Scene guest=new Scene(guestroot);
        Scene Receptionist=new Scene(receproot);
        guest.getStylesheets().add(css);
        Receptionist.getStylesheets().add(recep);
        scene.getStylesheets().add(css);

        // Configure the stage (the main window)
        primaryStage.setTitle("Hello HADI!");

        Image loginImage = new Image(this.getClass().getResource("/Images/Login.png").toExternalForm());
        ImageView loginImageView = new ImageView(loginImage);

        loginImageView.setFitWidth(x * 9.5);
        loginImageView.setFitHeight(x * 12);
        loginImageView.setY(-x * 2);
        Rectangle loginImageRect = makeRect(x * 9.5, x * 9, 0, 0, Color.rgb(10, 91, 84), Color.rgb(10, 91, 84));
        loginImageRect.setOpacity(0.67);
        Rectangle formRect = makeRoundRect(x * 10, x * 8, x * 8, x / 2, Color.ALICEBLUE, Color.ALICEBLUE, 30, 30);
        TextField username = makeTextField("",x*5.5,x*0.6,x*9.5,x*3.5,false);
        username.getStyleClass().add("textBox");
        TextField password = makeTextField("",x*5.5,x*0.6,x*9.5,x*4.7,true);
        password.getStyleClass().add("textBox");
        
        Button loginButton = new Button("LOGIN");
        loginButton.setLayoutX(x*9.5);
        loginButton.setLayoutY(x*5.7);
        loginButton.setPrefWidth(x*5.5);
        loginButton.setPrefHeight(x*0.7);
        loginButton.getStyleClass().add("btn-round");
        Hyperlink registerHyperlink = new Hyperlink("Create New Account");
        registerHyperlink.setLayoutX(x*10.8);
        registerHyperlink.setLayoutY(x*6.7);
        registerHyperlink.getStyleClass().add("hyperlink");
        
        Hyperlink loginHyperlink = new Hyperlink("I have an existing account");
        loginHyperlink.setLayoutX(x*10.5);
        loginHyperlink.setLayoutY(x*6.7);
        loginHyperlink.getStyleClass().add("hyperlink");
        
        Button registerButton = new Button("REGISTER");
        registerButton.setLayoutX(x*9.5);
        registerButton.setLayoutY(x*5.7);
        registerButton.setPrefWidth(x*5.5);
        registerButton.setPrefHeight(x*0.7);
        registerButton.getStyleClass().add("btn-round");

        registerHyperlink.setOnAction(e -> {
        	root.getChildren().setAll(
                    loginImageView,
                    formRect,
                    makeRoundRect(x * 2, x / 7, x * 7, x * 1.5, seaGrass, seaGrass, 10, 10),
                    makeRoundRect(x * 2, x / 7, x * 7, x * 7, seaGrass, seaGrass, 10, 10),
                    makeRoundRect(x * 3, x / 7, x * 6.5, x * 7.5, beige, beige, 10, 10),
                    makeRect(x * 3, x / 2.1, x * 9.5, 0, seaGrass, seaGrass),
                    makeRect(x * 3, x / 8, x * 13.5, x, seaGrass, seaGrass),
                    makeRect(x * 3, x / 8, x * 14, x * 1.2, beige, beige),
                    makeText("REGISTER","Verdana",48,FontWeight.BOLD,darkSlateGray,x*10.3,x*2),
                    makeText("Username","Verdana",16,FontWeight.MEDIUM,darkSlateGray,x*9.5,x*3.4),
                    makeText("Password","Verdana",16,FontWeight.MEDIUM,darkSlateGray,x*9.5,x*4.6),
            		username,
            		password,
            		registerButton,
            		loginHyperlink
            		);
        });	
        
        
        loginHyperlink.setOnAction(e -> {
        	root.getChildren().setAll(
        	       
        	                loginImageView,
        	                formRect,
        	                makeRoundRect(x * 2, x / 7, x * 7, x * 1.5, seaGrass, seaGrass, 10, 10),
        	                makeRoundRect(x * 2, x / 7, x * 7, x * 7, seaGrass, seaGrass, 10, 10),
        	                makeRoundRect(x * 3, x / 7, x * 6.5, x * 7.5, beige, beige, 10, 10),
        	                makeRect(x * 3, x / 2.1, x * 9.5, 0, seaGrass, seaGrass),
        	                makeRect(x * 3, x / 8, x * 13.5, x, seaGrass, seaGrass),
        	                makeRect(x * 3, x / 8, x * 14, x * 1.2, beige, beige),
        	                makeText("WELCOME","Verdana",48,FontWeight.BOLD,darkSlateGray,x*10.3,x*2),
        	                makeText("Username","Verdana",16,FontWeight.MEDIUM,darkSlateGray,x*9.5,x*3.4),
        	                makeText("Password","Verdana",16,FontWeight.MEDIUM,darkSlateGray,x*9.5,x*4.6),
        	        		username,
        	        		password,
        	        		loginButton,
        	        		registerHyperlink
        	        		);
        	        		
        });	
        
        
        root.getChildren().setAll(
                loginImageView,
                formRect,
                makeRoundRect(x * 2, x / 7, x * 7, x * 1.5, seaGrass, seaGrass, 10, 10),
                makeRoundRect(x * 2, x / 7, x * 7, x * 7, seaGrass, seaGrass, 10, 10),
                makeRoundRect(x * 3, x / 7, x * 6.5, x * 7.5, beige, beige, 10, 10),
                makeRect(x * 3, x / 2.1, x * 9.5, 0, seaGrass, seaGrass),
                makeRect(x * 3, x / 8, x * 13.5, x, seaGrass, seaGrass),
                makeRect(x * 3, x / 8, x * 14, x * 1.2, beige, beige),
                makeText("WELCOME","Verdana",48,FontWeight.BOLD,darkSlateGray,x*10.3,x*2),
                makeText("Username","Verdana",16,FontWeight.MEDIUM,darkSlateGray,x*9.5,x*3.4),
                makeText("Password","Verdana",16,FontWeight.MEDIUM,darkSlateGray,x*9.5,x*4.6),
        		username,
        		password,
        		loginButton,
        		registerHyperlink
        		);
        
        scene.getStylesheets().add(css);
        primaryStage.setScene(Receptionist);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
