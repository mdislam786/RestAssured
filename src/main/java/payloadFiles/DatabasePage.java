package payloadFiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabasePage  {
	

	public static List<String> getProduct(String columnName,int id) throws ClassNotFoundException, SQLException {
		// Setting properties for mysql
		Class.forName("com.mysql.cj.jdbc.Driver");
		// creating a connection to your local database
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/techfios_api_db", "root", "");
		// empowering the con reference valiable to execute queries
		Statement smt = con.createStatement();
		// delivering the sql query
		ResultSet rs = smt.executeQuery("select * from products where id="+ id);
		List <String>listOfPrducts= new ArrayList<String>();
		while (rs.next()) {
			listOfPrducts.add(rs.getString(columnName));
//		return	rs.getString(columnName);
		}
		return listOfPrducts;

	}

}
