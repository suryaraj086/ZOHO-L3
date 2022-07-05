package zkart;

public class History {
	Category category;
	String brand;
	String model;
	int price;
	int creditUsed;
	int totalPrice;
	int creditAdded;
	int invoiceNumber;

	public int getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(int invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public History(Category category, String brand, String model, int creditUsed, int totalPrice, int creditAdded,
			int invoiceNumber) {
		super();
		this.category = category;
		this.brand = brand;
		this.model = model;
		this.creditUsed = creditUsed;
		this.totalPrice = totalPrice;
		this.creditAdded = creditAdded;
		this.invoiceNumber = invoiceNumber;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getCreditUsed() {
		return creditUsed;
	}

	public void setCreditUsed(int creditUsed) {
		this.creditUsed = creditUsed;
	}

	public int getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getCreditAdded() {
		return creditAdded;
	}

	public void setCreditAdded(int creditAdded) {
		this.creditAdded = creditAdded;
	}

	@Override
	public String toString() {
		return "History [category=" + category + ", brand=" + brand + ", model=" + model + ", price=" + price
				+ ", creditUsed=" + creditUsed + ", totalPrice=" + totalPrice + ", creditAdded=" + creditAdded + "]";
	}

}
