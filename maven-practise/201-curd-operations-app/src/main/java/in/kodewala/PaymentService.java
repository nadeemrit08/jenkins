package in.kodewala;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PaymentService {

	public void getAllPayments(String _status) throws ClassNotFoundException, SQLException {

		/*
		 * Step-1 Load JDBC Driver
		 */
		Class.forName("com.mysql.cj.jdbc.Driver"); // checked exception

		// Step-2 Create Connection
		Connection connection = DriverManager.getConnection(
											"jdbc:mysql://localhost:3306/9th_march_2026",
											"root",
											"root"
											);

		//Step - 3 Create Statement
		Statement stmt = connection.createStatement();

		String query = null;

		if (_status == null) {
			query = "select * from payment";
		} else {
			query = "select * from payment where status='" + _status + "'";
		}

		System.out.println(query);
		
		//step-4 execute query
		ResultSet rs = stmt.executeQuery(query);

		//process data
		while (rs.next()) {
			int id = rs.getInt(1);
			String refId = rs.getString(2);
			String status = rs.getString(3);

			System.out.println("ID      : " + id);
			System.out.println("Ref ID  : " + refId);
			System.out.println("Status  : " + status);
			System.out.println("*********************");
		}
	}

	public void updatePaymentStatus(int id, String _status) throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection connection = DriverManager.getConnection(
											"jdbc:mysql://localhost:3306/9th_march_2026", 
											"root",
											"root"
											);

		Statement stmt = connection.createStatement();

		String query = "update payment set status ='" + _status + "' where id =" + id;

		System.out.println("Query is : " + query);

		int recordUpdated = stmt.executeUpdate(query);
		
		System.out.println("Record updated :: " + recordUpdated);

	}
}
