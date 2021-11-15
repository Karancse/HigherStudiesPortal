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


@WebServlet("/GetStudentDetails")
public class GetStudentDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String dburl ="jdbc:mysql://localhost:3307/hsp";
	private String dbuname="user";
	private String dbpassword="password";
	private String dbDriver="com.mysql.jdbc.Driver";
	
	public void loadDriver(String dbDriver) {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Connection getConnection()
	{
		Connection con=null;
		
		try {
			con=DriverManager.getConnection(dburl,dbuname,dbpassword);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
		return con;
	}
	
	private String generateJSONData(String course,String subBranch, String year) {
		loadDriver(dbDriver);
		 
		Connection con=getConnection();
		String query;
		
		StringBuffer output=new StringBuffer("{\"tableContent\":\"");
		
		Boolean previousConditions = false;
		
		
		String courseCondition="", subBranchCondition="", yearCondition="";
		
		if(course!="notSelected") {
			courseCondition=" course='"+course+"'";
			previousConditions = true;
		}
		
		if(subBranch!="notSelected") {
			if(previousConditions)
				subBranchCondition=" AND";
			subBranchCondition+=" subBranch='"+subBranch+"'";
			previousConditions = true;
		}
		
		if(year!="notSelected") {
			if(previousConditions)
				yearCondition=" AND";
			yearCondition+=" subBranch='"+year+"'";
			previousConditions = true;	
		}
		
		if(!previousConditions) {
			query="select * from hsp.student";
		}
		else {
			query="select * from hsp.student where"+courseCondition+subBranchCondition+yearCondition;
		}
		
		try {
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			output.append("<table><tbody>");
			output.append("<tr>");
			output.append("<th>s_no</th>");
			output.append("<th>year</th>");
			output.append("<th>student_roll_no</th>");
			output.append("<th>Student_name</th>");
			output.append("<th>initial</th>");
			output.append("<th>Branch</th>");
			output.append("<th>DOB</th>");
			output.append("<th>Caste</th>");
			output.append("<th>Age</th>");
			output.append("<th>Course</th>");
			output.append("</tr>");
			while(rs.next()) {
				output.append("<tr>");
                output.append("<td>"+rs.getString("s_no")+"</td>");
                output.append("<td>"+rs.getString("year")+"</td>");
                output.append("<td>"+rs.getString("student_roll_no")+"</td>");
                output.append("<td>"+rs.getString("student_name")+"</td>");
                output.append("<td>"+rs.getString("intitial")+"</td>");
                output.append("<td>"+rs.getString("branch")+"</td>");
                output.append("<td>"+rs.getString("dob")+"</td>");
                output.append("<td>"+rs.getString("caste")+"</td>");
                output.append("<td>"+rs.getString("age")+"</td>");
                output.append("<td>"+rs.getString("course")+"</td>");
                output.append("</tr>");				
			}
			output.append("</tbody></table>");
			output.append("\"}");
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error";
		}
		
		
		return output.toString();
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
			Cookie c[]= req.getCookies();
			
			if(c[0].getValue()==" "){
				res.sendRedirect("LogInRedirect");
				
				return;
			}

			res.setContentType("text/html");
			
			PrintWriter pw = res.getWriter();
			
			
			String course = req.getParameter("course");
			String subBranch = req.getParameter("subBranch");
			String year = req.getParameter("year");
			
			String tableContent = generateJSONData(course, subBranch, year);
			
			pw.write(tableContent);
	}
}
