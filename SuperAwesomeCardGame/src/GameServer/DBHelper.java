package GameServer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBHelper {
	Connection conn;
	
	public DBHelper()
	{
		conn =  null;
	}
	
	public Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(
				"jdbc:mysql://mysql.cs.iastate.edu:3306/db30911", "u30911", "4rv2ucue78");
	}

	public ResultSet executeQuery(String query)
	{
		try {   
	         // Load the driver (registers itself)
	         Class.forName ("com.mysql.jdbc.Driver");
	         } 
	      catch (Exception E) {
	            System.err.println ("Unable to load driver.");
	            E.printStackTrace ();
	      } 
		
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://mysql.cs.iastate.edu:3306/db30911", "u30911", "4rv2ucue78");
			

			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			System.err.println("Error executing query!");
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void executeUpdate(String query)
	{
		try {   
	         // Load the driver (registers itself)
	         Class.forName ("com.mysql.jdbc.Driver");
	         } 
	      catch (Exception E) {
	            System.err.println ("Unable to load driver.");
	            E.printStackTrace ();
	      } 
		
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://mysql.cs.iastate.edu:3306/db30911", "u30911", "4rv2ucue78");
			
			Statement st = conn.createStatement();
			st.executeUpdate(query);

			
		} catch (SQLException e) {
			System.err.println("Error executing query!");
			e.printStackTrace();
		}
		
	}
	
	public void closeConnection()
	{
		if(conn != null)
		{
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		conn = null;
	}

}
