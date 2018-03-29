// Developed by: Guillermo Ponce
// This driver uses wifi to define if the user outside the room, enter into the room recently, 
// or if he enter a longtime before 

// Network connection
//package presence;

import java.net.*;
import java.net.InetAddress;

import java.util.*;
import java.util.Arrays;
import java.util.Date;

import java.io.*;

// Configuration file
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;

// Text
import java.text.DateFormat;
import java.text.SimpleDateFormat;

// Other
import java.lang.Math ;

public class WIFIPresenceScanner extends PresenceScanner {
    public int allFalsesCounter;
    public int[] lastConnections;     
    int presenceWindow;
    int absenceWindow;
    int completedAbsenceWindows;
    String userPhoneIP;
    int phoneTimeOut;
    int delayTime;

    public WIFIPresenceScanner(){
        loadParametersFromProperties();
        state = NOT_INSIDE;
        allFalsesCounter = 0;
        lastConnections = lastConnections;
        lastConnections = new int [absenceWindow];
        Arrays.fill(lastConnections, 0);
    }
    
    @Override
    public int defineUserPresence(){
        shiftRightConnections();
        lastConnections[absenceWindow-1] = scanIPAddresses(userPhoneIP, phoneTimeOut, delayTime);

        if (state == NOT_INSIDE){
            if (countConnections(lastConnections) == presenceWindow){
                state = RECENTLY_INSIDE;
            }
        }
        else if (state == RECENTLY_INSIDE){
            state = LONG_TIME_INSIDE; 
        }
        else if (state == LONG_TIME_INSIDE){
            if (countConnections(lastConnections) == 0){
                allFalsesCounter++;
                if (allFalsesCounter > completedAbsenceWindows){
                    state = NOT_INSIDE;
                    allFalsesCounter = 0;
                }
            }
        }
        return state;
    }

    // Try to reach a device since the server network by using a ping command
    public int scanIPAddresses(String addressDevice, int phoneTimeOut, int delayTime){
        int wasFound = 0;
        try {
            if (InetAddress.getByName(addressDevice).isReachable(phoneTimeOut))
                wasFound = 1;
            else
                wasFound = 0;

            delay(delayTime);	      
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        return wasFound;
    }

    public int countConnections(int[] connections){
        int counter = 0;
        for (int i = 0; i < connections.length; i++) {
            if (connections[i] == 1)
                counter++;
        }    
        return counter;
    }

    @Override
    public void loadParametersFromProperties() {
        try {
            FileReader reader = new FileReader(configFile);
            Properties prop = new Properties();
            prop.load(reader);

            // Allocate initial values to variables
            userPhoneIP = prop.getProperty("USER_PHONE_IP");
            phoneTimeOut = Integer.parseInt(prop.getProperty("PHONE_TIME_OUT"));
            delayTime = Integer.parseInt(prop.getProperty("PHONE_DELAY_TIME"));
            completedAbsenceWindows = Integer.parseInt(prop.getProperty("COMPLETED_ABSENCE_WINDOWS"));
            presenceWindow = Integer.parseInt(prop.getProperty("PRESENCE_WINDOW"));
            absenceWindow = Integer.parseInt(prop.getProperty("ABSENCE_WINDOW"));
            reader.close();
        } 
        catch (FileNotFoundException ex) { // file does not exist 
	    System.out.println(ex);
        } 
        catch (IOException ex) { // I/O error
	    System.out.println(ex);
        }
    }

    public void delay(int time){
        try {
            Thread.sleep(time);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shiftRightConnections()
    {
        for (int i = 0; i < absenceWindow-1; i++){
            lastConnections[i] = lastConnections[i+1]; 
        }
        lastConnections[absenceWindow-1] = 0; 
    }

    /*
    // Uncomment just for testing the class
    public static void main(String[] args) throws Exception {
        PresenceScanner p = new WIFIPresenceScanner();
        while (true)
        {
            int s = p.defineUserPresence();
            if(s == p.NOT_INSIDE)
                System.out.println("IS NOT IN the room.");
            else if(s == p.RECENTLY_INSIDE)
                System.out.println("ENTER INTO the room.");
            else if(s == p.LONG_TIME_INSIDE)
                System.out.println("STAYS IN the room.");
        }
    }*/

}
