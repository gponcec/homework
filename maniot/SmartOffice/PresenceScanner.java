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
    public static int LONG_TIME_OUTSIDE = 0;
    public static int RECENTLY_INSIDE = 1;
    public static int LONG_TIME_INSIDE = 2;
    public static int RECENTLY_OUTSIDE = 3;
    public int state;
    String logFileName;
    File configFile = new File("PresenceScanner.properties");

    public PresenceScanner(){
        loadParametersFromProperties();
        state = LONG_TIME_OUTSIDE;
    }

    public int defineUserPresence(){
        if (state == LONG_TIME_OUTSIDE){
            if (scanUserDevice()){
                state = RECENTLY_INSIDE;
            }
        }
        else if (state == RECENTLY_INSIDE){
            //if (scanUserDevice()){
                state = LONG_TIME_INSIDE; 
            //}
            //else{
            //    state = RECENTLY_OUTSIDE; 
            //}
        }
        else if (state == LONG_TIME_INSIDE){
            if (!scanUserDevice()){
                state = RECENTLY_OUTSIDE;
            }
        }
        else if (state == RECENTLY_OUTSIDE){
            //if (!scanUserDevice()){
                state = LONG_TIME_OUTSIDE; 
            //}
            //else{
            //    state = RECENTLY_INSIDE;
            //}
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

