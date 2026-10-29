package in.kodewala.thread17.itc;

public class Driver {

	public static void main(String[] args) {

		Task task = new Task(); // shared object

		Producer p = new Producer(task);
		p.setName("Producer");
		p.start();

		Consumer c = new Consumer(task);
		c.setName("Consumer");
		c.start();
	}
}
