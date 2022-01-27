package servlets.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Basket;
import models.BasketItem;
import models.Customer;
import utils.BasketUtil;
import utils.CustomerUtil;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// render page
		RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// attempt to get a customer object from email address and password. if returns null, then email address and password are incorrect. if returns customer object, then they are correct.
		Customer customer = new CustomerUtil().getCustomer(request.getParameter("emailAddress"), request.getParameter("password"));
		
		if (customer == null) { // invalid email address or password
			// set error message to be displayed
			request.setAttribute("error", "Your email address or password is incorrect!");
			
			// render login page
			RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
			rd.forward(request, response);
		} else {
			// load customers' basket
			if (request.getSession().getAttribute("basket") != null) {
				Basket basket = (Basket) request.getSession().getAttribute("basket");
				
				for (BasketItem basketItem : basket.getItems()) {
					new BasketUtil().insertBasketItem(customer.getId(), basketItem);
				}
				
				basket = new Basket();
				
				for (BasketItem basketItem : new BasketUtil().getBasketItems(customer.getId())) {
					basket.addToBasket(basketItem);
				}
				
				request.getSession().setAttribute("basket", basket);
			}
			
			// add the customer object to the session
			request.getSession().setAttribute("customer", customer);
			
			// check if current page exists in the session
			if (request.getSession().getAttribute("currentPage") != null && !(request.getSession().getAttribute("currentPage").equals(""))) {
				// redirect the user to the last page they were on
				response.sendRedirect((String) request.getSession().getAttribute("currentPage"));
			} else {
				// redirect the user to the home page
				response.sendRedirect("/");
			}
		}
	}

}