package com.amazon.user;

public class UserMgmt {

	public void getUserInfo(String userType) {

		if (UserType.RETAIL.equalsIgnoreCase(userType)) {

			System.out.println("====== Retail Customer Details ======");
			System.out.println("User ID        : U1001");
			System.out.println("Name           : Rahul Sharma");
			System.out.println("Email          : rahul@gmail.com");
			System.out.println("Mobile         : +91-9876543210");
			System.out.println("Membership     : Prime");
			System.out.println("Reward Points  : 1250");
			System.out.println("Orders Placed  : 18");
			System.out.println("Account Status : Active");

		} else if (UserType.RESELLER.equalsIgnoreCase(userType)) {

			System.out.println("====== Reseller Details ======");
			System.out.println("Reseller ID    : R2001");
			System.out.println("Business Name  : Sharma Electronics");
			System.out.println("Owner Name     : Amit Sharma");
			System.out.println("Email          : amit@sharmaelectronics.com");
			System.out.println("Mobile         : +91-9988776655");
			System.out.println("GST Number     : 29ABCDE1234F1Z5");
			System.out.println("Discount Level : Gold");
			System.out.println("Credit Limit   : ₹5,00,000");
			System.out.println("Account Status : Active");

		} else if (UserType.MARKETPLACE.equalsIgnoreCase(userType)) {
			System.out.println("This is marketplace user");
		}
		
		
		else {

			System.out.println("Invalid User Type.");
		}
	}

}
