package ascentAgency.Repo.Impl;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ascentAgency.Data.SaveBillImformation;
import ascentAgency.Repo.BillGenrateRepository;
import ascentAgency.Repo.InvoiceRepo;
import ascentAgency.Repo.StockRepo;
import ascentAgency.entity.Invoice;
import ascentAgency.entity.InvoiceItem;
import ascentAgency.entity.Stocks;

@Service
public class BillGenrateRepoIMPL  implements BillGenrateRepository{

	@Autowired SaveBillImformation saveBill;
	
   static	Map<String, String> map = new HashMap<>();
  static {
	  map.put("LiquidSoup", "liq");
	  map.put("ToiletBowlCleaner", "tol");
	  map.put("HandWash5L", "han");
	  
	  map.put("GarbageBags", "gar"); //garbage bag gar
	  map.put("ColourPhenyl", "cph");// colour phenyl is cph
	  
  }
	
	@Override
	public String saveBill(String link) {
		//https://actshopmoney.netlify.app/text/AscentClean/invice.html?bill=123&date=2026-02-11&to=Rk_industry&1_LiquidSoup=111
		String string = Stream.of(link)
						.map(e->{
							String bill=e.split("invice.html?")[1].split("&")[0].split("=")[1];
							saveBill.billList.put(bill, e);
							try {
//								saveBill2(bill,link); // this metho work Json file 
								saveBill1(bill, link);// this method work onnly db oparation
							} catch (URISyntaxException | IOException e1) {
								
								e1.printStackTrace();
							}
							return e;
						}).findFirst().get();
		
		System.out.println("this bill is  ; "+string);
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
	public JsonNode getAllBills() throws IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
    	PathMatchingResourcePatternResolver resolver =  new PathMatchingResourcePatternResolver();
		 File bills= resolver.getResource("file:configPoduct/bills.json").getFile();
		 JsonNode tree = objectMapper.readTree(bills);
		
		return tree;
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

	
	private JsonNode saveBill2(String billNo,String url) throws URISyntaxException, IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
    	PathMatchingResourcePatternResolver resolver =  new PathMatchingResourcePatternResolver();
		URI uri = new URI(url);
	    String query = uri.getQuery();  // get only part after ?

	    File bills= resolver.getResource("file:configPoduct/bills.json").getFile();
	    ObjectNode  rootNode  =(ObjectNode ) objectMapper.readTree(bills);
	    
	    ObjectNode obn = objectMapper.createObjectNode();

	    
	    String[] pairs = query.split("&");

	    for (String pair : pairs) {
	        String[] keyValue = pair.split("=");
	        String key = keyValue[0];
	        String value = keyValue[1];
	        
	        if(key.equalsIgnoreCase("bill") 
	        	|| key.equalsIgnoreCase("date") 
	        	|| key.equalsIgnoreCase("to")
	        	|| key.equalsIgnoreCase("tp")) {
	        	
	        	obn.put(key, value);
	        	
	        }
	        else {
//	        	1_LiquidSoup
	        	String k= key.split("_")[1];
	        	String v= key.split("_")[0];
	        	
	        	stockUpdateByNameAndQnt(k,Integer.parseInt(v));
	        	
	        	obn.put(k, v);
	        }
	        
	    }
	    obn.put("view", url);
	    
	    // Add node to root JSON
        rootNode.set(billNo, obn);
        
     // Write back to file
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(bills, rootNode);
        
        System.out.println("rootNode is : "+rootNode);
	  
		return rootNode;
	}
	
	
	private String stockUpdateByNameAndQnt( String name ,int qnt) throws IOException{
		final ObjectMapper objectMapper = new ObjectMapper();
	 	PathMatchingResourcePatternResolver resolver =  new PathMatchingResourcePatternResolver();
	 	
	   	File stock= resolver.getResource("file:configPoduct/stock.json").getFile();
	   	
	   	ObjectNode  st = (ObjectNode )objectMapper.readTree(stock);
	   
	   	if(name.equalsIgnoreCase("ToiletBowlCleaner")) {
      	  st.put("toiletCliner", st.get("toiletCliner").asDouble()-(qnt*5));
      	  st.put("cans", st.get("cans").asDouble()-qnt)	;
        }
	   	else if(name.equalsIgnoreCase("LiquidSoup")) {
	      	  st.put("liquid_soup", st.get("liquid_soup").asDouble()-(qnt*5));
	      	  st.put("cans", st.get("cans").asDouble()-qnt)	;
	        }
		else if(name.equalsIgnoreCase("HandWash5L")) {
	      	  st.put("handwash", st.get("handwash").asDouble()-(qnt*5));
	      	  st.put("cans", st.get("cans").asDouble()-qnt)	;
	        }
		else if(name.equalsIgnoreCase("ColourPhenyl")) {
	      	  st.put("colour_Phenyl", st.get("colour_Phenyl").asDouble()-(qnt*5));
	        }
		else if(name.equalsIgnoreCase("GarbageBags")) {
	      	  st.put("liquid_soup", st.get("liquid_soup").asDouble()-(qnt*5));
	        }
	   	
	   	
	    // update back to Stock file
        objectMapper.writerWithDefaultPrettyPrinter()
       .writeValue(stock, st);
		
		return "update";
		
	}
	
	
	
	
	
	
	/// this is use for dp Oparatation
	@Autowired InvoiceRepo invoiceRepo;

