package ascentAgency.Controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import ascentAgency.Repo.StockRepo;
import ascentAgency.entity.Production;
import ascentAgency.entity.Stocks;
import ascentAgency.service.BillGenrateService;

@RestController
@RequestMapping("/api/stock")

public class StockMaintain {
	@Autowired BillGenrateService billGenrateService;

    @GetMapping("/viewAllStock")
    public JsonNode c2322ChackFile() throws IOException {
    	System.out.println("this is main stock method ");
    	 final ObjectMapper objectMapper = new ObjectMapper();
    	PathMatchingResourcePatternResolver resolver =  new PathMatchingResourcePatternResolver();
      
    	Resource[] resources= resolver.getResources(
     			"file:configPoduct/stock.json"
     			);
    	 	
    	File file = resources[0].getFile();
  
    	 
        return objectMapper.readTree(file);
    }
    
    
    @GetMapping("/allord")
    public JsonNode c2321ChackFile() throws IOException {
    	 final ObjectMapper objectMapper = new ObjectMapper();
    	PathMatchingResourcePatternResolver resolver =  new PathMatchingResourcePatternResolver();
      
    	Resource[] resources= resolver.getResources(
     			"file:configPoduct/stockm.json"
     			);
    	 	
    	File file = resources[0].getFile();
    	 
        return objectMapper.readTree(file);
    }
    
    
    @PutMapping("/updOrAdd/{value}")
    public JsonNode c2323ChackFile(@PathVariable String value) throws IOException {
    
    return	billGenrateService.updateStockByBatchName(value);
    }
    
//    --------------------------------------------------------------------------------------
    @Autowired StockRepo stockRepo;
//  this file method only chacking for database use
    @GetMapping("/viewAllStock1")
    public ObjectNode c2322ChackFile1() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode node = mapper.createObjectNode();

        List<Stocks> stockList = stockRepo.findAll();

        stockList.forEach(stock ->
                node.put(stock.getItemName(), stock.getQuantity())
        );

        return node;
    	 
      
    }
    
//  this file method only chacking for database use
    @GetMapping("/allord1")
    public ObjectNode c2321ChackFile1() throws IOException {
   	 final ObjectMapper objectMapper = new ObjectMapper();
  
   	 
       return billGenrateService.getAllProduction();
   }
   
   
    
//    this file method only chacking for database use
    @PutMapping("/updOrAdd1/{value}")
    public String c2323ChackFile2(@PathVariable String value) throws IOException {
    
    return	billGenrateService.updateStockByBatchName1(value);
    }
    
}
