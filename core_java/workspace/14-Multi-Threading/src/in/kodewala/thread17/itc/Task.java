package in.kodewala.thread17.itc;

public class Task {

	int number;
	boolean isDataAvailable = false;

	public synchronized void produce(int _num) throws InterruptedException {

		while (isDataAvailable) {
			System.out.println(Thread.currentThread().getName() + " is waiting....");
			wait();
		}

		number = _num;
		System.out.println("producing data : " + number);

		isDataAvailable = true;

		// Producer takes time to produce
		Thread.sleep(1000);

		notify();
	}

	public synchronized void consume() throws InterruptedException {

		while (!isDataAvailable) {
			System.out.println(Thread.currentThread().getName() + " is waiting....");
			wait();
		}

		System.out.println("consuming it.. " + number);

		// Consumer takes time to consume
		Thread.sleep(1500);

		isDataAvailable = false;

		notify();
	}
}
