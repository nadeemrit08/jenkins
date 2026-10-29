package in.amazon.bean;

import org.springframework.stereotype.Component;

@Component
public class Payment {

	private String refId;

	

	public Payment(String refId) {
		// TODO Auto-generated constructor stub
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public void pay() {
		System.out.println("Payment.pay().... " + refId);
	}

}
