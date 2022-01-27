package servlets.admin;

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
import utils.OrderUtil;
import utils.OrderUtil.SortType;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

@WebServlet("/admin/orders")
public class AdminOrdersServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("customer") == null || ((Customer) request.getSession().getAttribute("customer")).getIsAdmin() == 0) {
			response.sendRedirect("/");
			return;
		}
		
		if (request.getSession().getAttribute("currentPage") != null && !(request.getSession().getAttribute("currentPage").equals("/admin/orders"))) {
			request.getSession().removeAttribute("sortByAdmin");
			request.getSession().removeAttribute("itemsPerPageAdmin");
		}
		
		request.getSession().setAttribute("currentPage", "/admin/orders");
		
		int sortBy = 0;
		if (request.getSession().getAttribute("sortByAdmin") != null) sortBy = (int) request.getSession().getAttribute("sortByAdmin");
		if (request.getParameter("sortBy") != null) {
			sortBy = Integer.valueOf(request.getParameter("sortBy"));
			request.getSession().setAttribute("sortByAdmin", sortBy);
		}
		
		SortType sortType;
		
		if (sortBy == 1) {
			sortType = SortType.DATE_PLACED_OLDEST_TO_NEWEST;
		} else if (sortBy == 2) {
			sortType = SortType.STATUS_START_TO_FINISH;
		} else if (sortBy == 3) {
			sortType = SortType.STATUS_FINISH_TO_START;
		} else if (sortBy == 4) {
			sortType = SortType.CUSTOMER_A_TO_Z;
		} else if (sortBy == 5) {
			sortType = SortType.CUSTOMER_Z_TO_A;
		} else if (sortBy == 6) {
			sortType = SortType.TOTAL_HIGHEST_TO_LOWEST;
		} else if (sortBy == 7) {
			sortType = SortType.TOTAL_LOWEST_TO_HIGHEST;
		} else {
			sortType = SortType.DATE_PLACED_NEWEST_TO_OLDEST;
		}
		
		int itemsPerPage = 5;
		if (request.getSession().getAttribute("itemsPerPageAdmin") != null) sortBy = (int) request.getSession().getAttribute("itemsPerPageAdmin");
		if (request.getParameter("itemsPerPage") != null) {
			itemsPerPage = Integer.valueOf(request.getParameter("itemsPerPage"));
			request.getSession().setAttribute("itemsPerPageAdmin", itemsPerPage);
		}
		
		ArrayList<Order> results = new OrderUtil().getOrders(sortType, itemsPerPage);
		ArrayList<Order> resultsSelected = new ArrayList<Order>();
		
		for (Order order : results) {
			if (request.getParameter("order-" + order.getId()) != null) {
				resultsSelected.add(order);
			}
		}
		
		if (request.getParameter("markAs") != null) {
			for (Order order : resultsSelected) {
				if (request.getParameter("markAs").equals("delivered")) {
					order.setStatus("DELIVERED");
				} else if (request.getParameter("markAs").equals("returned")) {
					order.setStatus("RETURNED");
				} else if (request.getParameter("markAs").equals("missed")) {
					order.setStatus("MISSED");
				}
				
				new OrderUtil().updateOrder(order);
			}
		}
		
		if (request.getParameter("delete") != null) {
			new OrderUtil().deleteOrder(Integer.valueOf(request.getParameter("delete")));
		}
		
		results = new OrderUtil().getOrders(sortType, itemsPerPage);
		
		request.setAttribute("results", results);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/orders.jsp");
		rd.forward(request, response);
	}
	
}