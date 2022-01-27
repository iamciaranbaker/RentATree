package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Customer;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class CustomerUtil {
	
	// get customer from id
	public Customer getCustomer(int id) {
		// initalize a new customer object which will be returned
		Customer customer = null;
		
		// try/catch statement to catch any SQL errors
		try {
			// initalize a new connectio object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// set the SQL statement which will be executed, ?'s will be replaced with the passed data
			// get customer from customers table depending on id passed
			String sql = "SELECT * FROM customers WHERE id = ?";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			// set the 1st ? to the customer's id
			ps.setInt(1, id);
			
			// get the results from the query
			ResultSet rs = ps.executeQuery();
			
			// check if the query returned anything
			if (rs.next()) {
				// set the customer object to a new customer object using the returned data from the query
				customer = new Customer(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email_address"), rs.getString("phone_number"), rs.getString("address_line_1"), rs.getString("address_line_2"), rs.getString("city"), rs.getString("post_code"), rs.getInt("is_admin"));
			}
			
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
		
		// return the customer object which is either null or valid
		return customer;
	}
	
	// get customer from email address and password
	public Customer getCustomer(String emailAddress, String password) {
		// initalize a new customer object which will be returned
		Customer customer = null;
		
		// try/catch statement to catch any SQL errors
		try {
			// initalize a new connectio object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// set the SQL statement which will be executed, ?'s will be replaced with the passed data
			// get customer from customers table depending on email address and password passed
			String sql = "SELECT * FROM customers WHERE email_address = ? AND password = ?";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			// set the 1st ? to the customer's email address
			ps.setString(1, emailAddress);
			// set the 2nd ? to the customer's password
			ps.setString(2, password);
			
			// get the results from the query
			ResultSet rs = ps.executeQuery();
			
			// check if the query returned anything
			if (rs.next()) {
				// set the customer object to a new customer object using the returned data from the query
				customer = new Customer(rs.getInt("id"), rs.getString("first_name"), rs.getString("last_name"), rs.getString("email_address"), rs.getString("phone_number"), rs.getString("address_line_1"), rs.getString("address_line_2"), rs.getString("city"), rs.getString("post_code"), rs.getInt("is_admin"));
			}
			
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
		
		// return the customer object which is either null or valid
		return customer;
	}
	
	// return true/false of whether or not a customer exists with a specified email address
	public boolean checkIfEmailExists(String emailAddress) {
		// initalize a local boolean to return after the query
		boolean exists = false;
		
		// try/catch statement to catch any SQL errors
		try {
			// initalize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// set the SQL statement which will be executed, ?'s will be replaced with the passed data
			String sql = "SELECT id FROM customers WHERE email_address = ?";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			// set the 1st ? to the email address given
			ps.setString(1, emailAddress);
			
			// get the results from the query
			ResultSet rs = ps.executeQuery();
			
			// check if the query returned anything
			if (rs.next()) {
				// set the local boolean to true as a customer exists with the given email address
				exists = true;
			}
			
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
		
		// return the local boolean
		return exists;
	}
	
	// insert a new customer into the database
	public void insertCustomer(String firstName, String lastName, String emailAddress, String password) {
		// try/catch statement to catch any SQL errors
		try {
			// initalize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// set the SQL statement which will be executed, ?'s will be replaced with the passed data
			String sql = "INSERT INTO customers (first_name, last_name, email_address, password) VALUES (?, ?, ?, ?)";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			// set the 1st ? to the customer's first name
			ps.setString(1, firstName);
			// set the 2nd ? to the customer's last name
			ps.setString(2, lastName);
			// set the 3rd ? to the customer's email address
			ps.setString(3, emailAddress);
			// set the 4th ? to the customer's password
			ps.setString(4, password);
			// execute the insert query
			ps.executeUpdate();
			
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
	}
	
	// update a custoemr in the database
	public void updateCustomer(Customer customer) {
		// try/catch statement to catch any SQL errors
		try {
			// initalize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// set the SQL statement which will be executed, ?'s will be replaced with the passed data
			// insert a new customer into the database
			String sql = "UPDATE customers SET phone_number = ?, address_line_1 = ?, address_line_2 = ?, city = ?, post_code = ? WHERE id = ?";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			// set the 1st ? to the customer's phone number
			ps.setString(1, customer.getPhoneNumber());
			// set the 2nd ? to the customer's address line 1
			ps.setString(2, customer.getAddressLine1());
			// set the 3rd ? to the customer's address line 2
			ps.setString(3, customer.getAddressLine2());
			// set the 4th ? to the customer's city
			ps.setString(4, customer.getCity());
			// set the 5th ? to the customer's post code
			ps.setString(5, customer.getPostCode());
			// set the 6th ? to the customer's id
			ps.setInt(6, customer.getId());
			// execute the update query
			ps.executeUpdate();
	
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
	}

}