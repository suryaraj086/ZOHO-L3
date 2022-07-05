import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TaxiBooking {

	List<Taxi> taxi = new ArrayList<>();
	List<Character> points = new ArrayList<>();
	List<Passenger> passenger = new ArrayList<Passenger>();
	Map<String, List<TaxiHistory>> history = new HashMap<>();
	Map<Character, List<Book>> bookMap = new HashMap<>();

	public TaxiBooking() {
		points.add('A');
		points.add('B');
		points.add('C');
		points.add('D');
		points.add('E');
		points.add('F');
	}

	int val = 1;
	int bookingId = 1;
	int discount = 40;

	public void createTaxi(Taxi taxiDetails) {
		taxiDetails.setTaxiNumber("T" + val++);
		taxi.add(taxiDetails);
		history.put(taxiDetails.getTaxiNumber(), new ArrayList<>());
	}

	int k = 0;

	public String bookTaxi(long pickupTime, char currPoint, char toPoint, int customerid, boolean shareStatus) {
		if (!(currPoint >= 'A') || !(currPoint <= 'F') || !(toPoint >= 'A') || !(toPoint <= 'F')) {
			return "not valid location";
		}
		List<Taxi> temp = new ArrayList<Taxi>();
		for (int i = 0; i < taxi.size(); i++) {
			if (getIdleTaxi(taxi.get(i))) {
				temp.add(taxi.get(i));
			}
		}
		if (temp.size() == 0) {
			return "No Taxi Found";
		}
		Taxi booked = getNearestTaxi(temp, currPoint);
		int amount = paymentCalculator(currPoint, toPoint);
		int earned = booked.getAmountEarned();
		bookingSetter(booked, toPoint, earned, amount);
		Book book = new Book(bookingId++, shareStatus, toPoint, pickupTime, currPoint, booked.getTaxiNumber());
		List<Book> arr = bookMap.get(toPoint);
		if (arr == null) {
			arr = new ArrayList<>();
		}
		arr.add(book);
		bookMap.put(toPoint, arr);
		if (shareStatus == true) {
			amount = discountCalculator(amount, discount);
		}
		TaxiHistory temp1 = historySetter(pickupTime, currPoint, toPoint, amount, k++, customerid,
				booked.getTaxiNumber(), shareStatus);
		addToHistory(temp1, booked.getTaxiNumber());
		return "Booked successfully and the amount to pay is " + amount + ", alloted taxi is " + booked.getTaxiNumber();
	}

	public String bookShareTicket(char fromPoint, char point, long time, int customerId) {
		List<Book> arr = bookMap.get(point);
		String name = "";
		int amount = paymentCalculator(fromPoint, point);
		for (int i = 0; i < arr.size(); i++) {
			Book obj = arr.get(i);
			Boolean bool = timeCalculator(obj.getSource(), obj.getPickuptime(), point, time);
			if (obj.getDestination() == point && bool) {
				amount = discountCalculator(amount, discount);
				name = obj.getTaxiName();
				TaxiHistory temp1 = historySetter(time, fromPoint, point, amount, k++, customerId, name,
						obj.isShareStatus());
				addToHistory(temp1, obj.getTaxiName());
				return "Booked successfully and the amount to pay is " + amount + ",Allocated taxi is " + name;
			}
		}
		return "Booked successfully and the amount to pay is " + amount + ",Allocated taxi is " + name;
	}

	public boolean checkTimeAndDestination(char from, char point, long time) {
		List<Book> arr = bookMap.get(point);
		if (arr == null) {
			return false;
		}
		for (int i = 0; i < arr.size(); i++) {
			Book obj = arr.get(i);
			Boolean bool = timeCalculator(obj.getSource(), obj.getPickuptime(), from, time);
			if (obj.getDestination() == point && bool && obj.isShareStatus()) {
				return true;
			}
		}
		return false;
	}

	public int discountCalculator(int amount, int discount) {
		double val = amount * (discount / 100.0);
		return (int) val;
	}

	public boolean timeCalculator(char pickupPoint, long pickup, char point, long customer2PickupTime) {
		int val = Math.abs(pickupPoint - point);
		long reachTime = pickup + (val * 900000);
		if (reachTime >= customer2PickupTime) {
			return true;
		}
		return false;
	}

	public void bookingSetter(Taxi booked, char toPoint, int earned, int amount) {
		booked.setStatus(TaxiState.BUSY);
		booked.setPosition(toPoint);
		booked.setAmountEarned(earned + amount);
	}

	public void addToHistory(TaxiHistory temp1, String taxiNumber) {
		List<TaxiHistory> arr = history.get(taxiNumber);
		arr.add(temp1);
		history.put(taxiNumber, arr);
	}

	public TaxiHistory historySetter(long pickuptime, char fromLocation, char toLocation, int amount, int bookingid,
			int customerid, String taxiName, boolean shareStatus) {
		TaxiHistory history1 = new TaxiHistory(bookingid, customerid, fromLocation, toLocation, pickuptime, amount,
				shareStatus);
		return history1;
	}

	public int paymentCalculator(char currPoint, char toPoint) {
		int pay = 50;
		int fromindex = points.indexOf(currPoint);
		int toindex = points.indexOf(toPoint);
		int val = Math.abs(fromindex - toindex) - 1;
		pay += 10 * 10;
		pay += (val * 10 * 15);
		return pay;
	}

	public Taxi getNearestTaxi(List<Taxi> list, char pickup) {
		if (list.size() == 1) {
			return list.get(0);
		}
		int min = Integer.MAX_VALUE;
		Taxi temp = null;
		List<Taxi> arr = new ArrayList<Taxi>();
		for (int i = 0; i < list.size(); i++) {
			Taxi obj = list.get(i);
			int val = Math.abs(obj.getPosition() - pickup);
			if (val <= min) {
				if (val == min) {
					arr.add(temp);
					arr.add(obj);
				} else {
					arr.clear();
				}
				min = Math.min(val, min);
				temp = obj;
			}
		}
		if (arr.size() != 0) {
			temp = lowestEarning(arr);
		}
		return temp;
	}

	public Taxi lowestEarning(List<Taxi> list) {
		int temp = Integer.MAX_VALUE;
		Taxi out = null;
		for (int i = 0; i < list.size(); i++) {
			Taxi obj = list.get(i);
			if (obj.getAmountEarned() < temp) {
				temp = obj.getAmountEarned();
				out = obj;
			}
		}
		return out;
	}

	public boolean getIdleTaxi(Taxi taxiObj) {
		if (taxiObj.getStatus() == TaxiState.IDLE) {
			return true;
		}
		return false;
	}

	public void changeStatus(String taxino) {
		for (int i = 0; i < taxi.size(); i++) {
			Taxi obj = taxi.get(i);
			if (obj.getTaxiNumber().equals(taxino)) {
				obj.setStatus(TaxiState.IDLE);
			}
		}
	}

	public int distanceCalculator(char from, char to) {
		return Math.abs(from - to) * 15;
	}

	public boolean timeChecker(long pickuptime, Taxi taxiObj, char pickupPoint) {
		char temp = taxiObj.getPosition();
		long currtime = 0;
		int fromindex = points.indexOf(temp);
		int toindex = points.indexOf(pickupPoint);
		if (fromindex > toindex) {
			for (int i = fromindex - 1; i >= toindex; i--) {
				currtime += 900000;
			}
		} else {
			for (int i = toindex - 1; i >= fromindex; i--) {
				currtime += 900000;
			}
		}
		if (pickuptime < currtime) {
			return false;
		}
		return true;
	}

	public String printTaxiDetails() {
		String out = "";
		out += "\t\tBookingid\tStart point\tEnd point\tStart time\tBooking type\tCharges\n";
		Set<String> key = history.keySet();
		for (String val : key) {
			out += "Taxi number " + val + "\n";
			out += " " + history.get(val).toString() + "\n";
		}
		return out;
	}

}