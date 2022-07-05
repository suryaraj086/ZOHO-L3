package hyperloop;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

class SortByAge implements Comparator<Passenger> {

	public int compare(Passenger a, Passenger b) {

		return a.getAge() - b.getAge();
	}
}

public class Hyperloop {

	List<Passenger> passengerList = new LinkedList<>();

	public void addPassenger(Passenger pass) {
		passengerList.add(pass);
	}

	public void sortList() {
		Collections.sort(passengerList, new SortByAge());
	}

	public Passenger getHighAgePassenger() throws Exception {
		int index = passengerList.size() - 1;
		if (index < 0) {
			throw new Exception("Passengers not found");
		}
		Passenger pass = passengerList.get(index);
		passengerList.remove(index);
		return pass;
	}

	public String passengerQueue() {
		String out = "";
		int size = passengerList.size();
		for (int i = 0; i < size; i++) {
			out += passengerList.get(i).toString();
		}
		return out;
	}

}
