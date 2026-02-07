package ascentAgency.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ascentAgency.service.BillGenrateService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderController  {
	
	@Autowired BillGenrateService billService;
	
@PostMapping("savebill")
public String savebill(@RequestHeader String url) {
	System.out.println("this is Save  method "+url);
	return billService.saveBill(url);
}

@PostMapping("saveAllBill")
public List<String> saveAllbills(@RequestBody Map<String, String>  map) {
	System.out.println("this is Save  method ");
	return billService.saveAllBill(map);
}

@GetMapping("getBills")
public List<String> getAllbills() { 
	System.out.println("this is getAll method ");
	return billService.getAllBills();
}
   
@GetMapping("getBillsById/{id}")
public String getBillByInviceNo(@PathVariable Integer id ) {
	
	return billService.getBillsByInviceNo(id);
}

@GetMapping("getBillsByCostomerName/{name}")
public List<Map<String, String>> getBillsByCustomerName(@PathVariable String name ) {
	System.out.println("this is getCustomerName method ");
	return billService.getBillsByCustomerName(name);
}


    
}
