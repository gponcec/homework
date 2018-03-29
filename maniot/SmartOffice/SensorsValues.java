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

public class SensorsValues extends SmartThingsDriver {

    public SensorsValues (){
    }

    //This method connects to the installed SmartApp's endpoint and send a GET to retrive information about the sensors
    public void requestSensorsValues () throws JSONException {
    	String stringUrl = apiEndpoint+sensors_path;
    	try{
      		connectTo(stringUrl);
      		uc.setRequestProperty("X-Requested-With", "Curl");
      		uc.setRequestProperty("Authorization", "Bearer " + apiToken);
      		BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
      		String inputLine;
      		StringBuffer response = new StringBuffer();
      		while ((inputLine = in.readLine()) != null){
        		response.append(inputLine);
      		}
      		in.close();
      		String dataStringFormat = response.toString();
            smartThingsData = new JSONArray(dataStringFormat);
    	}
    	catch (IOException ex) {
            System.out.println ("Invalid URL: "+ stringUrl);
    	}
    }

    public void printJSON () {
        System.out.println(smartThingsData.toString());
    }

    public double getTemperatureByDevice (String device) throws JSONException {
        double temp = 0;
        for (int i = 0; i < smartThingsData.length(); i++) {
            JSONObject jsonobject = smartThingsData.getJSONObject(i);
            String sensor = jsonobject.getString("sensor");
            String name = jsonobject.getString("name");
            if (name.equals(device) && sensor.equals("Temperature")){
                temp = jsonobject.getDouble("value");
                break;
            }
            else if (i == smartThingsData.length() - 1) {
                System.out.println("Device not found.");
                return -274;
                //This temperature doesn't exist in celcius, because it is below 0 Kelvin
            }
        }
        return temp;
    }

    public int getDWStatusByDevice (String device) throws JSONException {
        int dwStatus = -1;
        for (int i = 0; i < smartThingsData.length(); i++) {
            JSONObject jsonobject = smartThingsData.getJSONObject(i);
            String sensor = jsonobject.getString("sensor");
            String name = jsonobject.getString("name");
            if (name.equals(device) && sensor.equals("Door/Window")){
                String dwString = jsonobject.getString("value");
                if (dwString.equals("open")){
                    dwStatus = 1;
                }
                else {
                    dwStatus = 0;
                }
            break;
            }
            else if (i == smartThingsData.length() - 1) {
                System.out.println("Device not found.");
            }
        }
        return dwStatus;
    }

    public int getHumidityByDevice (String device) throws JSONException {
        int humidity = 0;
        for (int i = 0; i < smartThingsData.length(); i++) {
            JSONObject jsonobject = smartThingsData.getJSONObject(i);
            String sensor = jsonobject.getString("sensor");
            String name = jsonobject.getString("name");
            if (name.equals(device) && sensor.equals("Humidity")){
                humidity = jsonobject.getInt("value");
                break;
            }
            else if (i == smartThingsData.length() - 1) {
                System.out.println("Device not found.");
                return -1;
            }
        }
        return humidity;
    }

    public int getIlluminanceByDevice (String device) throws JSONException {
        int illuminance = 0;
        for (int i = 0; i < smartThingsData.length(); i++) {
            JSONObject jsonobject = smartThingsData.getJSONObject(i);
            String sensor = jsonobject.getString("sensor");
            String name = jsonobject.getString("name");
            if (name.equals(device) && sensor.equals("Illuminance")){
                illuminance = jsonobject.getInt("value");
                break;
            }
            else if (i == smartThingsData.length() - 1) {
                System.out.println("Device not found.");
                return -1;
            }
        }
        return illuminance;
    }

    public int getPresenceByDevice (String device) throws JSONException {
        int presence= 0;
        for (int i = 0; i < smartThingsData.length(); i++) {
            JSONObject jsonobject = smartThingsData.getJSONObject(i);
            String sensor = jsonobject.getString("sensor");
            String name = jsonobject.getString("name");
            if (name.equals(device) && sensor.equals("Presence")){
                String presenceString = jsonobject.getString("value");
                if (presenceString.equals("present")) {
                    presence = 1;
                }
                else {
                    presence = 0;
                }
                break;
            }
            else if (i == smartThingsData.length() - 1) {
                System.out.println("Device not found.");
                return -1;
            }
        }
        return presence;
    }
}
