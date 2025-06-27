package ascentAgency.vo;

import java.util.List;

public class CheckoutRequest {
	  private String customerName;
	    private String email;
	    private String address;
	    private Double totalAmount;
	    private String mobile;
	    private List<CheckoutItem> items;
	    
	    
	    
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getCustomerName() {
			return customerName;
		}
		public String getEmail() {
			return email;
		}
		public String getAddress() {
			return address;
		}
		public Double getTotalAmount() {
			return totalAmount;
		}
		public List<CheckoutItem> getItems() {
			return items;
		}
		public void setCustomerName(String customerName) {
			this.customerName = customerName;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public void setTotalAmount(Double totalAmount) {
			this.totalAmount = totalAmount;
		}
		public void setItems(List<CheckoutItem> items) {
			this.items = items;
		}
	    
	    
	    
	    
}
