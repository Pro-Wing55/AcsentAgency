package ascentAgency.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Production {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bname;

    @OneToMany
    (mappedBy = "production", cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonManagedReference
    private List<ProductionItem> items = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}

	public List<ProductionItem> getItems() {
		return items;
	}

	public void setItems(List<ProductionItem> items) {
		this.items = items;
	}
    
    
    
}