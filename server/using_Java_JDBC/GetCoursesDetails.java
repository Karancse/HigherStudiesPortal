package HSPServer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/GetCoursesDetails")
public class GetCoursesDetails extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
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
	
	private String generateJSONData(String yearRange) {
		loadDriver(dbDriver);
		 
		Connection con=getConnection();
		//PrintWriter pw = res.getWriter();
		
		//String yearRange=req.getParameter("yearRange");

		String query;
		
		StringBuffer output=new StringBuffer("{\"tableContent\":\"");
		
		if(yearRange=="notSelected") {
			query="select * from hsp.course";
		}
		else {
			String startYear= yearRange.substring(0,4);
			String endYear = yearRange.substring(4,8);
			query="select * from hsp.login where admission_year>='"+startYear+"' and admission_year<='"+endYear+"'";
		}
		
		try {
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			output.append("<table><tbody>");
			output.append("<tr>");
			output.append("<th>S.No.</th>");
			output.append("<th>Course Name</th>");
			output.append("<th>No. of Seats</th>");
			output.append("<th>Admitted Students</th>");
			output.append("<th>Vacant Seats</th>");
			output.append("<th>Fee Structure</th>");
			output.append("</tr>");
			while(rs.next()) {
				output.append("<tr>");
                output.append("<td>"+rs.getString("course_id")+"</td>");
                output.append("<td>"+rs.getString("courseName")+"</td>");
                output.append("<td>"+rs.getString("no_of_seats")+"</td>");
                output.append("<td>"+rs.getString("Admitted_Students")+"</td>");
                output.append("<td>"+rs.getString("vacant_seats")+"</td>");
                output.append("<td>"+rs.getString("fee_structure")+"</td>");
                output.append("</tr>");				
			}
			output.append("</tbody></table>");
			output.append("\"}");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "Error";
		}
		
		
		return output.toString();
		
		//pw.println("Login Success...!");
		//pw.close();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
			Cookie c[]= req.getCookies();
			
			if(c[0].getValue()==" "){
				res.sendRedirect("LogInRedirect");
				
				return;
			}

			res.setContentType("text/html");
			
			PrintWriter pw = res.getWriter();
			
			String yearRange = req.getParameter("yearRange");
					
			String tableContent = generateJSONData(yearRange);
			
			pw.write(tableContent);
	}
	
	
}
