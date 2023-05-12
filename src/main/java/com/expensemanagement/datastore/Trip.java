package com.expensemanagement.datastore;


public class Trip {

	private int tripId;
	private String tripName;
	private long fromDate;
	private long toDate;

	public Trip(int tripId, String tripName, long fromDate, long toDate) {
		super();
		this.tripId = tripId;
		this.tripName = tripName;
		this.fromDate = fromDate;
		this.toDate = toDate;
	}

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
		return "Trip [tripId=" + tripId + ", tripName=" + tripName + ", fromDate=" + fromDate + ", toDate=" + toDate
				+ "]";
	}

}
