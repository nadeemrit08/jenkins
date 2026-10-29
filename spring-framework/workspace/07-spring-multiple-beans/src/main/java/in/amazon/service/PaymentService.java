package in.amazon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import in.amazon.beans.Payment;

@Service
public class PaymentService {
	
	@Autowired
//	@Qualifier("card")
	Payment payment;

	public void doPayment() {
		payment.pay();
	}
}
