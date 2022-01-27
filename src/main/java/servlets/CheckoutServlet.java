package servlets;

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
import models.Order;
import models.OrderItem;
import models.Tree;
import utils.BasketUtil;
import utils.CustomerUtil;
import utils.OrderUtil;
import utils.TreeUtil;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("currentPage", "/checkout");
		
		new BasketUtil().setupBasket(request.getSession());
		
		if (request.getSession().getAttribute("customer") == null) {
			response.sendRedirect("/login");
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("checkout.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("currentPage", "/checkout");
		
		Basket basket = new BasketUtil().setupBasket(request.getSession());
		
		if (request.getSession().getAttribute("customer") == null) {
			response.sendRedirect("/login");
			return;
		}
		
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		
		if (request.getParameter("phoneNumber") != null) {
			customer.setPhoneNumber(request.getParameter("phoneNumber"));
		}
		if (request.getParameter("addressLine1") != null) {
			customer.setAddressLine1(request.getParameter("addressLine1"));
		}
		if (request.getParameter("addressLine2") != null) {
			customer.setAddressLine2(request.getParameter("addressLine2"));
		}
		if (request.getParameter("city") != null) {
			customer.setCity(request.getParameter("city"));
		}
		if (request.getParameter("postCode") != null) {
			customer.setPostCode(request.getParameter("postCode"));
		}
		
		new CustomerUtil().updateCustomer(customer);
		
		request.setAttribute("customer", customer);
		
		Order order = new Order(customer.getId(), 0, basket.getTotalPrice(), basket.getTotalDeposit(), basket.getTotalDelivery());
		
		int orderId = new OrderUtil().insertOrder(order);
		
		for (BasketItem basketItem : basket.getItems()) {
			OrderItem orderItem = new OrderItem(orderId, basketItem.getTreeId(), basketItem.getRentalStartDate(), basketItem.getRentalEndDate(), basketItem.getDeliveryOut(), basketItem.getDeliveryIn(), basketItem.getQuantity());
		
			Tree tree = new TreeUtil().getTree(basketItem.getTreeId());
			tree.setStockLevel(tree.getStockLevel() - 1);
			
			new TreeUtil().updateTree(tree);
			
			new OrderUtil().insertOrderItem(orderItem);
			new BasketUtil().deleteBasketItem(customer.getId(), basketItem);
		}
		
		// remove basket from session as user has placed an order
		request.getSession().removeAttribute("basket");
		// set the current order id into the session so the order confirmation page knows which order to display
		request.getSession().setAttribute("currentOrderID", orderId);
		// redirect user to the order confirmation page
		response.sendRedirect("/order-confirmation");
	}
	
}
