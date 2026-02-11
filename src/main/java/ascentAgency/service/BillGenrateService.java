package ascentAgency.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ascentAgency.Repo.BillGenrateRepository;

@Service
public class BillGenrateService {

	@Autowired BillGenrateRepository billGRepo;
	
	public String saveBill(String value) {
		
		return billGRepo.saveBill(value);
	}
	public List<String> saveAllBill(Map<String, String> map) {
		// TODO Auto-generated method stub
		return billGRepo.saveAll(map);
	}

	public JsonNode getAllBills() throws IOException {
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
	
	
	
	
	
	public JsonNode updateStockByBatchName(String value) throws StreamWriteException, DatabindException, IOException {
		
   	 final ObjectMapper objectMapper = new ObjectMapper();
   	PathMatchingResourcePatternResolver resolver =  new PathMatchingResourcePatternResolver();
   	Resource resource = resolver.getResource("file:configPoduct/conseBatch.json");
   	JsonNode tree = objectMapper.readTree(resource.getFile());
   	JsonNode bt = tree.get(value);
   	
   	File stockm= resolver.getResource("file:configPoduct/stockm.json").getFile();
   	File stock= resolver.getResource("file:configPoduct/stock.json").getFile();
   	
   	ObjectNode  st = (ObjectNode )objectMapper.readTree(stock);
   		
   		
   	// Today's date → dd-MM-yyyy
   	String date = LocalDateTime.now()
   	        .format(DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss"));
   	ObjectNode  rootNode  =(ObjectNode ) objectMapper.readTree(stockm);
   	
   	
   	
   	ObjectNode todayNode = objectMapper.createObjectNode();
		          todayNode.put("bname", value);
		          
		          String sn = value.substring(0,3);
		    	double qn = Double.parseDouble(value.substring(3,value.length()));
		          
		          if(sn.equalsIgnoreCase("tol")) {
		        	  st.put("toiletCliner", st.get("toiletCliner").asDouble()+qn);
		          }
		          else if(sn.equalsIgnoreCase("liq")) {
		        	  st.put("liquid_soup", st.get("liquid_soup").asDouble()+qn);
		          }
		          else if(sn.equalsIgnoreCase("han")) {
		        	  st.put("handwash", st.get("handwash").asDouble()+qn);
		          }
		          
		          
		          Iterator<String> itr = bt.fieldNames();
		          	while(itr.hasNext()) {
		          		String v=itr.next();
		          		double use= bt.get(v).asDouble();
		          		double stoc= st.get(v).asDouble();
		          		
		          		if(v.equalsIgnoreCase("soda")) {
		          			 todayNode.put("soda_use", use);
		          		    todayNode.put("soda_remain", stoc-use);
		          		}
		          		
		          		else if (v.equalsIgnoreCase("slurry")) {
		          			 todayNode.put("slurry_use", use);
		   		             todayNode.put("slurry_remain", stoc-use);
		          		}
		          		
		          		else if(v.equalsIgnoreCase("sles")) {
		          			 todayNode.put("sles_use", use);
		   		             todayNode.put("sles_remain", stoc-use);
		          		}
		          		
		          		else if(v.equalsIgnoreCase("salt")) {
		          			todayNode.put("salt_use", use);
		  		            todayNode.put("salt_remain", stoc-use);
		          		}
		          		
		          		else if(v.equalsIgnoreCase("acid")) {
		          			todayNode.put("acid_use", use);
		  		            todayNode.put("acid_remain", stoc-use);
		          		}
		          		
		          		else {
		          			
		          		}
		          		
		          		st.put(v, stoc-use);
		          		
		          	}
		          	
		            // Add node to root JSON
		            rootNode.set(date, todayNode);

		            // Write back to file
		            objectMapper.writerWithDefaultPrettyPrinter()
		                    .writeValue(stockm, rootNode);
		            
		            // update back to Stock file
		            objectMapper.writerWithDefaultPrettyPrinter()
                   .writeValue(stock, st);
		         
		   
		          
 
   	
   
//       return objectMapper.readTree(updateValue);
   	  return st;
   	  
	}

}
