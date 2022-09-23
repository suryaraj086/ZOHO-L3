package expensemanagement.pojo;

import java.util.List;

public class Trip {

	private int tripId;
	private String tripName;
	private List<Integer> members;
	private long fromDate;
	private long toDate;
	
	
	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getTripName() {
		return tripName;
	}

	public void setTripName(String tripName) {
		this.tripName = tripName;
	}

	public List<Integer> getMembers() {
		return members;
	}

	public void setMembers(List<Integer> members) {
		this.members = members;
	}

	public long getFromDate() {
		return fromDate;
	}

	public void setFromDate(long fromDate) {
		this.fromDate = fromDate;
	}

	public long getToDate() {
		return toDate;
	}

	public void setToDate(long toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "Trip [tripId=" + tripId + ", tripName=" + tripName + ", members=" + members + ", fromDate=" + fromDate
				+ ", toDate=" + toDate + "]";
	}

}
