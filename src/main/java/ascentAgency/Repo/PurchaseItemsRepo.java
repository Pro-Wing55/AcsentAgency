package ascentAgency.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ascentAgency.entity.PurchaseItems;

@Repository
public interface PurchaseItemsRepo extends JpaRepository<PurchaseItems	, Integer> {

}
