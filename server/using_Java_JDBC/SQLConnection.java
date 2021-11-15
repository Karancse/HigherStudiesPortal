package HSPServer;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {
	public static Connection initializeDatabase() throws SQLException, ClassNotFoundException {
		String url = "jdbc:mysql://localhost:3307/hsp";
		String uname = "user";
		String pass = "password";
		Class.forName("com.mysql.cj.jdbc.Driver");
		
		Connection con = DriverManager.getConnection(url, uname, pass);
		return con;
	}
}
