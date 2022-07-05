package food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FoodDelivery {
	List<String> points = new ArrayList<>();
	Map<String, Executives> deliveryExecutives = new TreeMap<>();
	List<DeliveryHistory> history = new ArrayList<>();
	Map<Integer, Booking> book = new HashMap<>();
	int bookingId;
	int charge = 50;
	int chargeForNearbyDelivery = 5;
	int allowance = 10;

	public FoodDelivery() {
		Executives exe = new Executives();
		exe.setPoint("A");
		exe.setExecutiveNumber("DE1");
		exe.setChargesEarned(0);
		deliveryExecutives.put("DE1", exe);
		Executives exe1 = new Executives("DE2", "A", 0);
		Executives exe2 = new Executives("DE3", "A", 0);
		Executives exe3 = new Executives("DE4", "A", 0);
		deliveryExecutives.put("DE2", exe1);
		deliveryExecutives.put("DE3", exe2);
		deliveryExecutives.put("DE4", exe3);
		points.add("A");
		points.add("B");
		points.add("C");
		points.add("D");
	}

	public String order(int customerId, String restaurantPoint, String destination, long time) throws Exception {
		for (int book_id : book.keySet()) {
			Booking previBooking = book.get(book_id);
			if (previBooking != null && previBooking.getDestination().equals(destination)) {
				if (previBooking.getNextDeliveryLimit() > time) {
					String executive = previBooking.getExecutive();
					Booking obj = new Booking(customerId, bookingId, restaurantPoint, destination, time, executive);
					bookingId++;
					book.put(bookingId, obj);
					Executives executiveObj = deliveryExecutives.get(executive);
					executiveObj.setType(State.BUSY);
					executiveObj.setChargesEarned(chargeForNearbyDelivery);
					DeliveryHistory obj1 = history.get(history.size() - 1);
					int order = obj1.getOrder();
					obj1.setDeliveryCharge(executiveObj.getChargesEarned());
					obj1.setOrder(order + 1);
					return "Alloted delivery executive " + executive;
				}
			}
		}
		Executives exe = lowEarningExecutives(restaurantPoint);
		if (exe == null) {
			throw new Exception("No Idle Executives");
		}
		exe.setChargesEarned(charge + allowance);
		exe.setType(State.BUSY);
		bookingId++;
		Booking obj1 = new Booking(customerId, bookingId, restaurantPoint, destination, time, exe.getExecutiveNumber());
		book.put(bookingId, obj1);
		DeliveryHistory obj = new DeliveryHistory(exe.getExecutiveNumber(), restaurantPoint, destination, 1, time,
				time + 1800000/* for 30min */, exe.getChargesEarned());
		history.add(obj);
		return "Alloted delivery executive " + exe.getExecutiveNumber();
	}

	public Executives lowEarningExecutives(String restaurant) {
		int temp = Integer.MAX_VALUE;
		Executives out = null;
		for (Executives e : deliveryExecutives.values()) {
			int val = e.getChargesEarned();
			if (val < temp && e.getType() == State.IDLE) {
				temp = val;
				out = e;
			}
		}
		return out;
	}

	public String getDeliveryHistory() {
		String out = "";
		out += "Executives\tRestaurant\tDestination\tOrders\tPickup time\tDelivery time\tDelivery Charge\n";
		for (int i = 0; i < history.size(); i++) {
			DeliveryHistory obj = history.get(i);
			out += obj.toString();
		}
		return out;
	}

	public String getExecutiveActivity() {
		String out = "";
		out += "Executives\tDelivery Charge Earned\n";
		for (Executives obj : deliveryExecutives.values()) {
			out += obj.getExecutiveNumber() + "\t\t";
			out += obj.getChargesEarned() + "\n";
		}
		return out;
	}
}
