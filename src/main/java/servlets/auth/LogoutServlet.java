package servlets.auth;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ciaran Baker / Liam McClelland
 *
 */

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check if customer exists in the session
		if (request.getSession().getAttribute("customer") != null) {
			// remove customer from session
			request.getSession().removeAttribute("customer");
			// remove basket from session
			request.getSession().removeAttribute("basket");
		}
		
		response.sendRedirect("/");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}