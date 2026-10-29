package in.irctc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PnrController {

	@GetMapping("/pnrCheck")
	public String checkPnrStatus(@RequestParam("pnrNumber") String pnrNumber) {
		System.out.println("Recieved PNR from user is " + pnrNumber);
		return "pnr-status";
	}

}
