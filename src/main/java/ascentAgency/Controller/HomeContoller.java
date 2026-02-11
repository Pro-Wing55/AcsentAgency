package ascentAgency.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeContoller {  

	@GetMapping("/")
    public String home( ) {
		
		System.out.println("this is home page ");

        return "hpage/home"; // This will Main Page of My Application
    }
	
	@GetMapping("/history")
    public String history() {
        return "hpage/history"; // This is History Page of My Application
    }
	
	@GetMapping("/bills")
    public String bills() {
        return "hpage/bills"; // This is bills Page of My Application
    }

	@GetMapping("/updStock")
    public String updateStock() {
        return "hpage/updStock"; // This is bills Page of My Application
    }
}
