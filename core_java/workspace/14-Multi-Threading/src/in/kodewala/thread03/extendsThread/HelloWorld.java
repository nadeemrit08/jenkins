package in.kodewala.thread03.extendsThread;

class MyThread extends Thread {

	@Override
	public void run() {
		super.run();
		System.out.println("MyThread.run()...!!! - executing the task....!!! " + Thread.currentThread().getName());

		Payment payment = new Payment();
		payment.doPayment();
	}
}

public class HelloWorld {

	public static void main(String[] args) throws InterruptedException {

		System.out.println("HelloWorld.main() - START : " + Thread.currentThread().getName());

		MyThread t1 = new MyThread(); // new thread create

		t1.setName("T1");

		t1.start(); // new thread started ---> main thread + T1

		MyThread t2 = new MyThread(); // new thread create

		t2.setName("T2");

		t2.start(); // new thread started ---> main thread + T2

		Thread.sleep(1000);
		System.out.println("HelloWorld.main() - END : " + Thread.currentThread().getName());

	}

}

class Payment {

	public void doPayment() {
		System.out.println("Payment.doPayment() :: " + Thread.currentThread().getName());
	}
}
