package ascentAgency.vo;

public class CheckoutItem {

	private String productName;
    private Double price;
    private Integer quantity;
    
    
	public String getProductName() {
		return productName;
	}
	public Double getPrice() {
		return price;
	}
	public Integer getQuantity() {
		return quantity;
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
    
    

}
