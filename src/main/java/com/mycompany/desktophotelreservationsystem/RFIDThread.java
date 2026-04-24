package com.mycompany.desktophotelreservationsystem;
import com.fazecast.jSerialComm.SerialPort;
import com.mycompany.desktophotelreservationsystem.Controllers.theGOATcontroller;

import java.util.Scanner;

public class RFIDThread implements Runnable {
	SerialPort commPort;
	User user = new User();
	public void loginCard(String rfidID) {
        System.out.println("Received: " + rfidID);
        DataBase.currentUser = user.login("Ahmed", "67", true);
        DataBase.loggedIn = true;
        theGOATcontroller.goated.toLoginFromRFID("/theGoat.fxml");
	}
	@Override
	public void run() {
		
		for (SerialPort port : SerialPort.getCommPorts()) {
		    System.out.println(port.getDescriptivePortName());
		}
			
			commPort = SerialPort.getCommPort("COM5");
			commPort.setBaudRate(9600);
			commPort.setNumDataBits(8);
			commPort.setNumStopBits(1);
			commPort.setParity(SerialPort.NO_PARITY);
			
			if(commPort.openPort()) {
				System.out.println(commPort.getDescriptivePortName() + "is opennnn");
			}else {
				System.out.println("Port is closedddd");
			}
			
			commPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
	        try (Scanner scanner = new Scanner(commPort.getInputStream())) {
	            while (scanner.hasNextLine()) {
	            	if(!DataBase.loggedIn) {
	            	loginCard(scanner.nextLine().trim());
	            	}else {
	            		scanner.nextLine().trim();
	            	}
	            	
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	}

}
