package in.kodewala.thread14.extThread;

/*
 *  "In this example, balance is a static shared resource, so all customer threads access the same data. 
 *  Instead of synchronizing the complete withdraw method, we use a synchronized block with 
 *  BankAccount.class to create a class-level lock only around the critical section 
 *  where balance is checked and updated. This prevents race conditions while allowing 
 *  other non-critical operations to execute concurrently, improving performance."
 */

class BankAccount {
	private static int balance = 1000;

	// 1000 lines of code
	public static void withdraw(int amount) {

		System.out.println(Thread.currentThread().getName() + " some other logic...50 lines...."); // slow down the
																									// performance

		synchronized (BankAccount.class) { // only one Thread will be able to execute this block

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
		}
	}

	public static int getBalance() {
		return balance;
	}

}

class Customer extends Thread {

	public Customer(BankAccount account, String name) {
		super(name);
	}

	@Override
	public void run() {
		BankAccount.withdraw(800);
	}
}

public class RaceConditionDemo {

	public static void main(String[] args) throws InterruptedException {

		BankAccount account = new BankAccount(); // shared account belongs to Nadeem
		System.out.println("Nadeem's initial balance = " + BankAccount.getBalance());

		Customer c1 = new Customer(account, "Nadeem is doing PhonePay");
		Customer c2 = new Customer(account, "Nadeem's brother is doing GPay");

		c1.start();
		c2.start();

		c1.join();
		c2.join();

		System.out.println("Final Balance = " + BankAccount.getBalance());
	}
}
