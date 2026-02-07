package ascentAgency.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ascentAgency.Repo.BillGenrateRepository;

@Service
public class BillGenrateService {

	@Autowired BillGenrateRepository billGRepo;
	
	public String saveBill(String value) {
		// TODO Auto-generated method stub
		return billGRepo.saveBill(value);
	}
	public List<String> saveAllBill(Map<String, String> map) {
		// TODO Auto-generated method stub
		return billGRepo.saveAll(map);
	}

	public List<String> getAllBills() {
		// TODO Auto-generated method stub
		return billGRepo.getAllBills();
	}

	public String getBillsByInviceNo(Integer id) {
		// TODO Auto-generated method stub
		return billGRepo.getBillByInviceNo(id);
	}

	public List<Map<String, String>> getBillsByCustomerName(String name) {
		// TODO Auto-generated method stub
		return billGRepo.getBillsByCustomerName(name);
	}

}
