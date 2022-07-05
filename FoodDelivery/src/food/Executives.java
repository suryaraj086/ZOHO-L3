package food;

public class Executives {
	String executiveNumber;
	String point;
	int chargesEarned;
	State type;

	public Executives() {
		type = State.IDLE;
	}

	public State getType() {
		return type;
	}

	public void setType(State type) {
		this.type = type;
	}

	public Executives(String num, String pnt, int charge) {
		executiveNumber = num;
		point = pnt;
		chargesEarned = charge;
		type = State.IDLE;
	}

	public String getExecutiveNumber() {
		return executiveNumber;
	}

	public void setExecutiveNumber(String executiveNumber) {
		this.executiveNumber = executiveNumber;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public int getChargesEarned() {
		return chargesEarned;
	}

	public void setChargesEarned(int chargesEarned) {
		this.chargesEarned += chargesEarned;
	}

	@Override
	public String toString() {
		return "Executives [executiveNumber=" + executiveNumber + ", point=" + point + ", chargesEarned="
				+ chargesEarned + "]";
	}

}
