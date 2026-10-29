package com.amazon.orders;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderMgmt {

	public void placeOrder() throws ClassNotFoundException, SQLException {

		/*
		 * Step-1 Load JDBC Driver
		 */
		Class.forName("com.mysql.cj.jdbc.Driver"); // checked exception

		// Step-2 Create Connection
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/9th_march_2026", "root",
				"root");

		try {
			// Step - 3 Create Statement
			Statement stmt = connection.createStatement();

			connection.setAutoCommit(false);

			// create order record with status CREATED
			// Confirm the payment
			// Update the order tables with PAID

			String createdOrderSQL = "INSERT INTO ORDERS VALUES(1, 'IPHONE18', 'CREATED')";

			int records = stmt.executeUpdate(createdOrderSQL);

			if (records > 0) {
				System.out.println("Order Record inserted Successfully");
			} else {
				System.out.println("Failed");
			}

			String createdPaymentSQL = "INSERT INTO payment VALUES (101, 'REF1009', 'PAID')";

			records = stmt.executeUpdate(createdPaymentSQL);

			if (records > 0) {
				System.out.println("Payment Record inserted Successfully");
			} else {
				System.out.println("Failed");
			}

			// user did not have enough balance
			String updatePaymentSQL = "update payment set status='FAILED' where id=101";

//			String name = null;
//			name.length();

			records = stmt.executeUpdate(updatePaymentSQL);

			if (records > 0) {
				System.out.println("Payment Record updated Successfully");
			} else {
				System.out.println("Failed");
			}	
			System.out.println("Doing commit");
			connection.commit();

		} catch (Exception e) {
			System.out.println("Exception - Doing rollback");
			connection.rollback();
		}

	}

}
