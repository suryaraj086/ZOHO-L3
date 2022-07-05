package atm;

public class CustomerDetails {
	long accNo;
	String accountHolder;
	int pinNumber;
	long accountBalance;
	long customerId;

	public void pinChecker(int pin) throws Exception {
		if (pin != pinNumber) {
			throw new Exception("Invalid pin");
		}
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getAccNo() {
		return accNo;
	}

	public void setAccNo(long accNo) {
		this.accNo = accNo;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public int getPinNumber() {
		return pinNumber;
	}

	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}

	public long getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(long accountBalance) {
		this.accountBalance = accountBalance;
	}

	@Override
	public String toString() {
		return "" + accNo + "\t" + accountHolder + "\t" + pinNumber + "\t" + accountBalance + "\n";
	}

}
