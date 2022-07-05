package food;

public class Booking {
	long customerId;
	long bookingId;
	String restaurant;
	String destination;
	long time;
	long nextDeliveryLimit;
	String executive;

	Booking(long cusId, long id, String res, String des, long currTime, String exe) {
		bookingId = cusId;
		bookingId = id;
		restaurant = res;
		destination = des;
		time = currTime;
		executive = exe;
		nextDeliveryLimit = currTime + 900000;// 15MIN
	}

	public long getNextDeliveryLimit() {
		return nextDeliveryLimit;
	}

	public String getExecutive() {
		return executive;
	}

	public long getBookingId() {
		return bookingId;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public String getDestination() {
		return destination;
	}

	public long getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "Booking [bookingId=" + bookingId + ", restaurant=" + restaurant + ", destination=" + destination
				+ ", time=" + time + "]";
	}

}
