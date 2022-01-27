package models;

import java.util.ArrayList;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class Order {
	
	// order id from the db
	private int id;
	// id from the customer associated with the order
	private int customerId;
	// id from the promotion used on the order
	private int promotionId;
	// status of the order
	private String status;
	// date the order was placed
	private String dateOrdered;
	// date the order was last updated in the system
	private String dateLastUpdated;
	// list of the items in the order
	private ArrayList<OrderItem> items;
	// total price of the order
	private double totalPrice;
	// total deposit cost of the order
	private double totalDeposit;
	// total delivery cost of the order
	private double totalDelivery;
	// total cost of the order (combined)
	private double total;
	
	// constructor used for local intialization
	public Order(int customerId, int promotionId, double totalPrice, double totalDeposit, double totalDelivery) {
		this(0, customerId, promotionId, "PROCESSING", "", "", totalPrice, totalDeposit, totalDelivery, totalPrice + totalDeposit + totalDelivery);
	}
	
	// constructor used for db
	public Order(int id, int customerId, int promotionId, String status, String dateOrdered, String dateLastUpdated, double totalPrice, double totalDeposit, double totalDelivery, double total) {
		this.id = id;
		this.customerId = customerId;
		this.promotionId = promotionId;
		this.status = status;
		this.dateOrdered = dateOrdered;
		this.dateLastUpdated = dateLastUpdated;
		this.items = new ArrayList<OrderItem>();
		this.totalPrice = totalPrice;
		this.totalDeposit = totalDeposit;
		this.totalDelivery = totalDelivery;
		this.total = total;
	}
	
	// add item to the order
	public void addToOrder(OrderItem orderItem) {
		// add item to items list
		this.items.add(orderItem);
	}

	// get order id
	public int getId() {
		return id;
	}

	// get customer id
	public int getCustomerd() {
		return customerId;
	}

	// get promotion id
	public int getPromotionId() {
		return promotionId;
	}

	// get order status
	public String getStatus() {
		return status;
	}

	// set order status
	public void setStatus(String status) {
		this.status = status;
	}

	// get the date the order was placed
	public String getDateOrdered() {
		return dateOrdered;
	}

	// get the date the order was last updated
	public String getDateLastUpdated() {
		return dateLastUpdated;
	}

	// set the date the order was last updated
	public void setDateLastUpdated(String dateLastUpdated) {
		this.dateLastUpdated = dateLastUpdated;
	}

	// get all items in the order
	public ArrayList<OrderItem> getItems() {
		return items;
	}
	
	// get total order price
	public double getTotalPrice() {
		return totalPrice;
	}
	
	// get total order deposit cost
	public double getTotalDeposit() {
		return totalDeposit;
	}
	
	// get total order delivery cost
	public double getTotalDelivery() {
		return totalDelivery;
	}
	
	// get total order cost
	public double getTotal() {
		return total;
	}

}