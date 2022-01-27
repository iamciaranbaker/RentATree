package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import models.Order;
import models.OrderItem;
import utils.BasketUtil.DeliveryIn;
import utils.BasketUtil.DeliveryOut;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class OrderUtil {
	
	public enum SortType {
		DATE_PLACED_NEWEST_TO_OLDEST,
		DATE_PLACED_OLDEST_TO_NEWEST,
		STATUS_START_TO_FINISH,
		STATUS_FINISH_TO_START,
		CUSTOMER_A_TO_Z,
		CUSTOMER_Z_TO_A,
		TOTAL_HIGHEST_TO_LOWEST,
		TOTAL_LOWEST_TO_HIGHEST
	}
	
	public Order getOrder(int id) {
		Order order = null;
		
		try {
			Connection connection = new DBUtil().getConnection();
			
			String sql = "SELECT * FROM orders WHERE id = ?";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				order = new Order(rs.getInt("id"), rs.getInt("customer_id"), rs.getInt("promotion_id"), rs.getString("status"), rs.getString("date_ordered"), rs.getString("date_last_updated"), rs.getDouble("total_price"), rs.getDouble("total_deposit"), rs.getDouble("total_delivery"), rs.getDouble("total"));
			
				for (OrderItem orderItem : getOrderItems(id)) {
					order.addToOrder(orderItem);
				}
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return order;
	}
	
	public ArrayList<OrderItem> getOrderItems(int orderId) {
		ArrayList<OrderItem> orderItems = new ArrayList<OrderItem>();
		
		try {
			Connection connection = new DBUtil().getConnection();
			
			String sql = "SELECT * FROM order_items WHERE order_id = ?";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, orderId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				OrderItem orderItem = new OrderItem(rs.getInt("order_id"), rs.getInt("tree_id"), rs.getString("rental_start_date"), rs.getString("rental_end_date"), DeliveryOut.valueOf(rs.getString("delivery_out").toUpperCase()), DeliveryIn.valueOf(rs.getString("delivery_in").toUpperCase()), rs.getInt("quantity"));
				
				orderItems.add(orderItem);
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderItems;
	}
	
	// return array of orders
	public ArrayList<Order> getOrders(int customerId) {
		ArrayList<Order> orders = new ArrayList<Order>();
		
		try {
			Connection connection = new DBUtil().getConnection();
			
			String sql = "SELECT id FROM orders WHERE customer_id = ? ORDER BY date_ordered ASC";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, customerId);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Order order = getOrder(rs.getInt("id"));
				
				orders.add(order);
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orders;
	}
	
	public ArrayList<Order> getOrders(SortType sortType, int amount) {
		ArrayList<Order> orders = new ArrayList<Order>();
		
		String orderBy = "";
		
		if (sortType == SortType.DATE_PLACED_OLDEST_TO_NEWEST) {
			orderBy = " ORDER BY date_ordered ASC";
		} else if (sortType == SortType.STATUS_START_TO_FINISH) {
			orderBy = " ORDER BY status ASC";
		} else if (sortType == SortType.STATUS_FINISH_TO_START) {
			orderBy = " ORDER BY status DESC";
		} else if (sortType == SortType.CUSTOMER_A_TO_Z) {
			orderBy = " ORDER BY first_name ASC";
		} else if (sortType == SortType.CUSTOMER_Z_TO_A) {
			orderBy = " ORDER BY first_name DESC";
		} else if (sortType == SortType.TOTAL_HIGHEST_TO_LOWEST) {
			orderBy = " ORDER BY total DESC";
		} else if (sortType == SortType.TOTAL_LOWEST_TO_HIGHEST) {
			orderBy = " ORDER BY total ASC";
		} else {
			orderBy = " ORDER BY date_ordered DESC";
		}
		
		String limit = " LIMIT " + amount;
		
		// try/catch statement to catch any SQL errors
		try {
			// initialize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// set the SQL statement which will be executed, ?'s will be replaced with the passed data
			// selects every field from the orders table
			String sql = "SELECT * FROM orders INNER JOIN customers ON customer_id = customers.id" + orderBy + limit;
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			
			// get the results from the query
			ResultSet rs = ps.executeQuery();
			
			// loop through every row returned
			while (rs.next()) {
				Order order = new Order(rs.getInt("id"), rs.getInt("customer_id"), rs.getInt("promotion_id"), rs.getString("status"), rs.getString("date_ordered"), rs.getString("date_last_updated"), rs.getDouble("total_price"), rs.getDouble("total_deposit"), rs.getDouble("total_delivery"), rs.getDouble("total"));
			
				for (OrderItem orderItem : getOrderItems(rs.getInt("id"))) {
					order.addToOrder(orderItem);
				}
				
				orders.add(order);
			}
			
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
		
		return orders;
	}
	
	// return order ID
	public int insertOrder(Order order) {
		int orderId = 0;
		
		try {
			Connection connection = new DBUtil().getConnection();
			
			String sql = "INSERT INTO orders (customer_id, promotion_id, status, total_price, total_deposit, total_delivery, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			String[] columnsToGet = {"id"};
			PreparedStatement ps = connection.prepareStatement(sql, columnsToGet);
			ps.setInt(1,  order.getCustomerd());
			ps.setInt(2, order.getPromotionId());
			ps.setString(3, order.getStatus());
			ps.setDouble(4, order.getTotalPrice());
			ps.setDouble(5, order.getTotalDeposit());
			ps.setDouble(6, order.getTotalDelivery());
			ps.setDouble(7, order.getTotal());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
				orderId = rs.getInt(1);
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return orderId;
	}
	
	public void insertOrderItem(OrderItem orderItem) {
		try {
			Connection connection = new DBUtil().getConnection();
			
			String sql = "INSERT INTO order_items (order_id, tree_id, rental_start_date, rental_end_date, delivery_out, delivery_in, quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, orderItem.getOrderId());
			ps.setInt(2, orderItem.getTreeId());
			ps.setString(3, orderItem.getRentalStartDate());
			ps.setString(4, orderItem.getRentalEndDate());
			ps.setString(5, orderItem.getDeliveryOut().toString());
			ps.setString(6, orderItem.getDeliveryIn().toString());
			ps.setInt(7, orderItem.getQuantity());
			ps.executeUpdate();
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateOrder(Order order) {
		try {
			Connection connection = new DBUtil().getConnection();
			
			String sql = "UPDATE orders SET status = ?, date_last_updated = ? WHERE id = ?";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, order.getStatus());
			ps.setString(2, LocalDateTime.now().toString());
			ps.setInt(3, order.getId());
			ps.executeUpdate();
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteOrder(int id) {
		try {
			Connection connection = new DBUtil().getConnection();
			
			String sql = "DELETE FROM orders WHERE id = ?";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
