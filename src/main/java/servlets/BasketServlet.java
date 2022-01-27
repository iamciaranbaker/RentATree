package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

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
import utils.BasketUtil.DeliveryIn;
import utils.BasketUtil.DeliveryOut;
import utils.DBUtil;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

@WebServlet("/basket")
public class BasketServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("currentPage", "/basket");
		
		new BasketUtil().setupBasket(request.getSession());
		
		RequestDispatcher rd = request.getRequestDispatcher("basket.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("currentPage", "/basket");
		
		Basket basket = new BasketUtil().setupBasket(request.getSession());
		
		// check if user pressed update button on one of their items in their basket
		if (request.getParameter("update") != null) {
			// get the index of the item they want to update
			int index = Integer.valueOf(request.getParameter("update"));
			
			// get BasketItem object from basket using index
			BasketItem basketItem = basket.getItem(index);
			
			// update the required fields in the BasketItem object
			basketItem.setQuantity(Integer.valueOf(request.getParameter("quantity-" + index)));
			basketItem.setRentalStartDate(request.getParameter("rentalStartDate-" + index));
			basketItem.setRentalEndDate(request.getParameter("rentalEndDate-" + index));
			
			// check the state of the delivery dropdowns
			if (Integer.valueOf(request.getParameter("deliveryOut-" + index)) == 1) {
				basketItem.setDeliveryOut(DeliveryOut.COLLECT_FROM_WAREHOUSE);
			} else if (Integer.valueOf(request.getParameter("deliveryOut-" + index)) == 2) {
				basketItem.setDeliveryOut(DeliveryOut.AM_DELIVERY);
			} else if (Integer.valueOf(request.getParameter("deliveryOut-" + index)) == 3) {
				basketItem.setDeliveryOut(DeliveryOut.PM_DELIVERY);
			} else {
				basketItem.setDeliveryOut(DeliveryOut.STANDARD_DELIVERY);
			}
			
			if (Integer.valueOf(request.getParameter("deliveryIn-" + index)) == 1) {
				basketItem.setDeliveryIn(DeliveryIn.RETURN_TO_WAREHOUSE);
			} else if (Integer.valueOf(request.getParameter("deliveryIn-" + index)) == 2) {
				basketItem.setDeliveryIn(DeliveryIn.AM_COLLECTION);
			} else if (Integer.valueOf(request.getParameter("deliveryIn-" + index)) == 3) {
				basketItem.setDeliveryIn(DeliveryIn.PM_COLLECTION);
			} else {
				basketItem.setDeliveryIn(DeliveryIn.STANDARD_COLLECTION);
			}
			
			// user is logged in, so update the basket item in the db too
			if (request.getSession().getAttribute("customer") != null) {
				// get Customer object from session
				Customer customer = (Customer) request.getSession().getAttribute("customer");
				// update basket item in db
				new BasketUtil().updateBasketItem(customer.getId(), basketItem);
			}
			
			// update local Basket object
			basket.updateItemInBasket(index, basketItem);
			
			// update the Basket object in the session
			request.getSession().setAttribute("basket", basket);
		}
		
		if (request.getParameter("remove") != null) {
			int index = Integer.valueOf(request.getParameter("remove"));
			
			BasketItem basketItem = basket.getItem(index);
			
			// user is logged in, so remove the basket item from the db too
			if (request.getSession().getAttribute("customer") != null) {
				Customer customer = (Customer) request.getSession().getAttribute("customer");
				
				new BasketUtil().deleteBasketItem(customer.getId(), basketItem);
			}
			
			basket.removeFromBasket(basketItem);
			
			request.getSession().setAttribute("basket", basket);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("basket.jsp");
		rd.forward(request, response);
	}

}