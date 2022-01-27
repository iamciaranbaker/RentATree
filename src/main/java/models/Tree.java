package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import utils.DBUtil;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class Tree {
	
	// tree id
	private int id;
	// type of tree
	private String type;
	// material of tree
	private String material;
	// height of tree
	private int height;
	// tree description
	private String description;
	// name of supplier who supplies the tree
	private String supplier;
	// price per day of renting the tree
	private double pricePerDay;
	// stock level for the tree
	private int stockLevel;
	// deposit percentage
	private double depositPercentage;
	// actual deposit cost of the tree
	private double deposit;
	
	// empty constructor
	public Tree() {
		this(0, "", "", 0, "", "", 0, 0, 0);
	}
	
	public Tree(String type, String material, int height, String description, String supplier, double pricePerDay, int stockLevel, double depositPercentage) {
		this(0, type, material, height, description, supplier, pricePerDay, stockLevel, depositPercentage);
	}
	
	// constructor
	public Tree(int id, String type, String material, int height, String description, String supplier, double pricePerDay, int stockLevel, double depositPercentage) {
		// set local fields to passed params
		this.id = id;
		this.type = type;
		this.material = material;
		this.height = height;
		this.description = description;
		this.supplier = supplier;
		this.pricePerDay = pricePerDay;
		this.stockLevel = stockLevel;
		this.depositPercentage = depositPercentage;
		// divide local deposit percentage by 100 to get a decimal
		depositPercentage /= 100;
		// set deposit to price per day * decimal deposit percentage
		this.deposit = pricePerDay * depositPercentage;
	}

	// get tree id
	public int getId() {
		return id;
	}

	// get tree type
	public String getType() {
		return type;
	}
	
	// set tree type
	public void setType(String type) {
		this.type = type;
	}

	// get tree material
	public String getMaterial() {
		return material;
	}
	
	// set tree material
	public void setMaterial(String material) {
		this.material = material;
	}

	// get tree height
	public int getHeight() {
		return height;
	}
	
	// set tree height
	public void setHeight(int height) {
		this.height = height;
	}

	// get tree description
	public String getDescription() {
		return description;
	}
	
	// set tree description
	public void setDescription(String description) {
		this.description = description;
	}

	// get tree supplier name
	public String getSupplier() {
		return supplier;
	}
	
	// set tree supplier name
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	// get tree's price per day
	public double getPricePerDay() {
		return pricePerDay;
	}
	
	// set tree's price per day
	public void setPricePerDay(double pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	// get tree's stock level
	public int getStockLevel() {
		return stockLevel;
	}
	
	// set tree's stock level
	public void setStockLevel(int stockLevel) {
		this.stockLevel = stockLevel;
	}

	// get tree deposit percentage
	public double getDepositPercentage() {
		return depositPercentage;
	}
	
	// set tree deposit percentage
	public void setDepositPercentage(double depositPerentage) {
		this.depositPercentage = depositPerentage;
	}
	
	// get tree ACTUAL deposit (in pounds)
	public double getDeposit() {
		return deposit;
	}
	
	// get a formatted name of the tree
	public String getFormattedName() {
		return height + "cm " + material + " " + type + " Tree";
	}

}
