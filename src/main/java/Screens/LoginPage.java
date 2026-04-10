package Screens;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class LoginPage extends Pane{
	public LoginPage() {
	ScreenUtility screenUtil = new ScreenUtility();
	    
    double x = 70;

	Image loginImage = new Image(this.getClass().getResource("/Images/Login.png").toExternalForm());
    ImageView loginImageView = new ImageView(loginImage);

    loginImageView.setFitWidth(x * 9.5);
    loginImageView.setFitHeight(x * 12);
    loginImageView.setY(-x * 2);
    Rectangle formRect = screenUtil.makeRoundRect(x * 10, x * 8, x * 8, x / 2, Color.ALICEBLUE, Color.ALICEBLUE, 30, 30);
    TextField username = screenUtil.makeTextField("",x*5.5,x*0.6,x*9.5,x*3.5,false);
    username.getStyleClass().add("textBox");
    TextField password = screenUtil.makeTextField("",x*5.5,x*0.6,x*9.5,x*4.7,true);
    password.getStyleClass().add("textBox");
    
    ////////////////////////////////////////////////////// FIRST AND LAST NAME END
    
    TextField firstName = screenUtil.makeTextField("",x*2,x*0.6,x*9.5,x*3,false);

    TextField lastName = screenUtil.makeTextField("",x*2,x*0.6,x*(9.5+2+1.5),x*3,false);

    
    ////////////////////////////////////////////////////// FIRST AND LAST NAME END
    
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

    registerHyperlink.setOnAction(e -> { /////////// REGISTER
    	double change = x*0.5;
        loginHyperlink.setLayoutY(loginHyperlink.getLayoutY()+change);
        username.setLayoutY(username.getLayoutY()+change);
        password.setLayoutY(password.getLayoutY()+change);
        registerButton.setLayoutY(registerButton.getLayoutY()+change);

    	this.getChildren().setAll(
                loginImageView,
                formRect,
                screenUtil.makeRoundRect(x * 2, x / 7, x * 7, x * 1.5, screenUtil.seaGrass, screenUtil.seaGrass, 10, 10),
                screenUtil.makeRoundRect(x * 2, x / 7, x * 7, x * 7, screenUtil.seaGrass, screenUtil.seaGrass, 10, 10),
                screenUtil.makeRoundRect(x * 3, x / 7, x * 6.5, x * 7.5, screenUtil.beige, screenUtil.beige, 10, 10),
                screenUtil.makeRect(x * 3, x / 2.1, x * 9.5, 0, screenUtil.seaGrass, screenUtil.seaGrass),
                screenUtil.makeRect(x * 3, x / 8, x * 13.5, x, screenUtil.seaGrass, screenUtil.seaGrass),
                screenUtil.makeRect(x * 3, x / 8, x * 14, x * 1.2, screenUtil.beige, screenUtil.beige),
                screenUtil.makeText("REGISTER","Verdana",48,FontWeight.BOLD,screenUtil.darkSlateGray,x*10.3,x*2),
                screenUtil.makeText("Username","Verdana",16,FontWeight.MEDIUM,screenUtil.darkSlateGray,username.getLayoutX(),username.getLayoutY()-x*0.1),
                screenUtil.makeText("Password","Verdana",16,FontWeight.MEDIUM,screenUtil.darkSlateGray,password.getLayoutX(),password.getLayoutY()-x*0.1),
                screenUtil.makeText("First Name","Verdana",16,FontWeight.MEDIUM,screenUtil.darkSlateGray,firstName.getLayoutX(),firstName.getLayoutY()-x*0.1),
                screenUtil.makeText("Last Name","Verdana",16,FontWeight.MEDIUM,screenUtil.darkSlateGray,lastName.getLayoutX(),lastName.getLayoutY()-x*0.1),
        		username,
        		password,
        		registerButton,
        		loginHyperlink,
        		firstName,
        		lastName
        		);
    });	
    
    
    loginHyperlink.setOnAction(e ->
    {		            	
    double change = x*0.5;
    loginHyperlink.setLayoutY(loginHyperlink.getLayoutY()-change);
    username.setLayoutY(username.getLayoutY()-change);
    password.setLayoutY(password.getLayoutY()-change);
    registerButton.setLayoutY(registerButton.getLayoutY()-change);
    	this.getChildren().setAll(
    	                loginImageView,
    	                formRect,
    	                screenUtil.makeRoundRect(x * 2, x / 7, x * 7, x * 1.5, screenUtil.seaGrass, screenUtil.seaGrass, 10, 10),
    	                screenUtil.makeRoundRect(x * 2, x / 7, x * 7, x * 7, screenUtil.seaGrass, screenUtil.seaGrass, 10, 10),
    	                screenUtil.makeRoundRect(x * 3, x / 7, x * 6.5, x * 7.5, screenUtil.beige, screenUtil.beige, 10, 10),
    	                screenUtil.makeRect(x * 3, x / 2.1, x * 9.5, 0, screenUtil.seaGrass, screenUtil.seaGrass),
    	                screenUtil.makeRect(x * 3, x / 8, x * 13.5, x, screenUtil.seaGrass, screenUtil.seaGrass),
    	                screenUtil.makeRect(x * 3, x / 8, x * 14, x * 1.2, screenUtil.beige, screenUtil.beige),
    	                screenUtil.makeText("WELCOME","Verdana",48,FontWeight.BOLD,screenUtil.darkSlateGray,x*10.3,x*2),
                        screenUtil.makeText("Username","Verdana",16,FontWeight.MEDIUM,screenUtil.darkSlateGray,username.getLayoutX(),username.getLayoutY()-x*0.1),
                        screenUtil.makeText("Password","Verdana",16,FontWeight.MEDIUM,screenUtil.darkSlateGray,password.getLayoutX(),password.getLayoutY()-x*0.1),
    	        		username,
    	        		password,
    	        		loginButton,
    	        		registerHyperlink
    	        		);
    	        		
    });	
    
    ////////// ORIGINAL LOGIN SCREEN START
    this.getChildren().setAll(
            loginImageView,
            formRect,
            screenUtil.makeRoundRect(x * 2, x / 7, x * 7, x * 1.5, screenUtil.seaGrass, screenUtil.seaGrass, 10, 10),
            screenUtil.makeRoundRect(x * 2, x / 7, x * 7, x * 7, screenUtil.seaGrass, screenUtil.seaGrass, 10, 10),
            screenUtil.makeRoundRect(x * 3, x / 7, x * 6.5, x * 7.5, screenUtil.beige, screenUtil.beige, 10, 10),
            screenUtil.makeRect(x * 3, x / 2.1, x * 9.5, 0, screenUtil.seaGrass, screenUtil.seaGrass),
            screenUtil.makeRect(x * 3, x / 8, x * 13.5, x, screenUtil.seaGrass, screenUtil.seaGrass),
            screenUtil.makeRect(x * 3, x / 8, x * 14, x * 1.2, screenUtil.beige, screenUtil.beige),
            screenUtil.makeText("WELCOME","Verdana",48,FontWeight.BOLD,screenUtil.darkSlateGray,x*10.3,x*2),
            screenUtil.makeText("Username","Verdana",16,FontWeight.MEDIUM,screenUtil.darkSlateGray,username.getLayoutX(),username.getLayoutY()-x*0.1),
            screenUtil.makeText("Password","Verdana",16,FontWeight.MEDIUM,screenUtil.darkSlateGray,password.getLayoutX(),password.getLayoutY()-x*0.1),
    		username,
    		password,
    		loginButton,
    		registerHyperlink
    		);
    ////////// ORIGINAL LOGIN SCREEN END
	}
}
