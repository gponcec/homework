import java.io.*;
import java.util.Vector;

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

//Singleton
import javax.inject.Singleton;
// Sql
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Properties;


@Path("/rest")
@Singleton
public class Communicator {

    AirControl acuControl = new AirControl();
    //ControllerSwitchWemo wemoControl = new ControllerSwitchWemo();
    ShadeControl shadeControl = new ShadeControl();
    Storage databaseDriver = new Storage();
    SwitchControl smartSwitch= new SwitchControl();
    //boolean wemoStatus = false;

    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {
        return "Connection stablished correctly.";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getConfiguration")
    public RoomConfiguration getConfiguration(){
        System.out.println("COMM: Sending configuration...");
        RoomConfiguration rc = new RoomConfiguration();
        Vector<Integer> airParams = acuControl.GetTreatParameters(147);
        int airOn = airParams.get(0);
        int temperature = airParams.get(1);
        int speedOn = airParams.get(2);
        int swingOn = airParams.get(3);
        
        int lightOn = 1;
        int openShade = 0;        

        System.out.println("COMM:    airOn: "+ airOn + ", temperature: " + temperature + ", speedOn: " + speedOn + ", swingOn: " + swingOn +
                           ", lightOn: " + lightOn + ", openShade: " + openShade);
        rc.setAcuAirOn(airOn); rc.setAcuTemperature(temperature); rc.setAcuSpeed(speedOn); rc.setAcuSwingOn(swingOn); 
        rc.setRoomLightOn(lightOn); 
        rc.setRoomOpenShade(openShade);

        try {
            ResultSet result = databaseDriver.selectLearning(1, -1); // -1 for not consider time
            if (result.next()){
                rc.setAcuAirOn(Integer.parseInt(result.getString("acuAirOn")));
                rc.setAcuTemperature(Integer.parseInt(result.getString("acuTemperature")));
                rc.setAcuSpeed(Integer.parseInt(result.getString("acuSpeed")));
                rc.setAcuSwingOn(Integer.parseInt(result.getString("acuSwingOn")));
                rc.setRoomLightOn(Integer.parseInt(result.getString("roomLightOn")));
                rc.setRoomOpenShade(Integer.parseInt(result.getString("roomOpenShade")));
                System.out.println(rc);

            }
        } catch (SQLException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}            
        return rc;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("getEnvironment")
    public Environment getEnvironment(){
        Environment env = new Environment();
        env.setEnvTemperature(-1); env.setEnvPressure(-1); env.setEnvHumidity(-1); env.setEnvNoise(-1);
        env.setEnvAirQuality(-1); env.setEnvLuminosity1(-1); env.setEnvLuminosity2(-1); env.setEnvOpenWindow(-1);
        env.setEnvOpenDoor(-1); env.setEnvTrackTime("01-27-2018 00:00:00");

        try {
            ResultSet result = databaseDriver.selectEnvironment(1, 20);
            if (result.next()){
                env.setEnvTemperature(Integer.parseInt(result.getString("envTemperature")));
                env.setEnvPressure(Integer.parseInt(result.getString("envPressure")));
                env.setEnvHumidity(Integer.parseInt(result.getString("envHumidity")));
                env.setEnvNoise(Integer.parseInt(result.getString("envNoise")));
                env.setEnvAirQuality(Integer.parseInt(result.getString("envAirQuality")));
                env.setEnvLuminosity1(Integer.parseInt(result.getString("envLuminosity1")));
                env.setEnvLuminosity2(Integer.parseInt(result.getString("envLuminosity2")));
                env.setEnvOpenWindow(Integer.parseInt(result.getString("envOpenWindow")));
                env.setEnvOpenDoor(Integer.parseInt(result.getString("envOpenDoor")));
                env.setEnvTrackTime(result.getString("envTrackTime"));
            }
        } catch (SQLException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}            
        return env;
    }


    @POST
    @Path("sendConfiguration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String sendConfiguration(RoomConfiguration newConfiguration) {
        System.out.println("COMM: Rest server receiving configuration...");
	
        String response = applyAirChanges(newConfiguration) + applyLightChanges(newConfiguration) +
                          applyShadeChanges(newConfiguration) +
                           storeConfiguration(newConfiguration);

        if (response.equals("1111")){
            System.out.println("COMM:     Changes completed!");	                                                             
        }
        else{
            System.out.println("COMM:     Something was wrong.");
        }
        return String.valueOf(response);
    }

    @POST
    @Path("sendRatings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String sendRatings(RoomConfiguration conf) {
        int newWeatherRating = conf.getUserWeatherRating();
        int newLuminosityRating = conf.getUserLuminosityRating();
        int newNoiseRating = conf.getUserNoiseRating();
        String newAppUser = "none".toString();
	int isUserOrSystem = 1;
	int newTimeInterval = -1;
        System.out.println("Received user ratings: "+ newWeatherRating + " - " + newLuminosityRating + " - " + newNoiseRating);

        Storage databaseDriver = new Storage();
        String response = String.valueOf(databaseDriver.addLearning(-1, -1, -1, -1, -1, -1, newWeatherRating, newLuminosityRating, newNoiseRating, isUserOrSystem, newTimeInterval, newAppUser));
        return String.valueOf(response);
    }


    @POST
    @Path("sendAppSettings")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String sendAppSettings(AppSettings settings) {
        String response = "00000"; 
        System.out.println("COMM: Rest server receiving app settings...");
        System.out.println("COMM:    new user configuration: (DATABASE) -> Server IP: " + settings.getDbServerIP() + 
                                    ", Server Port: " + settings.getDbServerPort() + ", DB name: " + settings.getDbName() + 
                                    ", Username: " + settings.getDbUsername() + ", Password: " + settings.getDbPassword() + 
                                    "; (REST) -> Server IP: " + settings.getRestServerIP() + ", Server Port: " + settings.getRestServerPort() + 
                                    "; (AIR CONTROL) -> Username: " + settings.getMidiaUsername() + ", Password: " + settings.getMidiaPassword() +
                                    "; (PRESENCE) -> Phone IP: " + settings.getUserPhoneIP() + ",");

        response = updateDatabaseProperties(settings) + updateOrchestratorProperties(settings) + 
                   updatePredictorProperties(settings) + updateAirControlProperties(settings) +
                   updatePresenceScannerProperties(settings);

        return String.valueOf(response);
    }

    public Vector<String> getAirControlProperties()
    {
        Properties prop = new Properties();
        InputStream input = null;
        Vector<String> values = new Vector<String>();

        try {
            input = new FileInputStream("AirControl.properties");
            prop.load(input);
            String airUnit = prop.getProperty("AIR_UNIT");
            values.add(airUnit); 
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return values;
    }


    public String applyAirChanges(RoomConfiguration newConfiguration){
        // get values
        int newAirOn = newConfiguration.getAcuAirOn();
        int newTemperature = newConfiguration.getAcuTemperature();
        int newSpeed = newConfiguration.getAcuSpeed(); 
        int newSwingOn = newConfiguration.getAcuSwingOn();

        Vector<String> properties = getAirControlProperties();
        int airUnit = Integer.parseInt(properties.get(0));
        String airOn = newAirOn==1 ? "on" : "off";
        String temperature = String.valueOf(newTemperature);
        String swingOn = newSwingOn==1 ? "on" : "off";
	    System.out.println("COMM:     air changes->  Air: " + airOn + ", Temperature: " + newTemperature +
                           ", Speed: " + newSpeed + ", Swing: " + swingOn);
        String speed = String.valueOf(newSpeed);

        // Request the ACU to react using the user request
        System.out.print("COMM:     applying air changes...");
        String executionStatus = "0";
        try {
            acuControl.SetAll(airUnit, airOn, temperature, swingOn, speed);
            System.out.println("COMM:    AIR    OK!");
            executionStatus = "1";
        }
        catch(Exception e){
            System.out.println("COMM:    AIR    FAILED!");
            //e.printStackTrace();
        }
        return executionStatus;
    }

    public String applyLightChanges(RoomConfiguration newConfiguration){
        //SmartThingsDriver smartSwitch = new SmartThingsDriver();
        // get values 
        int newLightOn = newConfiguration.getRoomLightOn();
        String lightOn = newLightOn==1 ? "On" : "Off";
	    System.out.println("COMM:     Light changes-> Light: " + lightOn);

        System.out.print("COMM:     applying light changes...");
        // Request the WEMO to react using the user request
        String executionStatus = "0";
        if (newLightOn==1){
                try {
                    //wemoControl.SwitchOn();
                    smartSwitch.turnLightsOn();
		    //wemoStatus = true;
                    System.out.println("COMM:    Light    OK!");	
                    executionStatus = "1";
                }
                catch(Exception e){
		    System.out.println("COMM:    Light    FAILED!");
                    //e.printStackTrace();
                }
        }
        else if (newLightOn==0){
                try {
                    //wemoControl.SwitchOff();
                    smartSwitch.turnLightsOff();
                    //wemoStatus = false;
                    System.out.println("COMM:    Light    OK!");	
                    executionStatus = "1";
                }
                catch(Exception e){
                    System.out.println("COMM:    Light    FAILED!");
                    //e.printStackTrace();
                }
        }
        else{
            System.out.println("COMM:    Light    Unexpected value.");
        }
        return executionStatus;
    }

    public String applyShadeChanges(RoomConfiguration newConfiguration){
        int newOpenShade = newConfiguration.getRoomOpenShade();
        System.out.println("COMM:    newOpenShade: " + newOpenShade + "  battery: " + shadeControl.getShadeBattery());
        int openShade;
        if (newOpenShade == 0)
            openShade = 0; // completely opened
        else if (newOpenShade == 1)
            openShade = 33;
        else if (newOpenShade == 2)
            openShade = 66;
        else if (newOpenShade == 3)
            openShade = 99; // completely closed
        else
            openShade = 0;

        System.out.print("COMM:    applying shade changes...");
        String executionStatus = "0";
        //System.out.println(shadeControl.getShadePosition+" - "+openShade);        
        //if (shadeControl.getShadePosition()!=newOpenShade){
            if (shadeControl.setShadePosition(openShade)){
                System.out.println("COMM:    Shade    OK!");
                executionStatus = "1";
            }
            else{
                System.out.println("COMM:    Shade    FAILED!");
            }
        //}
        //else{
        //    System.out.println("    Without changes.    OK!");
        //}
        return executionStatus;
    }

    public String storeConfiguration(RoomConfiguration configuration){
        // Store the new configuration in the database
        System.out.println("COMM:     storing configuration in database...");
        String executionStatus = "0";
        if (databaseDriver.addLearning(configuration.getAcuAirOn(), configuration.getAcuTemperature(),
                                                      configuration.getAcuSpeed(), configuration.getAcuSwingOn(),
                                                      configuration.getRoomLightOn(), configuration.getRoomOpenShade(), 
                                                      -1, -1, -1, configuration.getIsUserOrSystem(), 90000, configuration.getAppUser())==1){
            System.out.println("COMM:    DB    OK!");
            executionStatus = "1";
        }
        else{
            System.out.println("COMM:    DB    FAILED!");
        }
        return executionStatus;
    }


    public String updateDatabaseProperties(AppSettings settings){
        // database properties
        System.out.println("COMM:     editing database properties file...");
        String dbServerIP = settings.getDbServerIP();
        String dbServerPort = settings.getDbServerPort();
        String dbName = settings.getDbName(); 
        String dbUsername = settings.getDbUsername(); 
        String dbPassword = settings.getDbPassword(); 
        String executionStatus = "0";
        try{
            FileInputStream in = new FileInputStream("ConnectMySQL.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream("ConnectMySQL.properties");
            props.setProperty("DB_SERVER_NAME", dbServerIP);
            props.setProperty("DB_SERVER_PORT", dbServerPort);
            props.setProperty("DB_NAME", dbName);
            props.setProperty("DB_USERNAME", dbUsername);
            props.setProperty("DB_PASSWORD", dbPassword);
            props.store(out, null);
            out.close();
            System.out.println("COMM:    ConnectMySQL.properties changed    OK!");
            executionStatus = "1";
        }
        catch (FileNotFoundException ex)  {
            System.out.println("COMM:     'ConnectMySQL.properties' file was not found.    FAILED");
        }
        catch (SecurityException ex)  {
            System.out.println("COMM:     'ConnectMySQL.properties' does not have read permissions.    FAILED");
        }
        catch (IOException e){
            System.out.println("COMM:     the FileInputStream argument is invalid.    FAILED");
        }
        return executionStatus;
    }


    public String updateOrchestratorProperties(AppSettings settings){
        // rest server
        System.out.print("COMM:     editing rest server properties file...");
        String restServerIP = settings.getRestServerIP(); 
        String restServerPort = settings.getRestServerPort(); 
        String executionStatus = "0";
        try{
            FileInputStream in = new FileInputStream("Orchestrator.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream("Orchestrator.properties");
            props.setProperty("SERVER_IP", restServerIP);
            props.setProperty("SERVER_PORT", restServerPort);
            props.store(out, null);
            out.close();
            System.out.println("COMM:    Orchestrator.Properties changes    OK!");
            executionStatus = "1";
        }
        catch (FileNotFoundException ex)  {
            System.out.println("COMM:     'Orchestrator.properties' file was not found.    FAILED");
        }
        catch (SecurityException ex)  {
            System.out.println("COMM:     'Orchestrator.properties' does not have read permissions.    FAILED");
        }
        catch (IOException e){
            System.out.println("COMM:     the FileInputStream argument is invalid.    FAILED");
        }
        return executionStatus;
    }

    public String updatePredictorProperties(AppSettings settings){
        System.out.println("COMM:     editing rest server properties file for predictor (python)...");
        String restServerIP = settings.getRestServerIP(); 
        String restServerPort = settings.getRestServerPort(); 
        String executionStatus = "0";
        try{
            PrintWriter writer = new PrintWriter("config.ini");
            writer.println("[RestConnection]");
            writer.println("Server: "+ restServerIP);
            writer.println("Port: "+ restServerPort);
            writer.close();
            System.out.println("COMM:    config.ini changes    OK!");
            executionStatus = "1";
        }
        catch(FileNotFoundException fnfe){
            System.out.println("COMM:     'config.ini' file was not found.    FAILED");
        }
        return executionStatus;
    }

    public String updateAirControlProperties(AppSettings settings){
        System.out.println("COMM:     editing air control properties file...");
        String midiaUsername = settings.getMidiaUsername(); 
        String midiaPassword = settings.getMidiaPassword(); 
        String executionStatus = "0";
        try{
            FileInputStream in = new FileInputStream("AirControl.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream("AirControl.properties");
            props.setProperty("MIDIA_USERNAME", midiaUsername);
            props.setProperty("MIDIA_PASSWORD", midiaPassword);
            props.store(out, null);
            out.close();
            System.out.println("COMM:    AirControl.properties    OK!");
            executionStatus = "1";
        }
        catch (FileNotFoundException ex)  {
            System.out.println("COMM:     'AirControl.properties' file was not found.    FAILED");
        }
        catch (SecurityException ex)  {
            System.out.println("COMM:     'AirControl.properties' does not have read permissions.    FAILED");
        }
        catch (IOException e){
            System.out.println("COMM:     the FileInputStream argument is invalid.    FAILED");
        }
        return executionStatus;
    }

    public String updatePresenceScannerProperties(AppSettings settings){
        System.out.print("COMM:     editing user phone properties file...");
        String userPhoneIP = settings.getUserPhoneIP();
        String executionStatus = "0";
        try{
            FileInputStream in = new FileInputStream("PresenceScanner.properties");
            Properties props = new Properties();
            props.load(in);
            in.close();

            FileOutputStream out = new FileOutputStream("PresenceScanner.properties");
            props.setProperty("USER_PHONE_IP", userPhoneIP);
            props.store(out, null);
            out.close();
            System.out.println("    OK!");
            executionStatus = "1";
        }
        catch (FileNotFoundException ex)  {
            System.out.println("COMM:     'PresenceScanner.properties' file was not found.    FAILED");
        }
        catch (SecurityException ex)  {
            System.out.println("COMM:     'PresenceScanner.properties' does not have read permissions.    FAILED");
        }
        catch (IOException e){
            System.out.println("COMM:     the FileInputStream argument is invalid.    FAILED");
        }
        return executionStatus;
    }
}
	
