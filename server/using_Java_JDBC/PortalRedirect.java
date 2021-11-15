package HSPServer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PortalRedirect
 */
@WebServlet("/PortalRedirect")
public class PortalRedirect extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public PortalRedirect() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie c[]= request.getCookies();
		
		if(c[0].getValue()==" "){
			response.sendRedirect("portal.html");
			return;
		}
		response.sendRedirect("portal.html");
	}
}
