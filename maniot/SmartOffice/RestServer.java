import java.io.*;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class RestServer implements Runnable{
      
    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    Server jettyServer = new Server(8080);
     
    public RestServer(){
        context.setContextPath("/");
        jettyServer.setHandler(context);
        ServletHolder jerseyServlet = context.addServlet(
        org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);
        Communicator C = new Communicator();
        
	// Tells the Jersey Servlet which REST service/class to load.
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", Communicator.class.getCanonicalName());
    }

    public void run(){
        try {
            try{
                jettyServer.start();
            }catch(Exception e){};
            
	    try{
                jettyServer.join();
            }catch(InterruptedException ie){}
        } finally {
            jettyServer.destroy();
        }
    }
}
