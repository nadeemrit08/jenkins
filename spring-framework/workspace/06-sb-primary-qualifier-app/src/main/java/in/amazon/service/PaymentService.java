package in.amazon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.amazon.bean.Payment;

@Service
public class PaymentService {

	@Autowired
	Payment  payment;
	
	public void doPayment() {
		payment.pay();
	}
}
