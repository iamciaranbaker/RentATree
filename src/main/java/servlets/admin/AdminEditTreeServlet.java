package servlets.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.BasketUtil;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

@WebServlet("/admin/edit-tree")
public class AdminEditTreeServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new BasketUtil().setupBasket(request.getSession());
		
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new BasketUtil().setupBasket(request.getSession());
		
		RequestDispatcher rd = request.getRequestDispatcher("/admin/modals/edit_tree.jsp");
		rd.forward(request, response);
	}

}
