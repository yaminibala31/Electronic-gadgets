package connect;

import java.sql.Connection;
import java.sql.DriverManager;
public class Dbutil {
	
	public static Connection con;
	public static Connection getDBConn() throws Exception {
		con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/Electronic_gadjets", "root", "B@la31");
		
		System.out.println("connection established");
		return con;
		
		
	}

}
