import java.sql.*;
import java.sql.CallableStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
	
	
public class Storage {	
	private static ConnectMySQL instance = ConnectMySQL.getInstance();
	private static Statement stmt = null;
	
	public static int insertThing(String name, String location) {
		if(name == "" || name == null) {
			System.out.println("Error: Failed to insert object in table 'things'.");
			System.out.println("       Column 'name' cannot contain an empty set.");
			return 0;
		}
		try {
			stmt = instance.conn.createStatement();
			String query = "INSERT INTO things (name, location) VALUES ('" + name + "', '" + location + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'things'.");
				return 1;
			}
				
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'things'.");
			if(name.length() > 50){
				System.out.println("       Data too long for column 'name'.");
			}
			else if(location.length() > 50){
				System.out.println("       Data too long for column 'location'.");
			}
			else
				e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'things'.");
			e.printStackTrace();
		}
		return 0;
	}
	

	public static int insertUser(String name, String login, String password) {
		if(name == "" || name == null) {
			System.out.println("Error: Failed to insert object in table 'users'.");
			System.out.println("       Column 'name' cannot contain an empty set.");
			return 0;
		}
		if(login == "" || login == null) {
			System.out.println("Error: Failed to insert object in table 'users'.");
			System.out.println("       Column 'login' cannot contain an empty set.");
			return 0;
		}
		try {
			stmt = instance.conn.createStatement();
			String query = "INSERT INTO users (name, login, password) VALUES ('" + name + "', '" + login + "', '" + password + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'users'.");
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'users'.");
			if(name.length() > 50){
				System.out.println("       Data too long for column 'name'.");
			}
			else if(login.length() > 20){
				System.out.println("       Data too long for column 'login'.");
			}
			else if(password.length() > 20){
				System.out.println("       Data too long for column 'password'.");
			}
			else
				e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'users'.");
			e.printStackTrace();
		}
		return 0;		
	}
	
	public static int insertAplication(String name, String description) {
		if(name == "" || name == null) {
			System.out.println("Error: Failed to insert object in table 'aplications'.");
			System.out.println("       Column 'name' cannot contain an empty set.");
			return 0;
		}
		try {
			stmt = instance.conn.createStatement();
			String query = "INSERT INTO aplications (name, description) VALUES ('" + name + "', '" + description + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'aplications'.");
				return 1;
			}
				
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'aplications'.");
			if(name.length() > 20){
				System.out.println("       Data too long for column 'name'.");
			}
			else
				e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'aplications'.");
			e.printStackTrace();
		}
		return 0;	
	}
	
	public static int insertResource(int idThing, String name, int idAdm) throws SQLException {	
		if(!Storage.verifyForeignKey("things", "idThing", idThing)) {
			System.out.println("Error: Failed to insert object in table 'resource'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		if(name == "") {	
			System.out.println("Error: Failed to insert object in table 'resource'.");
			System.out.println("       Column 'name' cannot contain an empty set.");
			return 0;
		}
		try {
			stmt = instance.conn.createStatement();
			String query = "INSERT INTO resources (idThing, name, idAdm) VALUES ('" + idThing + "', '" + name + "', '" + idAdm + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'resources'.");
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'resources'.");

			if(name.length() > 50){
				System.out.println("       Data too long for column 'name'.");		
			}
			else
				e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'resources'.");
			e.printStackTrace();
		}
		return 0;		
	}
	
	
	public static int insertPermission(int idResource, int idAplication, int idUser, String permission) throws SQLException {
		if(!Storage.verifyForeignKey("resource", "idResource", idResource)) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		if(!Storage.verifyForeignKey("aplications", "idAplication", idAplication)) {	
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		if(!Storage.verifyForeignKey("users", "idUser", idUser)) {	
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		try {
			stmt = instance.conn.createStatement();
			String query = "INSERT INTO permissions (idResource, idAplication, idUser, permission) "
						+ "VALUES ('" + idResource + "', '" + idAplication + "', '" + idUser + "', '" + permission + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'permissions'.");
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			if(permission.length() > 3){
				System.out.println("       Data too long for column 'permissions'.");
			}
			else
				e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			e.printStackTrace();
		}
		return 0;		
	}
	
	public static int insertData(int idResource, String value) throws SQLException {
		if(!Storage.verifyForeignKey("resources", "idResource", idResource)) {
			System.out.println("Error: Failed to insert object in table 'data'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		// Get current date and time
		Date date = new Date();
        // Convert the Date type parameter to a String, in the accepted MySQL format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = dateFormat.format(date);
		try {
			stmt = instance.conn.createStatement();
			String query = "INSERT INTO data (idResource, value, time) " + "VALUES (" + idResource + ", '" + value + "', '" + datetime + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'data'.");
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

			e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			e.printStackTrace();
		}
		return 0;		
	}

	public static int selectData(int idResource) throws SQLException {
		if(!Storage.verifyForeignKey("resources", "idResource", idResource)) {
			System.out.println("Error: Failed to select object in table 'data'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		// Get current date and time
		Date date = new Date();
        // Convert the Date type parameter to a String, in the accepted MySQL format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = dateFormat.format(date);
		try {
			stmt = instance.conn.createStatement();
			String query = "SELECT * FROM data WHERE idResource=" + idResource + " AND time >= DATE_SUB(now(), INTERVAL 5 SECOND)";
			ResultSet rs = stmt.executeQuery(query);
      
			// iterate through the java resultset
			while (rs.next()){
				String v = rs.getString("value");
				//Caso seja a consulta sobre tags (id==5), somente retorne um caso tenha, -1 contrario
				if(idResource==5){
				return 1;
				}
				return Integer.parseInt(v);
			}

		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'data'.");

			e.printStackTrace();
		
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			e.printStackTrace();
		}

		return -1;		
	}

	public static ResultSet selectData2(int idResource, int rowsQuantity, int timeInterval) throws SQLException {
		if(!Storage.verifyForeignKey("resources", "idResource", idResource)) {
			System.out.println("Error: Failed to select object in table 'data'.");
			System.out.println("       A foreign key constraint fails.");
			return null;
		}
		Date date = new Date();
                // Convert the Date type parameter to a String, in the accepted MySQL format
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = dateFormat.format(date);
		try {
			stmt = instance.conn.createStatement();	
			String query = "SELECT idResource, value, time FROM maniot.data d WHERE idResource="+idResource+" AND time >= DATE_SUB(now(), INTERVAL "+timeInterval+" SECOND) ORDER BY time DESC LIMIT "+rowsQuantity;
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("Error: Failed to select object from table 'data'.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			e.printStackTrace();
		}
		return null;		
	}

	public static int insertScheduling(int idThing, int idResource, int idUser, int action, String newValue, int date, int time, int count, String others) throws SQLException {
		if(!Storage.verifyForeignKey("things", "idThing", idThing)) {
			System.out.println("Error: Failed to insert object in table 'schduling'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		if(!Storage.verifyForeignKey("resources", "idResource", idResource)) {
			System.out.println("Error: Failed to insert object in table 'schduling'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}
		if(!Storage.verifyForeignKey("users", "idUser", idUser)) {
			System.out.println("Error: Failed to insert object in table 'schduling'.");
			System.out.println("       A foreign key constraint fails.");
			return 0;
		}	
		try {
			stmt = instance.conn.createStatement();
			String query = "INSERT INTO scheduling (idThing, idResource, idUser, action, newValue, date, time, count, others) "
						+ "VALUES (" + idThing + ", " + idResource + ", " + idUser + ", " + action + ", '" + newValue + "', "
						+ date + ", " + time +  ", " + count + ", '" + others + "')";
			int update = stmt.executeUpdate(query);
			if(update == 1) {
				System.out.println("Object was successfully added in table 'schduling'.");
				return 1;
			}
		} catch (SQLException e) {
			System.out.println("Error: Failed to insert object in table 'scheduling'.");
				e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'scheduling'.");
			e.printStackTrace();
		}
		return 0;		
	}

	
	public static ResultSet selectEnvironment(int rowsQuantity, int timeInterval) throws SQLException {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = dateFormat.format(date);
		try {
			String query = "SELECT temperature, pressure, humidity, noise, airQuality, luminosity1, luminosity2, time "+ 
			               "FROM environment WHERE time >= DATE_SUB(now(), INTERVAL "+timeInterval+" SECOND) "+
						   "ORDER BY time DESC LIMIT "+rowsQuantity;
			stmt = instance.conn.createStatement();	
			return stmt.executeQuery(query);
		} catch (SQLException e) {
			System.out.println("Error: Failed to select object from table 'data'.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Error: Failed to insert object in table 'permissions'.");
			e.printStackTrace();
		}
		return null;		
	}


	public static ResultSet executeQuery(String query) {
		try {
			stmt = instance.conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.out.println("error: failed to create a connection object.");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("other error:");
			e.printStackTrace();
		}
		return null;
	}
	

	// timeInterval=20
		public static int addLearning(int newAirOn, int newTemperature, int newSpeed, 
		                              int newSwingOn, int newLightOn, float newWeatherRating, float newLuminosityRating, float newNoiseRating, 
					      int isUserOrSystem, int timeInterval) {
			int response = -1;
			try {
	 			String query = "{call addLearning(?,?,?,?,?,?,?,?,?,?)}";
				System.out.println("LEARNING 0");
				CallableStatement stmt = instance.conn.prepareCall(query);
				System.out.println("LEARNING 1");
				stmt.setInt(1,newAirOn); 
				stmt.setInt(2,newTemperature); 
				stmt.setInt(3,newSpeed);
				stmt.setInt(4,newSwingOn); 
				stmt.setInt(5,newLightOn); 
				stmt.setFloat(6,newWeatherRating);
				stmt.setFloat(7,newLuminosityRating);
				stmt.setFloat(8,newNoiseRating); 
				stmt.setInt(9,isUserOrSystem);
				stmt.setInt(10,timeInterval);
				stmt.executeQuery();
				response = 1;
				System.out.println("LEARNING 2");
			} catch (SQLException e) {
				System.out.println("error: failed to create a connection object.");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("another error:");
				e.printStackTrace();
			}
			return response;
		}
		
		public static int addEnvironment(int newTemperature, float newPressure, int newHumidity, int newNoise, int newAirQuality, int timeWindow) {
			int response = -1;
			try{
				
				String query = "{call addEnvironment(?,?,?,?,?,?)}";
				//System.out.println("ENVIRONMENT 0");
				CallableStatement stmt = instance.conn.prepareCall(query);
				//System.out.println("ENVIRONMENT 1");
				stmt.setInt(1,newTemperature); 
				stmt.setFloat(2,newPressure); 
				stmt.setInt(3,newHumidity);
				stmt.setInt(4,newNoise); 
				stmt.setInt(5,newAirQuality); 
				stmt.setFloat(6,timeWindow);
				//System.out.println("ENVIRONMENT 2");
				stmt.executeQuery();
				response = 1;
				//System.out.println("ENVIRONMENT 3");
			} catch (SQLException e) {
				System.out.println("error: failed to create a connection object.");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("another error:");
				e.printStackTrace();
			}
				return response;
		}

	public static boolean verifyForeignKey(String table, String field, int idKey) throws SQLException {
		String query = "SELECT " + field + " FROM " + table + " WHERE " + field + " = " + idKey;
		ResultSet rs = null;
		rs = executeQuery(query);
		if(!rs.next())
			return false;
		return true;
	}
}

