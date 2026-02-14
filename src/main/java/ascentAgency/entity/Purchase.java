package ascentAgency.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String billNo;
    private String supplier;
    private LocalDate date;
    private Double total;

    @OneToMany(mappedBy = "purchase",
               cascade = CascadeType.ALL,
               orphanRemoval = true)
    @JsonManagedReference
    private List<PurchaseItems> items;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<PurchaseItems> getItems() {
		return items;
	}

	public void setItems(List<PurchaseItems> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "Purchase [id=" + id + ", billNo=" + billNo + ", supplier=" + supplier + ", date=" + date + ", total="
				+ total + ", items=" + items + "]";
	}

    // getters & setters
    
	
	
    
}