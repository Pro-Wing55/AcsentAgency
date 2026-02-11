package ascentAgency.Repo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.JsonNode;

@Repository
public interface BillGenrateRepository {
		
	public String saveBill(String link);
	public String getBillByInviceNo(int invoceNo);
	public String getBillByKey(String key);
	public JsonNode getAllBills() throws IOException;
	public List<Map<String, String>> getBillsByCustomerName(String name);
	
	List<String> saveAll(Map<String, String> map);
	
	
}
