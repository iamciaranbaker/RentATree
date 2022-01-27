package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import utils.TreeUtil;
import utils.BasketUtil.DeliveryIn;
import utils.BasketUtil.DeliveryOut;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class BasketItem {
	
	// tree id from the db
	private int treeId;
	// rental start date
	private String rentalStartDate;
	// rental end date
	private String rentalEndDate;
	// type of outbound delivery
	private DeliveryOut deliveryOut;
	// type of inbound delivery
	private DeliveryIn deliveryIn;
	// rental duration (in days)
	private int rentalDuration;
	// item quantity
	private int quantity;
	// date the item was added to the basket
	private LocalDateTime dateAdded;
	// total price of the item
	private double totalPrice;
	// total cost of the deposit
	private double totalDeposit;
	// total cost of the delivery
	private double totalDelivery;
	
	// constructor used for local intialization
	public BasketItem(int treeId, String rentalStartDate, String rentalEndDate, DeliveryOut deliveryOut, DeliveryIn deliveryIn, int quantity) {
		// pass passed params to db constructor to do the rest of the work
		this(treeId, rentalStartDate, rentalEndDate, deliveryOut, deliveryIn, quantity, LocalDateTime.now());
	}
	
	// constructor used for db
	public BasketItem(int treeId, String rentalStartDate, String rentalEndDate, DeliveryOut deliveryOut, DeliveryIn deliveryIn, int quantity, LocalDateTime dateAdded) {
		// set local fields to passed params
		this.treeId = treeId;
		this.rentalStartDate = rentalStartDate;
		this.rentalEndDate = rentalEndDate;
		this.deliveryOut = deliveryOut;
		this.deliveryIn = deliveryIn;
		this.rentalDuration = 0;
		// calculate rental duration in days
		calculateRentalDuration();
		this.quantity = quantity;
		this.dateAdded = dateAdded;
		this.totalPrice = 0;
		this.totalDeposit = 0;
		this.totalDelivery = 0;
		// calculate total costs
		calculateTotals();
	}

	// get tree id
	public int getTreeId() {
		return treeId;
	}

	// get rental start date
	public String getRentalStartDate() {
		return rentalStartDate;
	}
	
	// set rental start date
	public void setRentalStartDate(String rentalStartDate) {
		this.rentalStartDate = rentalStartDate;
		// re-calculate the rental duration as the dates have changed
		calculateRentalDuration();
		// re-calculate the total costs as the rental duration is different
		calculateTotals();
	}

	// get rental end date
	public String getRentalEndDate() {
		return rentalEndDate;
	}
	
	public void setRentalEndDate(String rentalEndDate) {
		this.rentalEndDate = rentalEndDate;
		// re-calculate the rental duration as the dates have changed
		calculateRentalDuration();
		// re-calculate the total costs as the rental duration is different
		calculateTotals();
	}
	
	// get outbound delivery type
	public DeliveryOut getDeliveryOut() {
		return deliveryOut;
	}
	
	// set outbound delivery type
	public void setDeliveryOut(DeliveryOut deliveryOut) {
		this.deliveryOut = deliveryOut;
		// re-calculate the total costs as the delivery type has changed
		calculateTotals();
	}
	
	// get inbound delivery type
	public DeliveryIn getDeliveryIn() {
		return deliveryIn;
	}
	
	// set inbound delivery type
	public void setDeliveryIn(DeliveryIn deliveryIn) {
		this.deliveryIn = deliveryIn;
		// re-calculate the total costs as the delivery type has changed
		calculateTotals();
	}
	
	// get rental duration (in days)
	public int getRentalDuration() {
		return rentalDuration;
	}

	// get item quantity
	public int getQuantity() {
		return quantity;
	}
	
	// set item quantity
	public void setQuantity(int quantity) {
		this.quantity = quantity;
		// re-calculate the total costs as the quantity has changed
		calculateTotals();
	}

	// get the date that the item was added to the basket
	public LocalDateTime getDateAdded() {
		return dateAdded;
	}
	
	// get the total price of the item(s)
	public double getTotalPrice() {
		return totalPrice;
	}
	
	// get the total deposit cost of the item(s)
	public double getTotalDeposit() {
		return totalDeposit;
	}
	
	// get the total delivery cost of the item(s)
	public double getTotalDelivery() {
		return totalDelivery;
	}
	
	// calculate the difference in days between the rental start date and the rental end date
	private void calculateRentalDuration() {
		// intialize date time format for use when calculating rental duration
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		// convert start and end date strings into LocalDate objects
		LocalDate startDate = LocalDate.parse(rentalStartDate, dtf);
		LocalDate endDate = LocalDate.parse(rentalEndDate, dtf);
		
		// calculate duration between start and end date
		int duration = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
		
		// set rental duration to calculated duration
		this.rentalDuration = duration;
	}
	
	// calculate the total costs
	private void calculateTotals() {
		// set the total price to the tree's price per day * rental duration * quantity
		this.totalPrice = (new TreeUtil().getTree(treeId).getPricePerDay() * rentalDuration) * quantity;
		// set the total deposit cost to the tree's deposit cost * rentalDuration * quantity
		this.totalDeposit = (new TreeUtil().getTree(treeId).getDeposit() * rentalDuration) * quantity;
		
		// set the total delivery cost to 0 so it can be calculated
		this.totalDelivery = 0;
		
		// if outbound delivery is AM or PM add £3.99
		if (deliveryOut == DeliveryOut.AM_DELIVERY || deliveryOut == DeliveryOut.PM_DELIVERY) {
			this.totalDelivery += 3.99;
		}
		
		// if inbound delivery is AM or PM add £3.99
		if (deliveryIn == DeliveryIn.AM_COLLECTION || deliveryIn == DeliveryIn.PM_COLLECTION) {
			this.totalDelivery += 3.99;
		}
	}

}
