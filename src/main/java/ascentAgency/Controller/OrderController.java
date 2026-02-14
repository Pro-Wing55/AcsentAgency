package ascentAgency.Controller;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.JsonNode;

import ascentAgency.Repo.Impl.BillGenrateRepoIMPL;
import ascentAgency.entity.Purchase;
import ascentAgency.service.BillGenrateService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderController  {
	@Autowired BillGenrateRepoIMPL billg;
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
public JsonNode getAllbills() throws IOException { 
	System.out.println("this is getAll method ");
//	return billService.getAllBills();
	return billg.getAllBills1();
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


//-----------------puchaseBill controller-----------------------------
@PostMapping("savePurchaseBill")
   public Purchase savePurchaseBill(@RequestBody Purchase  map) {
	
	return billService.savePurchaseBill(map);
}

@GetMapping("getAllPurchaseBill")
public List<Purchase> getAllPurchaseBill() {
	
	return billService.getAllPurchaseBill();
}
    
}
