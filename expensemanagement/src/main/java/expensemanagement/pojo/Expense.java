package expensemanagement.pojo;

public class Expense {

	private int fromUserId;
	private int toUserId;
	private float amount;
	private String description;
	private int tripId;
	private int expenseId;

	public Expense(int fromUserId, int toUserId, float amount, String description, int tripId, int expenseId) {
		super();
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.amount = amount;
		this.description = description;
		this.tripId = tripId;
		this.expenseId = expenseId;
	}

	public int getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(int expenseId) {
		this.expenseId = expenseId;
	}

	public int getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(int fromUserId) {
		this.fromUserId = fromUserId;
	}

	public int getToUserId() {
		return toUserId;
	}

	public void setToUserId(int toUserId) {
		this.toUserId = toUserId;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	@Override
	public String toString() {
		return "Expense [fromUserId=" + fromUserId + ", toUserId=" + toUserId + ", amount=" + amount + ", description="
				+ description + ", tripId=" + tripId + ", expenseId=" + expenseId + "]";
	}

}
