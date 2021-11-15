package HSPServer;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/Login")
public class Login extends HttpServlet{
	private String dburl ="jdbc:mysql://localhost:3307/hsp";
	private String dbuname="user";
	private String dbpassword="password";
	private String dbDriver="com.mysql.jdbc.Driver";
	
	public void loadDriver(String dbDriver) {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		Connection con=null;
		
		try {
			con=DriverManager.getConnection(dburl,dbuname,dbpassword);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		return con;
	}
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		
		loadDriver(dbDriver);
		 
		Connection con=getConnection();
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		
		String loginid=req.getParameter("loginid");
		String password=req.getParameter("password");

		int count=0;
		
		String query="select * from hsp.login where login_id='"+loginid+"' and password='"+password+"'";
		
		try {
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			pw.println(loginid+" "+password+"<br>");
			while(rs.next()) {
				count++;
				pw.println(rs.getString("login_id")+" ");
				pw.println(rs.getString("password")+"<br>");
			}
			if(count==0){
				pw.println("Invalid Credentials...");
				pw.close();
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			pw.println("Error");
			return;
		}
		
		Cookie c1= new Cookie("login_id",loginid);
		
		res.addCookie(c1);
		
		res.sendRedirect("PortalRedirect");
		
	}
	
}