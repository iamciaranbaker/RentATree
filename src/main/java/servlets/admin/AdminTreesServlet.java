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
import models.Tree;
import utils.OrderUtil;
import utils.TreeUtil;
import utils.TreeUtil.SortType;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

@WebServlet("/admin/trees")
public class AdminTreesServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getSession().getAttribute("customer") == null || ((Customer) request.getSession().getAttribute("customer")).getIsAdmin() == 0) {
			response.sendRedirect("/");
			return;
		}
		
		if (request.getSession().getAttribute("currentPage") != null && !(request.getSession().getAttribute("currentPage").equals("/admin/trees"))) {
			request.getSession().removeAttribute("sortByAdmin");
			request.getSession().removeAttribute("itemsPerPageAdmin");
		}
		
		request.getSession().setAttribute("currentPage", "/admin/trees");
		
		int sortBy = 0;
		if (request.getSession().getAttribute("sortByAdmin") != null) sortBy = (int) request.getSession().getAttribute("sortByAdmin");
		if (request.getParameter("sortBy") != null) {
			sortBy = Integer.valueOf(request.getParameter("sortBy"));
			request.getSession().setAttribute("sortByAdmin", sortBy);
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
		
		int itemsPerPage = 5;
		if (request.getSession().getAttribute("itemsPerPageAdmin") != null) sortBy = (int) request.getSession().getAttribute("itemsPerPageAdmin");
		if (request.getParameter("itemsPerPage") != null) {
			itemsPerPage = Integer.valueOf(request.getParameter("itemsPerPage"));
			request.getSession().setAttribute("itemsPerPageAdmin", itemsPerPage);
		}
		
		if (request.getParameter("type") != null && request.getSession().getAttribute("currentTreeID") != null) {
			Tree tree = new TreeUtil().getTree((int) request.getSession().getAttribute("currentTreeID"));
			tree.setType(request.getParameter("type"));
			tree.setMaterial(request.getParameter("material"));
			tree.setDescription(request.getParameter("description"));
			tree.setSupplier(request.getParameter("supplier"));
			tree.setHeight(Integer.valueOf(request.getParameter("height")));
			tree.setDepositPercentage(Double.valueOf(request.getParameter("depositPercentage")));
			tree.setPricePerDay(Double.valueOf(request.getParameter("pricePerDay")));
			tree.setStockLevel(Integer.valueOf(request.getParameter("stockLevel")));
			
			new TreeUtil().updateTree(tree);
			
			request.getSession().removeAttribute("currentTreeID");
		}
		
		if (request.getParameter("addTree") != null && request.getParameter("type") != null) {
			Tree tree = new Tree(request.getParameter("type"), request.getParameter("material"), Integer.valueOf(request.getParameter("height")), request.getParameter("description"), request.getParameter("supplier"), Double.valueOf(request.getParameter("pricePerDay")), Integer.valueOf(request.getParameter("stockLevel")), Double.valueOf(request.getParameter("depositPercentage")));
			
			new TreeUtil().insertTree(tree);
		}
		
		if (request.getParameter("delete") != null) {
			new TreeUtil().deleteTree(Integer.valueOf(request.getParameter("delete")));
		}
		
		ArrayList<Tree> results = new TreeUtil().getTrees(sortType, itemsPerPage);
		
		request.setAttribute("results", results);
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/trees.jsp");
		rd.forward(request, response);
	}
	
}