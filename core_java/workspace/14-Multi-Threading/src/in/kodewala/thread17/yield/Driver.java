package in.kodewala.thread17.yield;


class FirstThread extends Thread {

	public void run() {
		System.out.println("FirstThread.run() START " + Thread.currentThread().getName());

		Thread.yield(); // pause the current there and give chance to other thread to execute

		System.out.println("FirstThread.run() END " + Thread.currentThread().getName());
	}
}

public class Driver {

	public static void main(String[] args) {

		FirstThread t1 = new FirstThread();
		t1.start();

		FirstThread t2 = new FirstThread();
		t2.start();
		
		/*
		 * Thread t3 = new Thread(() -> { System.out.println("Driver.main()"); });
		 * 
		 * t3.start();
		 */
	}
}
