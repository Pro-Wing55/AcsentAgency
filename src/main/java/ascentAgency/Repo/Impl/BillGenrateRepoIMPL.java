package ascentAgency.Repo.Impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ascentAgency.Data.SaveBillImformation;
import ascentAgency.Repo.BillGenrateRepository;

@Service
public class BillGenrateRepoIMPL  implements BillGenrateRepository{

	@Autowired SaveBillImformation saveBill;
	
	@Override
	public String saveBill(String link) {
		String string = Stream.of(link)
						.map(e->{
							String bill=e.split("invice.html?")[1].split("&")[0].split("=")[1];
							saveBill.billList.put(bill, e);
							return e;
						}).findFirst().get();
		System.out.println("all bill is ; "+saveBill.billList);
		return string;
	}
	@Override
	public List<String> saveAll(Map<String, String> map) {
		saveBill.billList.putAll(map);
		return saveBill.billList.values().stream().toList();
	}

	@Override
	public String getBillByInviceNo(int invoceNo) {
		
		String string =saveBill.billList.values().stream()
				.filter(e->{
					String bill=e.split("invice.html?")[1].split("&")[0].split("=")[1];		
					return bill.equals(String.valueOf(invoceNo));
				}).findFirst().get();
		return string;
	}

	@Override
	public String getBillByKey(String key) {
		return saveBill.billList.get(key);
	}

	@Override
	public List<String> getAllBills() {
		// 
		return saveBill.billList.values().stream().toList();
	}

	@Override
	public List<Map<String, String>> getBillsByCustomerName(String name) {
//	"http://127.0.0.1:5500/text/AscentClean/invice.html?bill=900&date=2025-06-26&to=Rk_industry&19_LiquidSoup=115"
//   http://127.0.0.1:5500/text/AscentClean/invice.html?bill=340&date=2025-06-26&to=Rk_industry&10_ToiletCleanner=300&5_Handwash=450		
		 List<Map<String, String>> list = new ArrayList<>();
      
		 List<String> list2 = saveBill.billList.values().stream()
					.filter(e->{
						String bill=e.split("invice.html?")[1].split("&")[2].split("=")[1];
						return bill.equals(name);
					})
					.map(e->{
						Map<String, String>  map= new HashMap<>();
						int count=0;
						String li[] = e.split("invice.html?")[1].split("&");
						for (String l : li) {
							if(count++ <= 2) {
								map.put(l.split("=")[0],l.split("=")[1]);
							}
							else {
								String quint =l.split("=")[0].split("_")[0];
								String rate =l.split("=")[1];
								map.put(l.split("=")[0].split("_")[1], quint+'*'+rate.trim());
							}
						}
						list.add(map); 
						return e;
					})
					.toList();
		return list;
	}

	
	
}
