package ascentAgency.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ascentAgency.entity.Purchase;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {

}
