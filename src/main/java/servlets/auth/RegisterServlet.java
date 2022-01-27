package servlets.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.CustomerUtil;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// render page
		RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
		rd.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (new CustomerUtil().checkIfEmailExists(request.getParameter("emailAddress"))) {
			request.setAttribute("error", "That email address is already in use!");
			
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
			
			return;
		}
		
		if (!(request.getParameter("password").equals(request.getParameter("confirmPassword")))) {
			request.setAttribute("error", "Your passwords do not match!");
			
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
			
			return;
		}
		
		if (request.getParameter("password").length() < 8) {
			request.setAttribute("error", "Your password must be at least 8 characters long!");
			
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			rd.forward(request, response);
			
			return;
		}
		
		new CustomerUtil().insertCustomer(request.getParameter("firstName"), request.getParameter("lastName"), request.getParameter("emailAddress"), request.getParameter("password"));
		new LoginServlet().doPost(request, response);
	}

}