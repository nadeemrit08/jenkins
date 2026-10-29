package in.kodewala;

import java.sql.SQLException;

public class App {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {

		PaymentService paymentService = new PaymentService();

		paymentService.getAllPayments("PAID");

		//paymentService.updatePaymentStatus(102, "PAID");
	}
}
