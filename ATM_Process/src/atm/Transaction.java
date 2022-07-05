package atm;

public class Transaction {
	int transactionNumber;
	String description;
	TransactionType type;
	long amount;
	long closingBalance;
	long customerId;
	long accNo;

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public Transaction(int transactionNum, String descri, TransactionType type, long amounts, long balance,
			long customerId, long accNo) {
		transactionNumber = transactionNum;
		description = descri;
		this.type = type;
		amount = amounts;
		closingBalance = balance;
		this.customerId = customerId;
		this.accNo = accNo;
	}

	public long getAccNo() {
		return accNo;
	}

	public void setAccNo(long accNo) {
		this.accNo = accNo;
	}

	public int getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(int transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(long closingBalance) {
		this.closingBalance = closingBalance;
	}

	@Override
	public String toString() {
		return "\t" + transactionNumber + "\t\t" + description + "\t" + type + "\t\t" + amount + "\t" + closingBalance
				+ "\n";
	}

}
