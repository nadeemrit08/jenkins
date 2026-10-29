package in.nadeem.thread4;

class TicketBooking {

	int ticket = 5;

	Object lock = new Object();

	public void ticketBooking(String name) {

		synchronized (lock) {

			if (ticket > 0) {
				System.out.println(name + " booked ticket");
				ticket--;
			} else {
				System.out.println(" ticket not available");
			}
		}
	}
}

class Customer extends Thread {

	TicketBooking booking;
	String name;

	public Customer(TicketBooking booking, String name) {
		super();
		this.booking = booking;
		this.name = name;
	}

	@Override
	public void run() {
		booking.ticketBooking(name);
		super.run();
	}
}

public class Driver {

	public static void main(String[] args) {

		TicketBooking ticketBooking = new TicketBooking();

		Customer t1 = new Customer(ticketBooking, "User-1");

		Customer t2 = new Customer(ticketBooking, "User-2");

		t1.start();

		t2.start();
	}
}
