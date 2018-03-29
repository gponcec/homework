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

public class SmartThingsDriver {

    //These variables are used to simulate an event based system for turning lights on and off
    //according to user's presence, while we don't figure out how to use IFTTT
    protected static int windowSize;
    protected static int presenceWindow [];
    
    //This variable is a JSONArray that contains all the data from the GET
    protected JSONArray smartThingsData;

    protected URL url;
    protected URLConnection uc;
    
    //All these variables are from SmartApps
    protected String keyChainName;
    protected String apiEndpoint;
    protected String sensors_path;
    protected String actuators_path;
    protected String apiToken;
    
    //Selects wich device will be used by this Class to define whether the user is present or not (may be user's phone, key chain)
    protected String device;
    protected String propertyFileName = "SmartThings.properties";

    public SmartThingsDriver (){
        windowSize = 2;
        presenceWindow = new int [windowSize];
        //Opens properties file and loads configuration
    	try {
            FileReader reader = new FileReader(propertyFileName);
            Properties prop = new Properties();
            prop.load(reader);

            //Assigns initial values to variables according to the installed SmartApp
            //These informations should be on a .properties file
            apiEndpoint = prop.getProperty("API_ENDPONT");
            //Paths must come with "/", exemple: /env, /switches...
            sensors_path = prop.getProperty("SENSORS_PATH");
            actuators_path = prop.getProperty("ACTUATORS_PATH");
            apiToken = prop.getProperty("API_TOKEN");

            reader.close();
        }
        catch (FileNotFoundException ex) {
            //File does not exist
            System.out.println("SMART THINGS:    "+ propertyFileName +" does not exist.");
        } 
        catch (IOException ex) { 
            //I/O error
            System.out.println("SMART THINGS:    can't read succesfully from file.");
        }
    }

    protected boolean connectTo(String urlToConnect){
        try{
            this.url = new URL(urlToConnect);
            this.uc = this.url.openConnection();
        }
        catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean isConnected(URLConnection con) {
        try {
            con.setDoOutput(con.getDoOutput()); // throws IllegalStateException if connected
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }
}
