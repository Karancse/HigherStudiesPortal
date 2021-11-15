package HSPServer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AboutRedirect
 */
@WebServlet("/AboutRedirect")
public class AboutRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie c[]= req.getCookies();
		
		if(c[0].getValue()==" "){
			response.sendRedirect("about.html");
			return;
		}
		response.sendRedirect("aboutAuthenticated.html");
	}
}
