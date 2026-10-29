package com.amazon;

import com.amazon.orders.OrderMgmt;
import com.amazon.orders.OrderMgmtPreparedStmt;

public class App {
	public static void main(String[] args) throws Exception {
//		OrderMgmt mgmt = new OrderMgmt();
//
//		mgmt.placeOrder();

		OrderMgmtPreparedStmt stmt = new OrderMgmtPreparedStmt();

		stmt.updatePayment("BLOCKED", 101);
	}
}
