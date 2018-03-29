//***VERY IMPORTANT NOTE***
//THIS CODE WORKS ONLY FOR DANIEL'S ROOM AND WILL BE USED TO GATHER DATA
//FOR GUILLERMO'S RESEARCH. PLEASE DO NOT USE IT FOR OTHER PURPOSES

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class SwitchControl extends SensorsValues {

    public SwitchControl (){

    }

    //Because we need to orient the lights to events
    //Returns true if we have an event, false if we don't
    public static int presenceDealerOn (int window [], int newPresence) {
        if (window[0] == window[1] && newPresence != window[1] && newPresence == 1){
            return 1;
        }
        else {
            return 0;
        }
    }
  
    public static int presenceDealerOff (int window [], int newPresence) {
        if (window[0] == window[1] && newPresence != window[1] && newPresence == 0){
            return 1;
        }
        else {
            return 0;
        }
    }

    public static void insertPresence (int window [], int newPresence) {
        window [0] = window [1];
        window [1] = newPresence;
    }

    //This method calls a terminal and makes a curl to turn the lights on, because I couldn't figure out
    //how to do this with the HttpURLConnect or URLConnect library, if you figure out, please change this
    public void turnLightsOn () {
        System.out.println("Turning lights ON");
        try{
            ProcessBuilder pb = new ProcessBuilder("curl", "-X", "PUT",
                                                    apiEndpoint + actuators_path + "/on",
                                                    "-H", "Authorization: Bearer " + apiToken);
            pb.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void turnLightsOff () {
        System.out.println("Turning lights OFF");
        try{
            ProcessBuilder pb = new ProcessBuilder("curl", "-X", "PUT", 
                                                    apiEndpoint + actuators_path + "/off",
                                                    "-H", "Authorization: Bearer " + apiToken);
            pb.start();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    //Create a thread to run this method whilewe don't figure out how to orient this to events
    public void presenceWatchdog (String device) {   
        if (1 == getPresenceByDevice(device)){
            turnLightsOn();
            //System.out.println("Presente");
        }
        else {
            turnLightsOff();
            //System.out.println("Não Presente");
        }
        requestSensorsValues();
    }

     //Create a thread to run this method whilewe don't figure out how to orient this to events
    public void presenceThread (String device) {
        int presence = 0;
        
        //Assumes user is not present when the program starts for the first time
        insertPresence(presenceWindow, presence);
        insertPresence(presenceWindow, presence);

        //Checks if the user is present or not and actuates
        while(true) {
            if (1 == getPresenceByDevice(device)){
                presence = 1;
                //System.out.println("Presente");
            }
            else {
                presence = 0;
                //System.out.println("Não Presente");
            }
            if (1 == presenceDealerOn(presenceWindow, presence)){
                System.out.println("TURNING LIGHTS ON");
                turnLightsOn();
            }
            else if (1 == presenceDealerOff(presenceWindow, presence)){
                System.out.println("TURNING LIGHTS OFF");
                turnLightsOff();
            }
            insertPresence(presenceWindow, presence);
            requestSensorsValues();
        }
    }
}
