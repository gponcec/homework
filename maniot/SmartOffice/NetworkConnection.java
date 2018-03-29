import java.io.IOException;
import java.net.InetAddress;
import java.util.*;
import java.util.*;


public class NetworkConnection {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String addrguillermo = "150.164.10.53"; // gui
		String addrguilherme = "150.164.10.80"; // guilherme
		String addrguilhermepequeno = "150.164.10.27"; // guilherme pequeno
        String addrjul = "150.164.10.113"; // julio
        String addr = addrguilhermepequeno;
        Integer timeOut = 2000;
        Integer delayTime = 1000;
        Integer counterFound = 0;
        Integer counterNotFound = 0;
        Integer wasFound = 0;
        Integer window = 4;
        //Vector lastConnections = new Vector(window);
        int lastConnections[] = new int[window]; 
        Arrays.fill(lastConnections, 0);

        Integer counterScan = 0;

        while (true) {
        	wasFound = scanAddresses(addr, timeOut, delayTime);    	
        	lastConnections[counterScan] = wasFound;
        	
        	counterScan++;
        	if(counterScan == window)
        	{
	        	if (validateConnections(lastConnections))
	        		System.out.println(addr + " not found");
	        	else
	        		System.out.println(addr + " found");
	        	
	        	for (int i = 0; i < lastConnections.length-1; i++) {                
	        		lastConnections[i] = lastConnections[i+1];
	        	}
	        	lastConnections[lastConnections.length-1] = 0;
	        	counterScan--;
	        	
        	}
        }  
	}

    public static Integer scanAddresses(String addr, Integer timeOut, Integer delayTime){
    	Integer wasFound = 0;
        try {
	        if (InetAddress.getByName(addr).isReachable(timeOut)){
	            //System.out.println(addr + " found");
	            wasFound = 1;
	        }
	        else{
	        	//System.out.println(addr + " not found");
	        	wasFound = 0;
	        }
	        delay(delayTime);	      

        } catch (IOException e) {
            e.printStackTrace();
        }
        return wasFound;
        
    }
    
    public static void delay(Integer time){
        try {
     	   Thread.sleep(time);
     } catch (Exception e) {
     	   e.printStackTrace();
     }
     }
    
    // Validate if all the connections are false
    public static Boolean validateConnections2(Vector connections) 
    {
        Iterator itr = connections.iterator();
        Integer counter = 0;
        while(itr.hasNext())
        {
            Boolean value = itr.next() instanceof Boolean;
          
            if (value)
            	return true;
        }
        return false;
    }
    

    // Validate if all the connections are false
    public static Boolean validateConnections(int[] connections) 
    {
    	for (int i = 0; i < connections.length; i++) {
    	    if (connections[i]==1)
    	    	return false;
    	}
    	return true;
    }
    
    
}


/*
while (true) {
	wasFound = scanAddresses(addr, timeOut, delayTime);
	if(wasFound==1)
		counterFound++;
	else
		counterNotFound++;
	
	counterScan++;
	if (counterScan%window==0) 
	{
		if (counterNotFound == window)
			System.out.println(addr + " not found");
		else
			System.out.println(addr + " found");
		
		counterFound = 0;
		counterNotFound = 0;
	}

}*/
