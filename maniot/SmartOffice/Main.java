import java.io.IOException;
import java.util.Iterator;
import java.util.Arrays;

// Sql
import java.sql.ResultSet;
import java.sql.SQLException;

import net.tinyos.message.*;
import net.tinyos.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main{

public static void main(String[] args) throws Exception {
	//ShadeControl SC = new ShadeControl();
	//SC.Test();
	
        RestServer R = new RestServer();
	Orchestrator O = new Orchestrator();
	SerialRxTx S = new SerialRxTx();

	new Thread(R).start();
        new Thread(O).start();
	new Thread(S).start();
	
	
     	try{
           String[] params=new String[3];
           params[0]="-comm";
           params[1]="serial@/dev/ttyUSB1:iris";
           params[2]="-no-gui";
           SerialForwarder sf = new SerialForwarder(params);
           Thread t2=new Thread(sf);
           t2.start();
      	}
      	catch(Exception e){
      	}

      	try{
           TimeUnit.SECONDS.sleep(10);
     	 }
      	catch(Exception e){
      	}
      	Oscilloscope me = new Oscilloscope();
      	me.run();
	
   }//main end
}//class end
