package in.kodewala.thread09.extThread;

/*
 * Hotel --> Order Food --> Waiter --> cook --> cooking --> waiter will serve food
 */

class Cooking extends Thread {

	public void run() {
		System.out.println("Food is being prepared...[" + Thread.currentThread().getName() + "]");

		try {
			sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Food preparation done...[" + Thread.currentThread().getName() + "]");
	}
}

public class Driver {

	public static void main(String[] args) throws InterruptedException {

		Thread.currentThread().setName("Waiter");
		System.out.println("Waiter took the order...[" + Thread.currentThread().getName() + "]");

		Cooking c = new Cooking();
		c.setName("Cook");
		c.start(); // cooking started

		c.join(); // waiter thread will wait here till food is cooked

		System.out.println("Waiter started serving the food...[" + Thread.currentThread().getName() + "]");
	}
}
