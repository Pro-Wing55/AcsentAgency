package ascentAgency.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ascentAgency.SV;
import ascentAgency.Repo.BillGenrateRepository;
import ascentAgency.Repo.ProductionItemRepo;
import ascentAgency.Repo.ProductionRepo;
import ascentAgency.Repo.PurchaseRepo;
import ascentAgency.Repo.StockRepo;
import ascentAgency.Repo.Impl.BillGenrateRepoIMPL;
import ascentAgency.entity.Production;
import ascentAgency.entity.ProductionItem;
import ascentAgency.entity.Purchase;
import ascentAgency.entity.Stocks;

@Service
public class BillGenrateService {
	
	public  static final String HandWash ="HandWash";
	public  static final String water ="water";
	public  static final String LiquidSoup ="LiquidSoup";
	public  static final String ToiletCleaner ="ToiletCleaner";
	
	public  static final String GarbageBags ="GarbageBags";
	public  static final String ColourPhenyl ="ColourPhenyl";
	public  static final String GreenPhenyl ="GreenPhenyl";
	public  static final String WhitePhenyl ="WhitePhenyl";
	
	

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

	
	
	@Autowired ProductionRepo prpo;
	@Autowired ProductionItemRepo pipo;
	@Autowired StockRepo stockRepo;
	@Autowired PurchaseRepo purchaseRepo;
	@Autowired BillGenrateRepoIMPL billGenrateRepoIMPL;
	
	

	
	
	// this is very important file for save oll data to update 
	
	public String updateStockByBatchName1(String value) throws StreamWriteException, DatabindException, IOException {
		
	   	 final ObjectMapper objectMapper = new ObjectMapper();
	   	PathMatchingResourcePatternResolver resolver =  new PathMatchingResourcePatternResolver();
	   	Resource resource = resolver.getResource("classpath:configPoduct/conseBatch.json");
	   	JsonNode tree = objectMapper.readTree(resource.getFile());
	
	   	JsonNode bt = tree.get(value);
	   	List<Stocks> all = stockRepo.findAll();
	   	
	   	Production prod= new Production();
	    List<ProductionItem> items = new ArrayList<>();
	   	
	   	// Today's date → dd-MM-yyyy
	   	String date = LocalDateTime.now()
	   	        .format(DateTimeFormatter.ofPattern("d-M-yyyy HH:mm:ss"));
	   	prod.setBname(date);
	   	
//	   	ObjectNode todayNode = objectMapper.createObjectNode();
//			          todayNode.put("bname", value);
			          
			          String sn = value.substring(0,3);
			    	double qn = Double.parseDouble(value.substring(3,value.length()));
			          
			          if(sn.equalsIgnoreCase("tol")) {
			        	  Stocks tp = all.stream()
			        			  .filter(e->e.getItemName().equalsIgnoreCase(SV.ToiletCleaner))
			        			  .findFirst().get();
			        	  tp.setQuantity( tp.getQuantity() + qn);
			        	  stockRepo.save(tp);
			          }
			          else if(sn.equalsIgnoreCase("liq")) {
						         Stocks tp = all.stream()
				        			  .filter(e->e.getItemName().equalsIgnoreCase(SV.LiquidSoup))
				        			  .findFirst().get();
				        	  tp.setQuantity( tp.getQuantity() + qn);
				        	  stockRepo.save(tp);
			          }
			          else if(sn.equalsIgnoreCase("han")) {
			        	  Stocks tp = all.stream()
			        			  .filter(e->e.getItemName().equalsIgnoreCase(SV.HandWash))
			        			  .findFirst().get();
			        	  tp.setQuantity( tp.getQuantity() + qn);
			        	  stockRepo.save(tp);
			          }
			          
			          
			          Iterator<String> itr = bt.fieldNames();
			          	while(itr.hasNext()) {
			          		ProductionItem prodI= new ProductionItem();
			          		
			          		String v=itr.next();
			          		Stocks stocks = all.stream()
			          				.filter(e->e.getItemName().equalsIgnoreCase(v))
			          				.findFirst().get();
			          		double use= bt.get(v).asDouble();
			          		double stoc=stocks.getQuantity();
			          		
			          		if(v.equalsIgnoreCase("soda")) {
			          			
			          		    
			          		    prodI.setMaterialName("soda");
			          		    prodI.setUsedQty(use);
			          		    prodI.setRemainQty(stoc-use);
			          		   items.add(prodI);
			          		}
			          		
			          		else if (v.equalsIgnoreCase("slurry")) {
			          			
			   		            
			   		            prodI.setMaterialName("slurry");
			          		    prodI.setUsedQty(use);
			          		    prodI.setRemainQty(stoc-use);
			          		   items.add(prodI);
			          		}
			          		
			          		else if(v.equalsIgnoreCase("sles")) {
			          			
			   		             
			   		            prodI.setMaterialName("sles");
			          		    prodI.setUsedQty(use);
			          		    prodI.setRemainQty(stoc-use);
			          		   items.add(prodI);
			          		}
			          		
			          		else if(v.equalsIgnoreCase("salt")) {
			          			
			  		            
			  		            prodI.setMaterialName("salt");
			          		    prodI.setUsedQty(use);
			          		    prodI.setRemainQty(stoc-use);
			          		   items.add(prodI);
			          		}
			          		
			          		else if(v.equalsIgnoreCase("acid")) {
			  		            prodI.setMaterialName("acid");
			          		    prodI.setUsedQty(use);
			          		    prodI.setRemainQty(stoc-use);
			          		   items.add(prodI);
			          		}
			          		
			          		else {
			          			
			          		}
			          		// meintain stock to save return
						   	stocks.setQuantity(stocks.getQuantity()-use);
						   	stockRepo.save(stocks);
			          		
			          	  	prodI.setProduction(prod);
			          	    prod.getItems().add(prodI);
			          		
			          	}
			          	
			          	prod.setItems(items);
			          	prpo.save(prod);
			          	
	   	  return "update Ok";
	   	  
		}

	
	public ObjectNode getAllProduction() {
		
		List<Production> productions = prpo.findAll();

	    ObjectMapper mapper = new ObjectMapper();
	    ObjectNode rootNode = mapper.createObjectNode();

	    for (Production production : productions) {

	        ObjectNode productionNode = mapper.createObjectNode();

	        // If you have actual product name field use it
	        productionNode.put("bname", "tol90"); 
	        // OR productionNode.put("bname", production.getProductName());

	        for (ProductionItem item : production.getItems()) {

	            productionNode.put(
	                item.getMaterialName() + "_use",
	                item.getUsedQty()
	            );

	            productionNode.put(
	                item.getMaterialName() + "_remain",
	                item.getRemainQty()
	            );
	        }

	        // Using bname (timestamp) as key
	        rootNode.set(production.getBname(), productionNode);
	    }

	    return rootNode;
	}
	
	
	
//	----------------------- save purchase imformation ----------------------------------------------------------

	public Purchase savePurchaseBill(Purchase purchase) {		
		purchase.getItems().stream()
					.map(e-> {
						
						Double price = e.getPrice();
						billGenrateRepoIMPL.update(e.getItem(), e.getQty());
						e.setPurchase(purchase);
						return e; 
						})
					.collect(Collectors.toList());
		
		return purchaseRepo.save(purchase);
	}
	
	
	// get all purchase Entry 
	public List<Purchase> getAllPurchaseBill() {		
		return purchaseRepo.findAll();
	}
	
	
}
