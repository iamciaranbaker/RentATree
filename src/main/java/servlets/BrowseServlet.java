package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Basket;
import models.BasketItem;
import models.Customer;
import models.Tree;
import utils.BasketUtil;
import utils.DBUtil;
import utils.TreeUtil;
import utils.BasketUtil.DeliveryIn;
import utils.BasketUtil.DeliveryOut;
import utils.TreeUtil.SortType;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

@WebServlet("/browse")
public class BrowseServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("currentPage", "/browse");
		
		new BasketUtil().setupBasket(request.getSession());
		
		request.getSession().removeAttribute("searchQuery");
		request.getSession().removeAttribute("sortBy");
		
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("currentPage", "/browse");
		
		Basket basket = new BasketUtil().setupBasket(request.getSession());
		
		int sortBy = 0;
		if (request.getSession().getAttribute("sortBy") != null) sortBy = (int) request.getSession().getAttribute("sortBy");
		if (request.getParameter("sortBy") != null) {
			sortBy = Integer.valueOf(request.getParameter("sortBy"));
			request.getSession().setAttribute("sortBy", sortBy);
		}
		
		SortType sortType;
		
		if (sortBy == 1) {
			sortType = SortType.PRICE_LOWEST_TO_HIGHEST;
		} else if (sortBy == 2) {
			sortType = SortType.HEIGHT_TALLEST_TO_SHORTEST;
		} else if (sortBy == 3) {
			sortType = SortType.HEIGHT_SHORTEST_TO_TALLEST;
		} else {
			sortType = SortType.PRICE_HIGHEST_TO_LOWEST;
		}
		
		String searchQuery = "";
		if (request.getSession().getAttribute("searchQuery") != null) searchQuery = (String) request.getSession().getAttribute("searchQuery");
		if (request.getParameter("searchQuery") != null) {
			searchQuery = request.getParameter("searchQuery");
			request.getSession().setAttribute("searchQuery", searchQuery);
		}
		
		ArrayList<Tree> results;
		
		if (searchQuery.equals("")) { // no search query provided, get all results
			results = new TreeUtil().getTrees(sortType);
		} else { // search query provided, get specific results
			results = new TreeUtil().getTrees(sortType, searchQuery);
		}
		
		request.setAttribute("results", results);
		
		if (request.getParameter("quantity") != null && request.getSession().getAttribute("currentTreeID") != null) {
			BasketItem basketItem = new BasketItem((int) request.getSession().getAttribute("currentTreeID"), request.getParameter("rentalStartDate"), request.getParameter("rentalEndDate"), DeliveryOut.STANDARD_DELIVERY, DeliveryIn.STANDARD_COLLECTION, Integer.valueOf(request.getParameter("quantity")));
			
			basket.addToBasket(basketItem);
			
			// user is logged in, so add the basket item to the db too
			if (request.getSession().getAttribute("customer") != null) {
				Customer customer = (Customer) request.getSession().getAttribute("customer");
				
				new BasketUtil().insertBasketItem(customer.getId(), basketItem);
			}
			
			request.getSession().setAttribute("basket", basket);
			request.getSession().removeAttribute("currentTreeID");
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("browse.jsp");
		rd.forward(request, response);
	}

}