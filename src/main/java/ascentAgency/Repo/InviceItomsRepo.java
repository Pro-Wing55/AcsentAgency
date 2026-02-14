package ascentAgency.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ascentAgency.entity.InvoiceItem;

@Repository
public interface InviceItomsRepo extends JpaRepository<InvoiceItem, Integer> {

}
