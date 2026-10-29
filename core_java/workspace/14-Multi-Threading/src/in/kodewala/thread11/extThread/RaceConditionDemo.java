package in.kodewala.thread11.extThread;

class BankAccount {

	private int balance = 1000;

	// synchronized prevents race condition
	public  void withdraw(int amount) {

		if (balance >= amount) {

			System.out.println(Thread.currentThread().getName() + " is withdrawing " + amount);

			// Simulate delay
			try {

				Thread.sleep(100);

			} catch (InterruptedException e) {

				e.printStackTrace();
			}

			balance = balance - amount;

			System.out.println(Thread.currentThread().getName() + " completed withdrawal. Balance = " + balance);

		} else {

			System.out.println(Thread.currentThread().getName() + " Insufficient Balance");
		}
	}
}

public class RaceConditionDemo {

	public static void main(String[] args) throws InterruptedException {

		BankAccount account = new BankAccount();

		Thread t1 = new Thread(() -> {

			account.withdraw(700);

		}, "Thread-1");

		Thread t2 = new Thread(() -> {

			account.withdraw(700);

		}, "Thread-2");

		t1.start();

		t2.start();

		t1.join();

		t2.join();

		System.out.println("Transaction Completed");
	}
}
