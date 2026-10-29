package in.kodewala.thread13.extThread;

/*
 *  "In this example, BankAccount is a shared resource accessed by multiple customer threads. 
 *  Instead of synchronizing the whole withdraw() method, 
 *  we use a synchronized block to lock only the critical section where balance is checked and updated. 
 *  This improves performance because other non-critical logic can execute concurrently 
 *  while maintaining thread safety."
 */

class BankAccount {
	private int balance = 1000;

	// 1000 lines of code
	public void withdraw(int amount) {

		System.out.println(Thread.currentThread().getName() + " some other logic...50 lines...."); // slow down the
																									// performance

		synchronized (this) { // only one Thread will be able to execute this block

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
		account.withdraw(800);
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
