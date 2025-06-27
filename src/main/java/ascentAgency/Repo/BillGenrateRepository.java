package ascentAgency.Repo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface BillGenrateRepository {
		
	public String saveBill(String link);
	public String getBillByInviceNo(int invoceNo);
	public String getBillByKey(String key);
	public List<String> getAllBills();
	public List<Map<String, String>> getBillsByCustomerName(String name);
	
	
}
