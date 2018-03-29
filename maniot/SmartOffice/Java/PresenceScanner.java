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

public class PresenceScanner {
    public static int NOT_INSIDE = 0;
    public static int RECENTLY_INSIDE = 1;
    public static int LONG_TIME_INSIDE = 2;
    public int state;
    String logFileName;
    File configFile = new File("PresenceScanner.properties");

    public PresenceScanner(){
        loadParametersFromProperties();
        state = NOT_INSIDE;
    }

    public int defineUserPresence(){
        if (state == NOT_INSIDE){
            if (scanUserDevice()){
                state = 1;
            }
        }
        else if (state == RECENTLY_INSIDE){
            state = 2; 
        }
        else if (state == LONG_TIME_INSIDE){
            if (!scanUserDevice()){
                    state = NOT_INSIDE;
            }
        }
        return state;
    }

    public boolean scanUserDevice(){
        
        return true;
    }

    public void loadParametersFromProperties() {
    }

    public void delay(int time){
        try {
            Thread.sleep(time);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}


/* PARA SER USADO EN EL ORQUESTADOR

    boolean isUserPresent = verifyUserInside();
    int[] lastConnections = new int[ABSENCE_WINDOW];
    Arrays.fill(lastConnections, 0);
    int state = 0, allFalsesCounter = 0;
    loadConfigurationData();

    if (defineUserPresence(state, allFalsesCounter, lastConnections) == RECENTLY_INSIDE){
        ejectPrediction();
    }
    else{
        powerOFFDevices();     
    }

*/
