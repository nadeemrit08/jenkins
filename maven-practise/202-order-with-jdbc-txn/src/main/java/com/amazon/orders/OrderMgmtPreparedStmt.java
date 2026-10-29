package com.amazon.orders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class OrderMgmtPreparedStmt {

	public void updatePayment(String status, int id) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver"); // checked exception

		// Step-2 Create Connection
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/9th_march_2026", "root",
				"root");

		String updateQuery = "update payment set status = ? where id = ?";

		PreparedStatement ps = connection.prepareStatement(updateQuery);

		ps.setString(1, status);
		ps.setInt(2, id);

		int recordsUpdated = ps.executeUpdate();

		if (recordsUpdated > 0) {
			System.out.println("Record updated successfully : " + recordsUpdated);
		} else {

			System.err.println("Unable to updated record");
		}

	}
}
