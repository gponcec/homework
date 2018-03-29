//package restcommunicator;

//import com.rest.controllers.*;

import java.util.List;
import java.util.ArrayList;
import java.util.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.QueryParam;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import java.util.concurrent.ThreadLocalRandom;
import com.mashape.unirest.http.exceptions.UnirestException;

// Sql
import java.sql.ResultSet;
import java.sql.SQLException;


@Path("/rest")
public class Communicator {

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Data inserted successfully.";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getConfiguration")
    // ServerSendConf
    public RoomConfiguration getConfiguration(){
        RoomConfiguration rc = new RoomConfiguration();

        rc.setAcuTemperature(17);
        rc.setAcuSwingOn(0);
        rc.setAcuAirOn(0);
        rc.setAcuSpeed(0);
        rc.setAcuLightOn(0);
        return rc;
    }

    //*************************************************************************
    //Fan Values    | Temp VALUES | SWING VALUES | STATUS VALUES | ID VALUES
    //"auto" -> 3   |             |              |               | 
    //"fraco" -> 2  |    17~26    |   ON - OFF   |   ON - OFF    | 147 - DANIEL
    //"medio" -> 1  |             |              |               | 197 - WINET
    //"forte" -> 0  |             |              |               |
    //*************************************************************************
    @POST
    @Path("sendConfiguration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String sendConfiguration(RoomConfiguration conf) {
        int newAirOn = -1, newTemperature = -1, newSpeed = -1, newSwingOn = -1, newLightOn = -1, 
        newWeatherRating = -1, newLuminosityRating = -1, newNoiseRating = -1;
        int isUserOrSystem = 1, timeInterval = 90000;

	    // Get the new parameters
        newAirOn = conf.getAcuAirOn();
        newTemperature = conf.getAcuTemperature();
        newSpeed = conf.getAcuSpeed(); 
        newSwingOn = conf.getAcuSwingOn();
        newLightOn = conf.getAcuLightOn();
        System.out.println("NEW USER CONFIGURATION:"+ newAirOn + ", " + newTemperature + ", " + newSpeed +", "+newSwingOn+","+newLightOn);

        String strSpeed;
	if (newSpeed == 0){
	    strSpeed = "auto";
	}
	else if (newSpeed == 1) {
	    strSpeed = "fraco";
	}
	else if (newSpeed == 2){
	    strSpeed = "medio";
	}
	else
	    strSpeed = "forte";
	

	
        // Request the ACU to react using the user request
        AirControl acuControl = new AirControl();
        acuControl.Login();
        acuControl.GetParams(197);
        acuControl.SetAll(197, newAirOn==1 ? "on" : "off", String.valueOf(newTemperature),newLightOn==1 ? "on":"off",strSpeed);
        
        // Request the WEMO to react using the user request
        ControllerSwitchWemo wemoControl = new ControllerSwitchWemo();     //Driver Tomada Belkin Wemo
        boolean wemoStatus = false;
        if (newLightOn==1){
            if (!wemoStatus){
                try {
                    wemoControl.SwitchOn();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        else if (newLightOn==0){
            if (wemoStatus){
                try {
                    wemoControl.SwitchOff();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        else{}    

        // Store the new configuration in the database
        Storage databaseDriver = new Storage();
        String response = String.valueOf(databaseDriver.addLearning(newAirOn, newTemperature, newSpeed, newSwingOn, newLightOn, newWeatherRating, newLuminosityRating, newNoiseRating, isUserOrSystem, timeInterval));
        return String.valueOf(response);
    }


    @POST
    @Path("sendRatings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String sendRatings(RoomConfiguration conf) {
        int newAirOn = -1, newTemperature = -1, newSpeed = -1, newSwingOn = -1, newLightOn = -1; 
        int isUserOrSystem = 1, timeInterval = 90000;
        float newWeatherRating = -1, newLuminosityRating = -1, newNoiseRating = -1;

        newWeatherRating = conf.getWeatherRating();
        newLuminosityRating = conf.getLuminosityRating();
        newNoiseRating = conf.getNoiseRating();
        System.out.println("NEW USER RATINGS"+ newWeatherRating + " - " + newLuminosityRating + " - " + newNoiseRating);

        Storage databaseDriver = new Storage();
        String response = String.valueOf(databaseDriver.addLearning(newAirOn, newTemperature, newSpeed, newSwingOn, newLightOn, newWeatherRating, newLuminosityRating, newNoiseRating, isUserOrSystem, timeInterval));
        return String.valueOf(response);
    }


    @POST
    @Path("sendAppSettings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String sendAppSettings(AppSettings settings) {
        String midiaUser = "", midiaPassword = "", databaseServerIP = "", databaseUser = "", databasePassword = "", restMLServerIP = "", restMLServerPort = "";

        midiaUser = settings.getMidiaUser(); 
        midiaPassword = settings.getMidiaPassword(); 
        databaseServerIP = settings.getDatabaseServerIP(); 
        databaseUser = settings.getDatabaseUser(); 
        databasePassword = settings.getDatabasePassword(); 
        restMLServerIP = settings.getRestMLServerIP(); 
        restMLServerPort = settings.getRestMLServerPort();

        System.out.println("NEW APP SETTINGS"+ midiaUser + ", " + midiaPassword + ", " + databaseServerIP + ", " 
                                             + databaseUser + ", "+ databasePassword + ", " + restMLServerIP + ", " + restMLServerPort);

        /*  MODIFICAR LOS VALORES DEL ARCHIVO PROPERTIES
        Properties prop = new Properties();
        prop.load(...); // FileInputStream 
        prop.setProperty("key", "value");
        prop.store(...); // FileOutputStream */

	    String response ="1";
        return String.valueOf(response);
    }
}
