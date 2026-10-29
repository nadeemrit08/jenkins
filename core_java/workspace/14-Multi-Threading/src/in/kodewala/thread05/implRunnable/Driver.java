package in.kodewala.thread05.implRunnable;

class MyThread implements Runnable {

	@Override
	public void run() {
		System.out.println("MyThread.run() : is running... ");
	}

}

public class Driver {

	public static void main(String[] args) {

		MyThread myThread = new MyThread();
		
		Thread t1 = new Thread(myThread);
		
		System.out.println(t1.getState());
		
		t1.start();
		
		System.out.println(t1.getState());

	}
}
