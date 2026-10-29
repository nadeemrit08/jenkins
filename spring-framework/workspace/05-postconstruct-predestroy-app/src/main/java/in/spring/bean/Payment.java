package in.spring.bean;

import org.springframework.context.annotation.Scope;

@Scope("prototype")
public class Payment {

	private String refId;

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

}
