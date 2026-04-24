package com.mycompany.desktophotelreservationsystem;
import com.fazecast.jSerialComm.SerialPort;
import com.mycompany.desktophotelreservationsystem.Controllers.theGOATcontroller;

import java.util.Scanner;

public class RFIDThread implements Runnable {
	public static String commText;
	public static SerialPort commPort;
	User user = new User();
	public void loginCard(String rfidID) {
        System.out.println("Received: " + rfidID);
        for(User u : DataBase.people) {
        	if(rfidID.equals(u.getRfidId())) {
	        DataBase.currentUser = u;
	        DataBase.loggedIn = true;
        	}
        }
        
        if(DataBase.currentUser != null) {
	        if(DataBase.currentUser instanceof Admin) {
	        theGOATcontroller.goated.toLoginFromRFID("/theGoat.fxml");
	        }
	        if(DataBase.currentUser instanceof Guest) {
	        theGOATcontroller.goated.toLoginFromRFID("/guestscenebuilder.fxml");
	        }
	        if(DataBase.currentUser instanceof Receptionist) {
	        theGOATcontroller.goated.toLoginFromRFID("/Receptionists.fxml");
	        }
        }else {
        	System.out.println("No User Found");
        }
    }
	@Override
	public void run() {
		
		for (SerialPort port : SerialPort.getCommPorts()) {
		  //  System.out.println(port.getDescriptivePortName());
		}
			
			commPort = SerialPort.getCommPort("COM5");
			commPort.setBaudRate(9600);
			commPort.setNumDataBits(8);
			commPort.setNumStopBits(1);
			commPort.setParity(SerialPort.NO_PARITY);
			
			if(commPort.openPort()) {
				System.out.println("port is opennnn");
			}else {
				System.out.println("Port is closedddd");
			}
			
			commPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
	        try (Scanner scanner = new Scanner(commPort.getInputStream())) {
	            while (scanner.hasNextLine()) {
            		commText = scanner.nextLine().trim();
	            	if(!DataBase.loggedIn) {
	            	loginCard(commText);
	            	}
	            	
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	}

}
