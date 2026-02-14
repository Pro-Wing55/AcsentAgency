package ascentAgency.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;


//@Entity
public class Order {
//@Id
//@GeneratedValue(strategy = GenerationType.AUTO)
	    private Long id;
	    private String customerName;
	    private String mobile;
	    private String email;
	    private String address;
	    private Double totalAmount;
	    private LocalDateTime orderDate;
	    
	    @OneToMany
	    private List<OrderItem> items = new ArrayList<>();

	    public Order() {
	    	// TODO Auto-generated constructor stub
	    }

		public Long getId() {
			return id;
		}
		
		

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getCustomerName() {
			return customerName;
		}

		public String getMobile() {
			return mobile;
		}

		public String getAddress() {
			return address;
		}

		public Double getTotalAmount() {
			return totalAmount;
		}

		public LocalDateTime getOrderDate() {
			return orderDate;
		}

		public List<OrderItem> getItems() {
			return items;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}

		public void setOrderDate(LocalDateTime orderDate) {
			this.orderDate = orderDate;
		}

		public void setItems(List<OrderItem> items) {
			this.items = items;
		}
	    
	    
}
