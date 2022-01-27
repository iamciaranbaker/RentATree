package servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Customer;
import models.Order;
import utils.BasketUtil;
import utils.OrderUtil;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("currentPage", "/orders");
		
		new BasketUtil().setupBasket(request.getSession());
		
		if (request.getSession().getAttribute("customer") == null) {
			response.sendRedirect("/login");
			return;
		}
		
		Customer customer = (Customer) request.getSession().getAttribute("customer");
		
		ArrayList<Order> results = new OrderUtil().getOrders(customer.getId());
		
		request.setAttribute("results", results);
		
		RequestDispatcher rd = request.getRequestDispatcher("orders.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
