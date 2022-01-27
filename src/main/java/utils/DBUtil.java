package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class DBUtil {
	
	// the database url
	private String url;
	// the database username
	private String username;
	// the database password
	private String password;
	
	public DBUtil() {
		// set the database url
		// format: jdbc:mysql://<host>:<port>/<database>
		this.url = "jdbc:mysql://localhost:3306/project";
		// set the database username
		this.username = "project_user";
		// set the database password
		this.password = "password";
	}
	
	// get a new connection
	public Connection getConnection() {
		// initalize a connection object set to null
		Connection connection = null;
		
		// try/catch statement to catch any SQL errors
		try {
			// get a new connection from the DriverManager class
			connection = DriverManager.getConnection(url, username, password);
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
		
		// return the connection object
		return connection;
	}

}