package models;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

public class Customer {
	
	// customer's id from the db
	private int id;
	// customer's first name
	private String firstName;
	// customer's last name
	private String lastName;
	// customer's email address
	private String emailAddress;
	// customer's phone number
	private String phoneNumber;
	// customer's address line 1
	private String addressLine1;
	// customer's address line 2
	private String addressLine2;
	// customer's city
	private String city;
	// customer's post code
	private String postCode;
	// is customer admin?
	private int isAdmin;
	
	// constructor
	public Customer(int id, String firstName, String lastName, String emailAddress, String phoneNumber, String addressLine1, String addressLine2, String city, String postCode, int isAdmin) {
		// set local fields to passed params
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.postCode = postCode;
		this.isAdmin = isAdmin;
	}
	
	// get customer's id
	public int getId() {
		return id;
	}
	
	// get customer's first name
	public String getFirstName() {
		return firstName;
	}
	
	// get customer's last name
	public String getLastName() {
		return lastName;
	}
	
	// get customer's email address
	public String getEmailAddress() {
		return emailAddress;
	}
	
	// get customer's phone number
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	// set customer's phone number
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	// get customer's address line 1
	public String getAddressLine1() {
		return addressLine1;
	}
	
	// set customer's address line 1
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	
	// get customer's address line 2
	public String getAddressLine2() {
		return addressLine2;
	}
	
	// set customer's address line 2
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	
	// get customer's city
	public String getCity() {
		return city;
	}
	
	// set customer's city
	public void setCity(String city) {
		this.city = city;
	}
	
	// get customer's post code
	public String getPostCode() {
		return postCode;
	}
	
	// set customer's post code
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	// get whether or not the 'customer' is an admin
	public int getIsAdmin() {
		return isAdmin;
	}
}