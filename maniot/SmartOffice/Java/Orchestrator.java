import java.io.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


// Sql
import java.sql.ResultSet;
import java.sql.SQLException;

public class Orchestrator implements Runnable{

Storage driverDatabase = new Storage();
PresenceScanner presenceDriver = new WIFIPresenceScanner();

public Orchestrator(){
}

public void run(){
        while (true)
        {
            int s = presenceDriver.defineUserPresence();
            if(s == presenceDriver.LONG_TIME_OUTSIDE){
                System.out.println("LONG TIME OUTSIDE the room.");
                }

            else if(s == presenceDriver.RECENTLY_INSIDE){
                System.out.println("ENTER RECENTLY INTO the room.");
                try {
                    ResultSet result = driverDatabase.selectEnvironment(1, 20);
                    //int temperature=-1, pressure=-1, humidity=-1, noise=-1, airQuality=-1, luminosity1=-1, luminosity2=-1,date=-1,time=-1;
		    String parameters = "24,-1,-1,-1,-1,-1,-1,-1,-1";
		    String TimeDate;

                    if (result.next()){
			TimeDate = result.getString("time");						
			String[] TimeDateSplit = TimeDate.split(" ");
			
                        parameters = result.getString("temperature") + ","
                        + result.getString("pressure") + "," + result.getString("humidity") + ","
                        + result.getString("noise") + "," + result.getString("airQuality") + ","
                        + result.getString("luminosity1") + "," + result.getString("luminosity2") + ","
                        + TimeDateSplit[0] +  "," +  TimeDateSplit[1].replace(":","-").substring(0,8);
			
                    }
		    System.out.println(parameters);	
                    Process proc = Runtime.getRuntime().exec(new String[] {"python", "-c", "import Predictor; Predictor.predict("+parameters+")"});
                 }
                 catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error: Predictor has failed calculating or executing prediction.");
                }
            }
            else if(s == presenceDriver.LONG_TIME_INSIDE){
                System.out.println("LONG TIME INTO the room.");
            }
            else if(s == presenceDriver.RECENTLY_OUTSIDE){
                System.out.println("RECENLTLY OUTSIDE the room.");
                try {
                    Process proc = Runtime.getRuntime().exec(new String[] {"python", "-c", "import Predictor; Predictor.disactivate_devices()"});
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    System.out.println("Error: Predictor has failed disabling devices.");
                }
            }//end of else                        
        }//while end
    }//run end
}//end of class