	@SuppressWarnings("deprecation")
	public JsonNode getAllBills1() throws IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
		ObjectNode mainNode = objectMapper.createObjectNode();
		 
	        List<Invoice> invoices = invoiceRepo.findAll();
	        invoices.forEach(e ->{
	        	ObjectNode node = objectMapper.createObjectNode();
	        	
	                node.put("bill", e.getBill());
	                node.put("date", e.getDate().toString());
	                node.put("to", e.getCustomerName());
	                
	                node.put("tp", e.getTotalPrice());
	                node.put("view", e.getViewUrl());
	                
	                mainNode.set(e.getBill(), node);
	               
	          }  
	        );
	        
	        return mainNode;
	}
	
	
	public ObjectNode saveBill1(String billNo,String url) throws URISyntaxException, IOException {
		final ObjectMapper objectMapper = new ObjectMapper();
    	PathMatchingResourcePatternResolver resolver =  new PathMatchingResourcePatternResolver();
		URI uri = new URI(url);
	    String query = uri.getQuery();  // get only part after ?
	//ll=101&date=2026-01-30&to=Kartik_Hospital_Chinchwad&12_LiquidSoup=190&30_GarbageBags=95&2_HandWash5L=480
	    
	    
	    
	    String[] pairs = query.split("&");
	    Invoice in= new Invoice();
	    in.setViewUrl(url);
	   List<InvoiceItem> itomes= new ArrayList<>();
	   
	    for (String pair : pairs) {
	    	InvoiceItem it= new InvoiceItem();
	        String[] keyValue = pair.split("=");
	        String key = keyValue[0];
	        String value = keyValue[1];
	        
	        if(key.equalsIgnoreCase("bill")){
	        	in.setBill(value);
	        }
	        else if(key.equalsIgnoreCase("date")) {
	        	in.setDate(LocalDate.parse(value));
	        }		
	        else if( key.equalsIgnoreCase("to")) {
	        	in.setCustomerName(value);
	        }
	        else if( key.equalsIgnoreCase("tp")) {
	        	in.setTotalPrice(Double.parseDouble(value)); 
	        }
	        else {
//	        	1_LiquidSoup
	        	String k= key.split("_")[1];
	        	String v= key.split("_")[0];
	        	
	        	stockUpdateByNameAndQnt1(k,Integer.parseInt(v));
	        	
	        	it.setProductName(k);
	        	it.setQuantity(Double.parseDouble(v));
	        	
	        	itomes.add(it);
	        	it.setInvoice(in);
	        	in.getItems().add(it);
	        }
	        
	    }
	    
	   
	    invoiceRepo.save(in);
	  
	    ObjectNode node = objectMapper.createObjectNode();
        List<Stocks> stockList = stockRepo.findAll();
        stockList.forEach(stock ->
                node.put(stock.getItemName(), stock.getQuantity())
        );
        return node;
	}
	
	@Autowired StockRepo stockRepo;
	
	private String stockUpdateByNameAndQnt1( String name ,int qnt) throws IOException{
		final ObjectMapper objectMapper = new ObjectMapper();
	 	PathMatchingResourcePatternResolver resolver =  new PathMatchingResourcePatternResolver();
	 	
	  
	   	List<Stocks> all = stockRepo.findAll();
	   	Optional<Stocks> first = all.stream().filter(e->{
	   		if(e.getItemName().equalsIgnoreCase(name)) {
	   			e.setQuantity(e.getQuantity()-(qnt*5));
	   			System.out.println("-1---------------------------------------------------------------------");
	   			updateCans( qnt);
	   			stockRepo.save(e);
	   			return true;
	   		}
	   		else {
	   			return false;
	   		}
	   	}).findFirst();
	   
	   	
		
		return "update";
		
	}
	
	private String updateCans(double qnt) {
		Stocks st = stockRepo.findByItemName("cans").get(0);
		st.setQuantity(st.getQuantity()-qnt);
		System.out.println("---2-------------------------------------------------------------------");
		stockRepo.save(st);
		return null;
	}
	
	
//	 this methodd is update or add purchase entry 
	public Stocks update(String itemName, double qnt) {
		Stocks st = stockRepo.findByItemName(itemName).get(0);
		st.setQuantity(st.getQuantity()+qnt);
		return stockRepo.save(st);
	}
	
}
