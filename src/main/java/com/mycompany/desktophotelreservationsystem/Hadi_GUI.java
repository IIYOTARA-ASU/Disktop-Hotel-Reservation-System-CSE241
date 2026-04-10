package com.mycompany.desktophotelreservationsystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.shape.Line;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static java.awt.Color.red;
import static javafx.application.Application.launch;
import static javafx.scene.paint.Color.*;

public class Hadi_GUI extends Application {

    public static void main(String[] args)
    {
        //skeleton
        launch(args);
    }


    @Override
    public void start (Stage hStage) throws IOException {

        // add text
        Text text = new Text();
        Text text2 = new Text();
        text2.setText("hello world");
        text2.setX(50);
        text2.setY(50);
        text2.setFont(Font.font("Verdana",22));
        text2.setFill(Color.BLACK);
        text.setText("67676767676767");
        text.setX(50);
        text.setY(50);
        text.setFont(Font.font("Verdana",22));
        text.setFill(Color.BLACK);

        //add lines
        Line line = new Line();
        line.setStartX(50);//point on pane
        line.setStartY(50);
        line.setEndX(50);
        line.setEndY(100);
        line.setStrokeWidth(2);
        line.setStroke(Color.RED);
        line.setOpacity(100);
        line.setRotate(180);

        //Create a button and set its text
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        //Define an action for the button
        btn.setOnAction(event -> System.out.println("hello world"));



        //skeleton
        hStage.setTitle("AHMED 67");
        int x=70;
        Parent hroot = FXMLLoader.load(getClass().getResource("/scenebuilder.fxml")); // add fxml file
        String css= this.getClass().getResource("/hadi.css").toExternalForm();
        Scene hscene=new Scene(hroot,550,400,Color.WHITE);
        hscene.getStylesheets().add(css);
        hStage.setScene(hscene);
        hStage.setX(700);// position on screen
        hStage.setY(200);
        hStage.show();

    }
}
