package in.kodewala.thread15.reentrant;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/*
 *  "In this example, BankAccount is a shared resource accessed by multiple customer threads. 
 *  Instead of synchronizing the whole withdraw() method, 
 *  we use a synchronized block to lock only the critical section where balance is checked and updated. 
 *  This improves performance because other non-critical logic can execute concurrently 
 *  while maintaining thread safety."
 */

class BankAccount {
	private int balance = 1000;
	ReentrantLock reentrantLock = new ReentrantLock();

	// 1000 lines of code
	public void withdraw(int amount) throws InterruptedException {

		System.out.println(Thread.currentThread().getName() + " some other logic...50 lines....");

		boolean tryLockStatus = reentrantLock.tryLock(2000, TimeUnit.MILLISECONDS); // sync started --> T1

		System.out.println("BankAccount.withdraw() " + tryLockStatus);

		if (balance >= amount) {
			System.out.println(Thread.currentThread().getName() + " is withdrawing " + amount);

			try {
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}

			balance = balance - amount;
			System.out.println(Thread.currentThread().getName() + " completed withdrwal balance = " + balance);

		} else {
			System.out.println(Thread.currentThread().getName() + " insufficient balance");
		}
		reentrantLock.unlock();
	}

	public int getBalance() {
		return balance;
	}

}

class Customer extends Thread {

	private BankAccount account;

	public Customer(BankAccount account, String name) {
		super();
		this.account = account;
	}

	@Override
	public void run() {
		try {
			account.withdraw(800);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

public class RaceConditionDemo {

	public static void main(String[] args) throws InterruptedException {

		BankAccount account = new BankAccount(); // shared account belongs to Nadeem
		System.out.println("Nadeem's initial balance = " + account.getBalance());

		Customer c1 = new Customer(account, "Nadeem is doing PhonePay");
		Customer c2 = new Customer(account, "Nadeem's brother is doing GPay");

		c1.start();
		c2.start();

		c1.join();
		c2.join();

		System.out.println("Final Balance = " + account.getBalance());
	}
}
