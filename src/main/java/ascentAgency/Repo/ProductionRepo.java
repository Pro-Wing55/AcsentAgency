package ascentAgency.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ascentAgency.entity.Production;

@Repository
public interface ProductionRepo extends JpaRepository<Production, Integer> {

}
