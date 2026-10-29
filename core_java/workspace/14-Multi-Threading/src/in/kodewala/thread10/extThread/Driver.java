package in.kodewala.thread10.extThread;

class Task {

	void printNumber() { // this method is being executed in parallel(t1 and t2)

		for (int i = 0; i < 10; i++) {

			System.out.println(" Number : " + i + " -->" + Thread.currentThread().getName());

		}
	}
}

class PrintThread extends Thread {

	Task task;

	public PrintThread(Task task) {
		super();
		this.task = task;
	}

	@Override
	public void run() {

		task.printNumber();
	}
}

public class Driver {

	public static void main(String[] args) {

		Task task1 = new Task(); // user-1

		PrintThread oddThread = new PrintThread(task1);
		oddThread.start();

		Task task2 = new Task(); // user-2
		PrintThread evenThread = new PrintThread(task2);
		evenThread.start();

	}
}
