package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Order;
import utils.BasketUtil;
import utils.OrderUtil;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

@WebServlet("/order-confirmation")
public class OrderConfirmationServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new BasketUtil().setupBasket(request.getSession());
		
		if (request.getSession().getAttribute("currentOrderID") == null || request.getSession().getAttribute("customer") == null) {
			response.sendRedirect("/");
			return;
		}
		
		Order order = new OrderUtil().getOrder((int) request.getSession().getAttribute("currentOrderID"));
		
		request.setAttribute("order", order);
		
		RequestDispatcher rd = request.getRequestDispatcher("order_confirmation.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}