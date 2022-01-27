package models;

import java.util.ArrayList;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class Basket {
	
	// list of the items in the basket
	private ArrayList<BasketItem> items;
	// total deposit cost for every item in the basket combined
	private double totalDeposit;
	// total price for every item in the basket combined
	private double totalPrice;
	// total delivery cost for every item in the basket combined
	private double totalDelivery;
	
	// constructor
	public Basket() {
		// set defaults
		this.items = new ArrayList<BasketItem>();
		this.totalDeposit = 0;
		this.totalPrice = 0;
		this.totalDelivery = 0;
	}
	
	// add item to basket
	public void addToBasket(BasketItem basketItem) {
		// add item to items list
		items.add(basketItem);
		// re-calculate totals as items list has changed
		calculateTotals();
	}
	
	// remove item from basket
	public void removeFromBasket(BasketItem basketItem) {
		// remove BasketItem object from items ArrayList
		items.remove(basketItem);
		// re-calculate totals as items list has changed
		calculateTotals();
	}
	
	// update item in basket
	public void updateItemInBasket(int index, BasketItem updatedBasketItem) {
		// create a copy of the items list as it will be cleared
		ArrayList<BasketItem> tempItems = new ArrayList<BasketItem>(items);
		
		// clear the items list
		items.clear();
		
		// loop through every item in the temp items list
		for (BasketItem basketItem : tempItems) {
			// check if the current item is the same as the one passed
			if (basketItem == tempItems.get(index)) {
				// if it is then add the passed (updated) item to the items list
				items.add(updatedBasketItem);
			} else {
				// if it isn't then add the existing value back to the list
				items.add(basketItem);
			}
		}
		
		// clear the temp items list as it is no longer needed
		tempItems.clear();
		
		// re-calculate totals as items list has changed
		calculateTotals();
	}
	
	// get item from items list using index of item
	public BasketItem getItem(int index) {
		return items.get(index);
	}
	
	// get all items from items list
	public ArrayList<BasketItem> getItems() {
		return items;
	}
	
	// get size of items list
	public int getSize() {
		return items.size();
	}
	
	// get total deposit
	public double getTotalDeposit() {
		return totalDeposit;
	}
	
	// get total price
	public double getTotalPrice() {
		return totalPrice;
	}
	
	// get total delivery
	public double getTotalDelivery() {
		return totalDelivery;
	}
	
	// calculate totals of every cost
	private void calculateTotals() {
		// set each total back to 0 so that it can be (re)calculated
		this.totalDeposit = 0;
		this.totalPrice = 0;
		this.totalDelivery = 0;
		
		// loop through every item in the items list
		for (BasketItem basketItem : items) {
			// add the items total deposit cost to the overall total deposit cost
			this.totalDeposit += basketItem.getTotalDeposit();
			// add the items total price to the overall total price
			this.totalPrice += basketItem.getTotalPrice();
			// add the items total delivery cost to the overall total delivery cost
			this.totalDelivery += basketItem.getTotalDelivery();
		}
	}

}
