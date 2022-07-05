package food;

public class DeliveryHistory {

	String executives;
	String restaurant;
	String destination;
	int order;
	long pickupTime;
	long deliveryTime;
	int deliveryCharge;

	public DeliveryHistory(String exe, String res, String des, int order, long pickup, long delivery, int charge) {
		executives = exe;
		restaurant = res;
		destination = des;
		this.order = order;
		pickupTime = pickup;
		deliveryTime = delivery;
		deliveryCharge = charge;
	}

	public String getExecutives() {
		return executives;
	}

	public void setExecutives(String executives) {
		this.executives = executives;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public long getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(long pickupTime) {
		this.pickupTime = pickupTime;
	}

	public long getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(long deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public int getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(int deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	@Override
	public String toString() {
		return "" + executives + "\t\t" + restaurant + "\t\t" + destination + "\t\t" + order + "\t" + pickupTime + "\t"
				+ deliveryTime + "\t" + deliveryCharge + "\n";
	}

}
