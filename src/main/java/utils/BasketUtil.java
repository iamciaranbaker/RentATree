package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import models.Basket;
import models.BasketItem;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class BasketUtil {
	
	// enum for outbound delivery type
	public enum DeliveryOut {
		// STANDARD_DELIVERY : Standard Delivery (Free)
		// COLLECT_FROM_WAREHOUSE : I'll Collect From The Warehouse (Free)
		// AM_DELIVERY : AM Delivery (+ £3.99)
		// PM_DELIVERY : PM Delivery (+ £3.99)
		STANDARD_DELIVERY, COLLECT_FROM_WAREHOUSE, AM_DELIVERY, PM_DELIVERY
	}
	
	// enum for inbound delivery type
	public enum DeliveryIn {
		// STANDARD_COLLECTION : Standard Collection (Free)
		// RETURN_TO_WAREHOUSE : I'll Return To The Warehouse (Free)
		// AM_COLLECTION : AM Collection (+ £3.99)
		// PM_COLLECTION : PM Collection (+ £3.99)
		STANDARD_COLLECTION, RETURN_TO_WAREHOUSE, AM_COLLECTION, PM_COLLECTION
	}
	
	// check if basket exists in session, if not then create one
	// returns basket to be used
	public Basket setupBasket(HttpSession session) {
		// check to see if basket exists in session
		if (session.getAttribute("basket") == null) {
			// if it doesn't, then create one and add it
			session.setAttribute("basket", new Basket());
		}
		
		// return basket from session
		// (can't return null as we have just ensured one exists)
		return (Basket) session.getAttribute("basket");
	}
	
	// get all items from a customer's basket from the db
	public ArrayList<BasketItem> getBasketItems(int customerId) {
		// initialize an empty list to add the items to
		ArrayList<BasketItem> basketItems = new ArrayList<BasketItem>();
		
		// try/catch statement to catch any SQL errors
		try {
			// initialize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// set the SQL statement which will be executed, ?'s will be replaced with the passed data
			// selects every field from the basket_items table which belongs to the customer
			// ordered by the date it was added in ascending order
			String sql = "SELECT * FROM basket_items WHERE customer_id = ? ORDER BY date_added ASC";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			// set the 1st ? to the customer's id
			ps.setInt(1, customerId);
			
			// get the results from the query
			ResultSet rs = ps.executeQuery();
			
			// local date time format used to parse the dates returned by the query
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			
			// loop through every row returned
			while (rs.next()) {
				// initalize a new basket item object using the data from the query
				BasketItem basketItem = new BasketItem(rs.getInt("tree_id"), rs.getString("rental_start_date"), rs.getString("rental_end_date"), DeliveryOut.valueOf(rs.getString("delivery_out").toUpperCase()), DeliveryIn.valueOf(rs.getString("delivery_in").toUpperCase()), rs.getInt("quantity"), LocalDateTime.parse(rs.getString("date_added"), dtf));
				
				// add the basket item to the new basket items list
				basketItems.add(basketItem);
			}
			
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
		
		// return the basket items list which is now populated from the db
		return basketItems;
	}
	
	// insert basket item into the db
	public void insertBasketItem(int customerId, BasketItem basketItem) {
		// try/catch statement to catch any SQL errors
		try {
			// initialize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// set the SQL statement which will be executed, ?'s will be replaced with the passed data
			// inserts a new row into the basket_items table
			String sql = "INSERT INTO basket_items (customer_id, tree_id, rental_start_date, rental_end_date, delivery_out, delivery_in, quantity, date_added) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			// set the 1st ? to the customer's id
			ps.setInt(1, customerId);
			// set the 2nd ? to the tree's id
			ps.setInt(2, basketItem.getTreeId());
			// set the 3rd ? to the rental start date
			ps.setString(3, basketItem.getRentalStartDate());
			// set the 4th ? to the rental end date
			ps.setString(4, basketItem.getRentalEndDate());
			// set the 5th ? to the outbound delivery type
			ps.setString(5, basketItem.getDeliveryOut().toString());
			// set the 6th ? to the inbound delivery type
			ps.setString(6, basketItem.getDeliveryIn().toString());
			// set the 7th ? to the item quantity
			ps.setInt(7, basketItem.getQuantity());
			// set the 8th ? to the date the item was added to the basket
			ps.setString(8, basketItem.getDateAdded().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			// execute the SQL insert query
			ps.executeUpdate();
			
			// close the connection, save resources
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
	}
	
	// update basket item in the db
	public void updateBasketItem(int customerId, BasketItem basketItem) {
		// try/catch statement to catch any SQL errors
		try {
			// initialize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// set the SQL statement which will be executed, ?'s will be replaced with the passed data
			// updates a row in the basket_items table
			String sql = "UPDATE basket_items SET quantity = ?, rental_start_date = ?, rental_end_date = ?, delivery_out = ?, delivery_in = ? WHERE customer_id = ? AND tree_id = ? AND date_added = ?";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			// set the 1st ? to the item quantity
			ps.setInt(1, basketItem.getQuantity());
			// set the 2nd ? to the rental start date
			ps.setString(2, basketItem.getRentalStartDate());
			// set the 3rd ? to the rental end date
			ps.setString(3, basketItem.getRentalEndDate());
			// set the 4th ? to the outbound delivery type
			ps.setString(4, basketItem.getDeliveryOut().toString());
			// set the 5th ? to the inbound delivery type
			ps.setString(5, basketItem.getDeliveryIn().toString());
			// set the 6th ? to the customer's id
			ps.setInt(6, customerId);
			// set the 7th ? to the tree's id
			ps.setInt(7, basketItem.getTreeId());
			// set the 8th ? to the date the item was added to the basket
			ps.setString(8, basketItem.getDateAdded().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			// execute the SQL update query
			ps.executeUpdate();
			
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
	}
	
	// delete basket item from the db
	public void deleteBasketItem(int customerId, BasketItem basketItem) {
		// try/catch statement to catch any SQL errors
		try {
			// initalize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// set the SQL statement which will be executed, ?'s will be replaced with the passed data
			// delete a row from the basket_items table based on the customer id, tree id and the date it was added to the basket
			String sql = "DELETE FROM basket_items WHERE customer_id = ? AND tree_id = ? AND date_added = ?";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			// set the 1st ? to the customer's id
			ps.setInt(1, customerId);
			// set the 2nd ? to the tree's id
			ps.setInt(2, basketItem.getTreeId());
			// set the 3rd ? to the date the item was added to the basket
			ps.setString(3, basketItem.getDateAdded().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			// execute the SQL delete query
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