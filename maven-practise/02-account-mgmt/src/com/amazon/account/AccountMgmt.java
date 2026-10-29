package com.amazon.account;

import com.amazon.user.UserMgmt;

public class AccountMgmt {

	public static void main(String[] args) {
		
		UserMgmt um = new UserMgmt();
		
		um.getUserInfo("marketplace");
		
	}
}
