package in.nadeem.thread;

class MyThread extends Thread {

	@Override
	public void run() {
		System.out.println("Thread started");
		super.run();

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Thread completed");
	}
}

public class Driver {

	public static void main(String[] args) throws InterruptedException {

		MyThread t1 = new MyThread();

		t1.start();
		
		t1.join();

		System.out.println("Main thread completed");
	}
}
