
public class Taxi {

	private String taxiNumber;
	private int amountEarned;
	private char position = 'A';
	private TaxiState status = TaxiState.IDLE;

	public TaxiState getStatus() {
		return status;
	}

	public void setStatus(TaxiState status) {
		this.status = status;
	}

	public String getTaxiNumber() {
		return taxiNumber;
	}

	public void setTaxiNumber(String taxiNumber) {
		this.taxiNumber = taxiNumber;
	}

	public int getAmountEarned() {
		return amountEarned;
	}

	public void setAmountEarned(int amountEarned) {
		this.amountEarned = amountEarned;
	}

	public char getPosition() {
		return position;
	}

	public void setPosition(char position) {
		this.position = position;
	}

}
