package zkart;

public class Product implements Cloneable {
	private Category type;
	private String productName;
	private String brandName;
	private int price;
	private int stock;
	private int discount;
	private int lessStock = 5;

	public boolean getLessStock() {
		if (lessStock > stock) {
			return true;
		}
		return false;
	}

	public void setLessStock(int lessStock) throws Exception {
		if (lessStock < 0) {
			throw new Exception("Less number of products");
		}
		this.lessStock = lessStock;
	}

	Product(String name, String brand, int pri, int stk, int dis, Category type) {
		productName = name;
		brandName = brand;
		price = pri;
		stock = stk;
		discount = dis;
		this.type = type;
	}

	public Category getType() {
		return type;
	}

	public void setType(Category type) {
		this.type = type;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) throws Exception {
		if (stock < 0) {
			throw new Exception("Less number of products");
		}
		this.stock = stock;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String toString() {
		return "Category=" + type + ", product Name=" + productName + ", brand Name=" + brandName + ", price=" + price
				+ ", stock=" + stock + ", discount=" + discount + "\n";
	}

}
