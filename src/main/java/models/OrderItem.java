package models;

import utils.BasketUtil.DeliveryIn;
import utils.BasketUtil.DeliveryOut;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class OrderItem {
	
	// order id from the db
	private int orderId;
	// tree id
	private int treeId;
	// rental start date
	private String rentalStartDate;
	// rental end date
	private String rentalEndDate;
	// type of outbound delivery
	private DeliveryOut deliveryOut;
	// type of inbound delivery
	private DeliveryIn deliveryIn;
	// item quantity
	private int quantity;
	
	// constructor
	public OrderItem(int orderId, int treeId, String rentalStartDate, String rentalEndDate, DeliveryOut deliveryOut, DeliveryIn deliveryIn, int quantity) {
		// set local fields to passed params
		this.orderId = orderId;
		this.treeId = treeId;
		this.rentalStartDate = rentalStartDate;
		this.rentalEndDate = rentalEndDate;
		this.deliveryOut = deliveryOut;
		this.deliveryIn = deliveryIn;
		this.quantity = quantity;
	}

	// get order id
	public int getOrderId() {
		return orderId;
	}

	// get tree id
	public int getTreeId() {
		return treeId;
	}

	// get rental start date
	public String getRentalStartDate() {
		return rentalStartDate;
	}

	// get rental end date
	public String getRentalEndDate() {
		return rentalEndDate;
	}

	// get type of outbound delivery
	public DeliveryOut getDeliveryOut() {
		return deliveryOut;
	}

	// get type of inbound delivery
	public DeliveryIn getDeliveryIn() {
		return deliveryIn;
	}

	// get item quantity
	public int getQuantity() {
		return quantity;
	}

}
