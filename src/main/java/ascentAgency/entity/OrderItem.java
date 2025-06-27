package ascentAgency.entity;

public class OrderItem {
	
	    private Long id;
	    private String productName;
	    private Double price;
	    private Integer quantity;
		public Long getId() {
			return id;
		}
		public String getProductName() {
			return productName;
		}
		public Double getPrice() {
			return price;
		}
		public Integer getQuantity() {
			return quantity;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public void setProductName(String productName) {
			this.productName = productName;
		}
		public void setPrice(Double price) {
			this.price = price;
		}
		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}
		@Override
		public String toString() {
			return "OrderItem [id=" + id + ", productName=" + productName + ", price=" + price + ", quantity="
					+ quantity + "]";
		}
	    
	    
		
		

}
