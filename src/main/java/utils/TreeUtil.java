package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.Tree;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class TreeUtil {
	
	public enum SortType {
		PRICE_HIGHEST_TO_LOWEST,
		PRICE_LOWEST_TO_HIGHEST,
		HEIGHT_TALLEST_TO_SHORTEST,
		HEIGHT_SHORTEST_TO_TALLEST
	}
	
	public Tree getTree(int id) {
		Tree tree = null;
		
		try {
			Connection connection = new DBUtil().getConnection();
			
			String sql = "SELECT * FROM trees WHERE id = ?";
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				tree = new Tree(rs.getInt("id"), rs.getString("type"), rs.getString("material"), rs.getInt("height"), rs.getString("description"), rs.getString("supplier"), rs.getDouble("price_per_day"), rs.getInt("stock_level"), rs.getInt("deposit_percentage"));
			}
			
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return tree;
	}
	
	public ArrayList<Tree> getTrees(SortType sortType) {
		return getTrees(sortType, "", 0, 0);
	}
	
	public ArrayList<Tree> getTrees(SortType sortType, int amount) {
		return getTrees(sortType, "", amount, 0);
	}
	
	public ArrayList<Tree> getTrees(SortType sortType, String searchQuery) {
		return getTrees(sortType, searchQuery, 0, 0);
	}
	
	public ArrayList<Tree> getTrees(SortType sortType, String searchQuery, int amount, int page) {
		ArrayList<Tree> results = new ArrayList<Tree>();
		
		String orderBy = "";
		
		if (sortType == SortType.PRICE_LOWEST_TO_HIGHEST) {
			orderBy = " ORDER BY price_per_day ASC";
		} else if (sortType == SortType.HEIGHT_TALLEST_TO_SHORTEST) {
			orderBy = " ORDER BY height DESC";
		} else if (sortType == SortType.HEIGHT_SHORTEST_TO_TALLEST) {
			orderBy = " ORDER BY height ASC";
		} else {
			orderBy = " ORDER BY price_per_day DESC";
		}
		
		String limit = "";
		
		if (amount != 0) {
			limit = " LIMIT " + amount;
		}
		
		// try/catch statement to catch any SQL errors
		try {
			// initialize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			// setup a prepared statement with no initalization
			PreparedStatement ps;
			
			// no search query provided, get all results
			if (searchQuery.equals("")) {
				// set the SQL statement which will be executed, ?'s will be replaced with the passed data
				// select all trees from trees table
				// append orderBy and limit at the end
				String sql = "SELECT * FROM trees" + orderBy + limit;
				
				// setup a prepared statement using the SQL statement above
				ps = connection.prepareStatement(sql);
			// search query provided, get specific results
			} else {
				// split the search query into a String array
				String[] searchQuerySplit = searchQuery.split(" ");
				
				// set the SQL statement which will be executed
				// will be appended to in the below loop
				String sql = "";
				
				// loop from 0 to length of split search query string
				for (int i = 0; i < searchQuerySplit.length; i++) {
					// add to SQL statement
					sql += "SELECT * FROM trees WHERE ? IN (type, material, height, supplier, price_per_day)";
					
					// check if this isn't the last iteration
					if (i != searchQuerySplit.length - 1) {
						// add to the SQL string to join future iterations
						sql += " UNION ";
					}
				}
				
				// append orderBy and limit at the end of the SQL query
				sql += orderBy + limit;
				
				// setup a prepared statement using the SQL statement above
				ps = connection.prepareStatement(sql);
				
				// loop from 0 to length of split search query string, again
				for (int i = 0; i < searchQuerySplit.length; i++) {
					// get the next word in the search query
					String word = searchQuerySplit[i];
					// remove any instances of 'cm'
					word = word.replaceAll("cm", "");
					
					// add the search query parameters to the prepared statement
					ps.setString(i + 1, word);
				}
			}
			
			// get the results from the query
			ResultSet rs = ps.executeQuery();
			
			// loop through every row returned
			while (rs.next()) {
				// initialize a new tree object using the values from the db
				Tree tree = new Tree(rs.getInt("id"), rs.getString("type"), rs.getString("material"), rs.getInt("height"), rs.getString("description"), rs.getString("supplier"), rs.getDouble("price_per_day"), rs.getInt("stock_level"), rs.getInt("deposit_percentage"));
				
				// add the tree to the results list
				results.add(tree);
			}
			
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
		
		// return the results list
		return results;
	}
	
	public int insertTree(Tree tree) {
		// initalize a variable which will be set to the id of the inserted tree
		int insertedTreeId = 0;
		
		// try/catch statement to catch any SQL errors
		try {
			// initialize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			String sql = "INSERT INTO trees (type, material, height, description, supplier, price_per_day, stock_level, deposit_percentage) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			String[] columnToReturn = {"id"};
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql, columnToReturn);
			ps.setString(1, tree.getType());
			ps.setString(2, tree.getMaterial());
			ps.setInt(3, tree.getHeight());
			ps.setString(4, tree.getDescription());
			ps.setString(5, tree.getSupplier());
			ps.setDouble(6, tree.getPricePerDay());
			ps.setInt(7, tree.getStockLevel());
			ps.setDouble(8, tree.getDepositPercentage());
			ps.executeUpdate();
			
			ResultSet rs = ps.getGeneratedKeys();
			
			if (rs.next()) {
				insertedTreeId = rs.getInt(1);
			}
			
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
		
		return insertedTreeId;
	}
	
	public void updateTree(Tree tree) {
		// try/catch statement to catch any SQL errors
		try {
			// initialize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			String sql = "UPDATE trees SET type = ?, material = ?, description = ?, supplier = ?, height = ?, deposit_percentage = ?, price_per_day = ?, stock_level = ? WHERE id = ?";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, tree.getType());
			ps.setString(2, tree.getMaterial());
			ps.setString(3, tree.getDescription());
			ps.setString(4, tree.getSupplier());
			ps.setInt(5, tree.getHeight());
			ps.setDouble(6, tree.getDepositPercentage());
			ps.setDouble(7, tree.getPricePerDay());
			ps.setInt(8, tree.getStockLevel());
			ps.setInt(9, tree.getId());
			ps.executeUpdate();
			
			// close the connection
			connection.close();
		// catch any SQL exceptions
		} catch (SQLException e) {
			// print the error in the console
			e.printStackTrace();
		}
	}
	
	public void deleteTree(int id) {
		// try/catch statement to catch any SQL errors
		try {
			// initialize a new connection object from the DBUtil class
			Connection connection = new DBUtil().getConnection();
			
			String sql = "DELETE FROM trees WHERE id = ?";
			
			// setup a prepared statement using the SQL statement above
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			sql = "DELETE FROM basket_items WHERE tree_id = ?";
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
			
			sql = "DELETE FROM order_items WHERE tree_id = ?";
			
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
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