package in.kodewala.thread08.extThread;

class OddThread extends Thread {

	public void run() {

		int count = 0;

		for (int i = 1; i <= 20; i += 2) {
			
			Thread.currentThread().setName("Thread-Odd");

			System.out.println(Thread.currentThread().getName() + " : " + i);

			count++;

			if (count == 5) {

				try {

					System.out.println(Thread.currentThread().getName() + " Sleeping for 5 seconds...");

					Thread.sleep(5000);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
}

class EvenThread extends Thread {

	public void run() {

		int count = 0;

		for (int i = 2; i <= 20; i += 2) {
			
			Thread.currentThread().setName("Thread-Even");

			System.out.println(Thread.currentThread().getName() + " : " + i);

			count++;

			if (count == 5) {

				try {

					System.out.println(Thread.currentThread().getName() + " Sleeping for 5 seconds...");

					Thread.sleep(5000);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}
		}
	}
}

public class Driver {

	public static void main(String[] args) {

		OddThread odd = new OddThread();

		EvenThread even = new EvenThread();

		odd.setName("Odd Thread");

		even.setName("Even Thread");

		odd.start();

		even.start();

	}
}

