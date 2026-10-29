package in.kodewala.thread17.itc;

public class Consumer extends Thread {

	Task task;

	public Consumer(Task task) {
		super();
		this.task = task;
	}

	@Override
	public void run() {

		for (int i = 0; i < 10; i++) {

			try {
				task.consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
