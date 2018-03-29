import java.sql.*;

// Configuration file
import java.util.*;
import java.util.Scanner;
import java.util.Scanner.*;
import java.io.FileReader;
import java.io.FileReader.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.*;

public class ConnectMySQL  {
    public Connection conn;
    private static ConnectMySQL instance;

    // Initializes singleton: connect to MySQL database
    // *********INSTANCE IT BACK TO PRIVATE*******************
    private ConnectMySQL() {
        // Load configuration file to allocate values to the project variables    
        String driverName = "", serverIP = "", serverPort = "", database = "", url="", username = "", password = "";
        File configFile = new File("ConnectMySQL.properties");

        // Allocate initial values to variables
        try {
            FileReader reader = new FileReader(configFile);
            Properties prop = new Properties();
            prop.load(reader);

            // JDBC driver name and database URL
            //driverName = "com.mysql.jdbc.Driver".toString();
            driverName= prop.getProperty("DRIVER_NAME");
            serverIP = prop.getProperty("SERVER_IP");
            serverPort = prop.getProperty("SERVER_PORT");
            database = prop.getProperty("DATABASE");
            url = "jdbc:mysql://"+serverIP+":"+serverPort+"/"+database+"?autoReconnect=true&useSSL=false";
            System.out.println(url);
            // Database credentials
            username = prop.getProperty("USERNAME");
            password = prop.getProperty("PASSWORD");
            reader.close();
        }  
        catch (FileNotFoundException ex) { // file does not exist
            System.out.println(ex.getMessage());		 
        } 
        catch (IOException ex) { // I/O error
            System.out.println(ex.getMessage());		 
        }

        // Try to do the database connection
        try {
            // Register JDBC driver
            Class.forName(driverName);
            // Open a connection
            conn = DriverManager.getConnection(url, username, password);
            // Driver not found
        } catch (ClassNotFoundException cnfex) {
            System.out.println(cnfex.getMessage());
            // Not able to connect to the database
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            // Other error
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Return a MySQL connection object
   
   public static ConnectMySQL getInstance() {
        if (instance == null)
            instance = new ConnectMySQL();
        return instance;
    }
    

    // Close the database connection
    public static int closeConnection(Connection connection) {
        try {
            connection.close();
            return 1;
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
            return 0;
        }
    }
}
