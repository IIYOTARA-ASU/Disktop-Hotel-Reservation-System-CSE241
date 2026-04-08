package com.mycompany.desktophotelreservationsystem;
import javafx.scene.shape.Rectangle;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.*;


    public class GUI_testing extends Application {

    	public Rectangle makeRect(double width,double height,double x, double y, Color stroke, Color fill) {
    		Rectangle rectangle = new Rectangle();
    		rectangle.setWidth(width);
    		rectangle.setHeight(height);
    		rectangle.setX(x);
    		rectangle.setY(y);
    		rectangle.setStroke(stroke);
    		rectangle.setFill(fill);
    		return rectangle;
    	}
    	
    	public Rectangle makeRoundRect(double width,double height,double x, double y, Color stroke, Color fill,double arcWidth, double arcHeight) {
    		Rectangle rectangle = makeRect(width,height,x,y,stroke,fill);
    		rectangle.setArcWidth(arcWidth);
    		rectangle.setArcHeight(arcHeight);
    		return rectangle;
    	}
    	
        @Override
        public void start(Stage primaryStage) {
        	Color darkSlateGray = Color.rgb(37, 68, 65);
        	Color seaGrass = Color.rgb(67, 170, 139);
        	Color beige = Color.rgb(222, 210, 174);

            // Create a button and set its text
          //  Button btn = new Button();
          //  btn.setText("Say 'Hello World'");

            // Define an action for the button
         //   btn.setOnAction(event -> System.out.println("Hello World!"));
            
          //  btn.setStyle("--fx--background-color:Color.Red;");
            // Add the button to a layout pane
            Pane root = new Pane();
        //    root.getChildren().add(btn);

            // Create a scene with the layout pane, setting dimensions
            int x = 70;
            primaryStage.setResizable(false);
            String css = this.getClass().getResource("Style.css").toExternalForm();
            Scene scene = new Scene(root, 16*x, 9*x-2);
            
            // Configure the stage (the main window)
            primaryStage.setTitle("Hello HADI!");
            
            
           Image loginImage = new Image(getClass().getResource("Images/Login.png").toExternalForm());
           ImageView loginImageView = new ImageView(loginImage);
           
           loginImageView.setFitWidth(x*9.5);
           loginImageView.setFitHeight(x*12);
           loginImageView.setY(-x*2);
           Rectangle loginImageRect = makeRect(x*9.5,x*9,0,0,Color.rgb(10, 91, 84),Color.rgb(10, 91, 84));
           loginImageRect.setOpacity(0.67);
           Rectangle formRect = makeRoundRect(x*10,x*8,x*8,x/2,Color.ALICEBLUE,Color.ALICEBLUE,30,30);

           
           
           
            root.getChildren().addAll(
            		loginImageView,
            		//loginImageRect,
            		formRect,
            		makeRoundRect(x*2,x/7,x*7,x*1.5,seaGrass,seaGrass,10,10),
            		makeRoundRect(x*2,x/7,x*7,x*7,seaGrass,seaGrass,10,10),
            		makeRoundRect(x*3,x/7,x*6.5,x*7.5,beige,beige,10,10),
            		makeRect(x*3,x/2.1,x*9.5,0,seaGrass,seaGrass),
            		makeRect(x*3,x/8,x*13.5,x,seaGrass,seaGrass),
            		makeRect(x*3,x/8,x*14,x*1.2,beige,beige)
            		);           
            scene.getStylesheets().add(css);
            primaryStage.setScene(scene);
            primaryStage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }

