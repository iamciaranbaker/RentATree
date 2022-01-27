package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import models.Customer;
import utils.CustomerUtil;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

class CustomerTest {

	@Test
	void constructorTest() {
		// initialize a new customer object
		Customer customer = new Customer(1, "John", "Smith", "johnsmith@gmail.com", "07123456789", "10 Sandy Lane", "", "London", "NW1 1AA", 0);
		// check if the customer id matches
		assertEquals(1, customer.getId());
		// check if the customer first name matches
		assertEquals("John", customer.getFirstName());
		// check if the customer last name matches
		assertEquals("Smith", customer.getLastName());
		// check if the customer email address matches
		assertEquals("johnsmith@gmail.com", customer.getEmailAddress());
		// check if the customer phone number matches
		assertEquals("07123456789", customer.getPhoneNumber());
		// check if the customer address line 1 matches
		assertEquals("10 Sandy Lane", customer.getAddressLine1());
		// check if the customer address line 2 matches
		assertEquals("", customer.getAddressLine2());
		// check if the customer city matches
		assertEquals("London", customer.getCity());
		// check if the customer post code matches
		assertEquals("NW1 1AA", customer.getPostCode());
		// check if the customer admin status matches
		assertEquals(0, customer.getIsAdmin());
	}
	
	@Test
	void getFromDatabaseTest() {
		// initialize a new customer object from the database
		Customer customer = new CustomerUtil().getCustomer(1);
		// check if the customer id matches
		assertEquals(1, customer.getId());
		// check if the customer first name matches
		assertEquals("John", customer.getFirstName());
		// check if the customer last name matches
		assertEquals("Smith", customer.getLastName());
		// check if the customer email address matches
		assertEquals("johnsmith@gmail.com", customer.getEmailAddress());
		// check if the customer phone number matches
		assertEquals("07123456789", customer.getPhoneNumber());
		// check if the customer address line 1 matches
		assertEquals("10 Sandy Lane", customer.getAddressLine1());
		// check if the customer address line 2 matches
		assertEquals("", customer.getAddressLine2());
		// check if the customer city matches
		assertEquals("London", customer.getCity());
		// check if the customer post code matches
		assertEquals("NW1 1AA", customer.getPostCode());
		// check if the customer admin status matches
		assertEquals(0, customer.getIsAdmin());
	}

}
