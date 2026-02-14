package ascentAgency.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ascentAgency.entity.Stocks;

@Repository
public interface StockRepo  extends JpaRepository<Stocks, Integer>{

	
	public  List<Stocks> findByItemName(String itemName);
}
