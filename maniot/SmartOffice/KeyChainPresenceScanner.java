import java.io.*;
import java.util.*;

// Configuration file
import java.io.IOException;

// Text
import java.text.DateFormat;
import java.text.SimpleDateFormat;

// Other
import java.lang.Math ;

public class KeyChainPresenceScanner extends PresenceScanner {

    private SensorsValues sensorsData;

    public KeyChainPresenceScanner(){
	sensorsData = new SensorsValues();
    }

    // Try to reach a device since the server network by using a ping command
    public int scanPresenceKeyChain (String keyChainName){
	sensorsData.requestSensorsValues();
        int wasFound = 0;
        if (1 == sensorsData.getPresenceByDevice(keyChainName))
            wasFound = 1;
        else
            wasFound = 0;
        return wasFound;
    }
 
    /*public static void delay2(int time){
        try {
            Thread.sleep(time);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }*/

 /*   // Uncomment just for testing the class
    public static void main(String[] args) throws Exception {
        KeyChainPresenceScanner p = new KeyChainPresenceScanner();

        while (true){
            delay2(1000);
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());

            int s = p.scanPresenceKeyChain();
            if (s==1)
                System.out.println("dentro");
            else
                System.out.println("fora");
        
            try (FileWriter file = new FileWriter("/home/gustavo/Desktop/MANIOT/JSON/data_log.txt", true)){
                if (1 == s) {
                    file.append(timeStamp + " Dentro\n");
                }
                else{
                    file.append(timeStamp + " Fora\n");
                }
            }
            catch (IOException e) {}
        }
    }*/

}

