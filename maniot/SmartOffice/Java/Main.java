import net.tinyos.message.*;
import net.tinyos.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Main{


	/*
//REST SERVER MAIN TESTING (OK)
public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");


        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
             org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        // Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", Communicator.class.getCanonicalName());

        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            jettyServer.destroy();
        }
    }
*/	


 // PRESENCE MODULE MAIN TESTING (OK)
    public static void main(String[] args) throws Exception {
        PresenceScanner p = new WIFIPresenceScanner();
        while (true)
        {
            int s = p.defineUserPresence();
            if(s == p.NOT_INSIDE)
                System.out.println("IS NOT IN the room.");
            else if(s == p.RECENTLY_INSIDE)
                System.out.println("ENTER INTO the room.");
            else if(s == p.LONG_TIME_INSIDE)
                System.out.println("STAYS IN the room.");
        }
    }




/*
//SERIAL CONNECTION TO ARDUINO MAIN TESTING(OK)
public static void main(String[] args) throws Exception {
		//Abre thread para conexao serial
		SerialRxTx serialLine = new SerialRxTx();
		serialLine.initialize();
		Thread t = new Thread() {
			public void run() {
			}
		};
		t.start();
		System.out.println("Connected to serial Port");
	}
*/

/*
//AIR CONDITIONING CONTROLL MAIN TESTING(OK)
public static void main (String[] args){
	AirControl Air = new AirControl();
	Air.SetAll(147,"on","22","off","fraco");
	Air.GetParams(147);
}
*/

/*
    //SERIAL FORWARDING MAIN TESTING (OK)
    //OSCILOSCOPE MAIN TESTING (OK)
    public static void main(String[] args) {
      
      //Abre Thread para o Serial Forwarder
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

      //Abre Thread para o Osciloscope
      Oscilloscope me = new Oscilloscope();
      me.run(); 
    }
*/
}
