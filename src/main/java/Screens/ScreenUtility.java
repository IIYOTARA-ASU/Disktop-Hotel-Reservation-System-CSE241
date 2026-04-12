package Screens;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ScreenUtility {
	    Color darkSlateGray = Color.rgb(37, 68, 65);
	    Color seaGrass = Color.rgb(67, 170, 139);
	    Color beige = Color.rgb(222, 210, 174);
    
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
	    	textField.getStyleClass().add("textBox");
	    	return textField;
	    }
}
