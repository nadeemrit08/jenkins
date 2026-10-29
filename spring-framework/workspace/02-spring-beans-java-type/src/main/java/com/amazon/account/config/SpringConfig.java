package com.amazon.account.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.amazon.account.beans.Account;

@Configuration // This tell that this class is source of bean definition
@ComponentScan(basePackages = "com.amazon")
public class SpringConfig {

//	/**
//	 * How do we define bran?
//	 */
//	@Bean("account")
//	public Account createAccount() {
//
//		Account account = new Account();
//		account.setAccountHolderName("Batch, 9th March");
//		account.setAccountNumber("0987654321");
//		account.setIfscCode("SBIN87653");
//
//		return account;
//	}
//
//	@Bean("account")
//	public Account createAccount2() {
//
//		Account account = new Account();
//		account.setAccountHolderName("Batch, 9th March");
//		account.setAccountNumber("0987654321");
//		account.setIfscCode("ICICN87653");
//
//		return account;
//	}

}
