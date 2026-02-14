package ascentAgency.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ascentAgency.entity.ProductionItem;

@Repository
public interface ProductionItemRepo extends JpaRepository<ProductionItem, Integer> {

}
