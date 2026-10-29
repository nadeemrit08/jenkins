package in.kodewala.thread06.implRunnable;

class MyThread implements Runnable {

	@Override
	public void run() {
		System.out.println("MyThread.run()...attending Kodewala classess ---- STARTED at 10 AM ");
		System.out.println("MyThread.run()");
		System.out.println("MyThread.run()");
		System.out.println("MyThread.run()");

		/*
		 * T1(STUDENT) can go to sleep/wait/pause state(30 minutes). --->"WAITING STATE" - 
		 * Once waiting is over, T1(Student) will move to RUNNABLE ---> CPU gives time to T1 ---> RUNNING state
		 */
		System.out.println("MyThread.run()");
		System.out.println("MyThread.run()");
		
		System.out.println("MyThread.run()... attending Kodewala classes ---- ENDED at 6 PM ");
	}   // Once run method is completed, your Thread T1 is terminated/dead

}

public class Driver {

	public static void main(String[] args) {

		MyThread myThread = new MyThread();
		
		Thread t1 = new Thread(myThread); // New Born (Thread object created)
		
		t1.start(); // Threaded moved from 'NEW Born' to 'RUNNABLE'
		
		t1.start();
	}
}
