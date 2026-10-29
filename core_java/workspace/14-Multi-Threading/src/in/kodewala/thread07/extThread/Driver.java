package in.kodewala.thread07.extThread;

class PrintNumbers extends Thread {
	@Override
	public void run() {

		System.out.println("PrintNumbers.run()....!!");

		for (int i = 0; i < 10; i++) {

			System.out.println("Number is : " + i + " and printed by " + Thread.currentThread().getName());

			if (i == 5) {

				Thread.currentThread().setName("Thread - Nadeem");
				System.out.println("sending " + Thread.currentThread().getName() + " to sleeping/waiting state");

				try {

					Thread.currentThread().sleep(10000); // sleep for 5 sec

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("sleeping time over, execution started again ");
			}
		}
	}// TERMINATED
}

public class Driver {

	public static void main(String[] args) {

		PrintNumbers p = new PrintNumbers();
		p.start();
	}
}
