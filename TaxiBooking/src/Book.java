
public class Book {

	int bookingId;
	boolean shareStatus;
	char destination;
	long pickuptime;
	char source;
	String taxiName;

	public Book(int bookingId, boolean shareStatus, char destination, long pickuptime, char source, String taxiName) {
		this.bookingId = bookingId;
		this.shareStatus = shareStatus;
		this.destination = destination;
		this.pickuptime = pickuptime;
		this.source = source;
		this.taxiName = taxiName;
	}

	public String getTaxiName() {
		return taxiName;
	}

	public void setTaxiName(String taxiName) {
		this.taxiName = taxiName;
	}

	public char getSource() {
		return source;
	}

	public void setSource(char source) {
		this.source = source;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public boolean isShareStatus() {
		return shareStatus;
	}

	public void setShareStatus(boolean shareStatus) {
		this.shareStatus = shareStatus;
	}

	public char getDestination() {
		return destination;
	}

	public void setDestination(char destination) {
		this.destination = destination;
	}

	public long getPickuptime() {
		return pickuptime;
	}

	public void setPickuptime(long pickuptime) {
		this.pickuptime = pickuptime;
	}

	@Override
	public String toString() {
		return "  " + bookingId + "\t" + source + "\t" + destination + "\t" + pickuptime + "\t" + shareStatus + "\t"
				+ taxiName + "";
	}

}
