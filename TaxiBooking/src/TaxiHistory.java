
public class TaxiHistory {
	private int Bookingid;
	private int customerid;
	private char pickupLocation;
	private char dropLocation;
	private boolean shareStatus;
	private long pickuptime;
	private int amount;

	public TaxiHistory(int bookingid, int customerid, char pickupLocation, char dropLocation, long pickuptime,
			int amount, boolean shareStatus) {
		Bookingid = bookingid;
		this.customerid = customerid;
		this.pickupLocation = pickupLocation;
		this.dropLocation = dropLocation;
		this.pickuptime = pickuptime;
		this.amount = amount;
		this.shareStatus = shareStatus;
	}

	public int getBookingid() {
		return Bookingid;
	}

	public void setBookingid(int bookingid) {
		Bookingid = bookingid;
	}

	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public char getPickupLocation() {
		return pickupLocation;
	}

	public void setPickupLocation(char pickupLocation) {
		this.pickupLocation = pickupLocation;
	}

	public char getDropLocation() {
		return dropLocation;
	}

	public void setDropLocation(char dropLocation) {
		this.dropLocation = dropLocation;
	}

	public long getPickuptime() {
		return pickuptime;
	}

	public void setPickuptime(long pickuptime) {
		this.pickuptime = pickuptime;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "  " + Bookingid + "\t\t" + pickupLocation + "\t\t" + dropLocation + "\t\t" + pickuptime + "\t"
				+ shareStatus + "\t\t" + amount + "\n";
	}
}
