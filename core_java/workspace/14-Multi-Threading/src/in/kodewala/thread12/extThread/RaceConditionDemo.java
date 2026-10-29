package in.kodewala.thread12.extThread;

/*
 * "BankAccount is a shared resource accessed by multiple customer threads. 
 *  Without synchronization, multiple threads can modify balance simultaneously causing race condition. 
 *  By making withdraw() synchronized, we use the object's monitor lock and ensure only one thread performs 
 *  withdrawal at a time, making the operation thread-safe."
 */

class BankAccount {
	private int balance = 1000;

	public synchronized void withdraw(int amount) {
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
