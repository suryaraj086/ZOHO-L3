package atm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ATM_Process extends Thread {

	Map<Long, CustomerDetails> map = new HashMap<>();
	Map<Long, List<Transaction>> transaction = new HashMap<>();
	int transactionNumber = 1000;

	public ATM_Process() throws NumberFormatException, Exception {
//		CustomerDetails cus = new CustomerDetails();
//		cus.setAccNo(1);
//		cus.setAccountBalance(5000);
//		cus.setAccountHolder("Surya");
//		cus.setPinNumber(1234);
//		cus.setCustomerId(1);
//		long val = 1;
//		map.put(val, cus);
		loadAtMCashFromFile();
		loadCustomerDetailsFromFile();
	}

	public void run() {
		try {
			sleep(5000);
			System.out.println("Thread after delay");
			storeTransactionToFile();
			interrupt();

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void loadToATM(int twoThosand, int fiveHundred, int hundred) throws Exception {
		Amount.Instance.setTwoThosandCount(twoThosand);
		Amount.Instance.setFiveHundredCount(fiveHundred);
		Amount.Instance.setHundredCount(hundred);
	}

	public void moneyDistribution(long amount) throws Exception {
		long total = 0;
		amountChecker(amount);
		Amount.Instance.setPreviousCount();
		if (amount > Amount.Instance.calculateTotal()) {
			throw new Exception("Insufficient Balance At ATM");
		}
		if (amount <= 5000) {
			if (amount > 3000) {
				int twoCount = Amount.Instance.getTwoThosandCount();
				total += (2000 * 1);
				Amount.Instance.setTwoThosandCount(twoCount - 1);
				total = fiveHunderdCalculator(total, amount);
				total = HunderdCalculator(total, amount);
			} else {
				if (amount <= 1000) {
					try {
						total = HunderdCalculator(total, amount);
					} catch (Exception e) {
						Amount.Instance.restoreOldCount();
						throw new Exception(e.getMessage());
					}
				} else {
					total = fiveHunderdCalculator(total, amount);
					try {
						total = HunderdCalculator(total, amount);
					} catch (Exception e) {
						Amount.Instance.restoreOldCount();
						throw new Exception(e.getMessage());
					}
				}
			}
		} else {
			int twoCount = Amount.Instance.getTwoThosandCount();
			total += (2000 * 2);
			Amount.Instance.setTwoThosandCount(twoCount - 2);
			total = fiveHunderdCalculator(total, amount);
			try {
				total = HunderdCalculator(total, amount);
			} catch (Exception e) {
				Amount.Instance.restoreOldCount();
				throw new Exception(e.getMessage());
			}
		}
	}

	public long fiveHunderdCalculator(long amount, long reachValue) throws Exception {
		long temp = 0;
		while (true) {
			if (amount < reachValue) {
				temp = amount;
			} else {
				return temp;
			}
			int twoCount = Amount.Instance.getFiveHundredCount();
			amount += 500 * 1;
			try {
				Amount.Instance.setFiveHundredCount(twoCount - 1);
			} catch (Exception e) {
				return temp;
			}
		}
	}

	public long HunderdCalculator(long amount, long reachValue) throws Exception {
		long temp = 0;
		while (true) {
			if (amount < reachValue) {
				temp = amount;
			} else {
				return temp;

			}
			int twoCount = Amount.Instance.getHundredCount();
			amount += 100 * 1;
			Amount.Instance.setHundredCount(twoCount - 1);
		}
	}

	public String withdrawAmount(long accNo, long amount, int pin) throws Exception {
		CustomerDetails obj = map.get(accNo);
		nullChecker(obj);
		obj.pinChecker(pin);
		long balance = obj.getAccountBalance();
		balanceChecker(amount, balance);
		moneyDistribution(amount);
		Transaction tran = new Transaction(transactionNumber++, "Cash Withdrawal", TransactionType.debit, amount,
				balance - amount, obj.getCustomerId(), obj.getAccNo());
		List<Transaction> arr = transaction.get(accNo);
		if (arr == null) {
			arr = new ArrayList<>();
		}
		arr.add(tran);
		transaction.put(accNo, arr);
		balance -= amount;
		obj.setAccountBalance(balance);
		start();
		setName("Asynchronous");
		return "Withdraw Successful";
	}

	public void balanceChecker(long amount, long balance) throws Exception {
		if (balance < amount) {
			throw new Exception("Insufficient Balance");
		}
	}

	public void nullChecker(Object inp) throws Exception {
		if (inp == null) {
			throw new Exception("Account not found");
		}
	}

	public String TransferAccount(long fromAcc, long toAcc, long amount, int pin) throws Exception {
		CustomerDetails fromUser = map.get(fromAcc);
		nullChecker(fromUser);
		fromUser.pinChecker(pin);
		long fromBalance = fromUser.getAccountBalance();
		balanceChecker(amount, fromBalance);
		CustomerDetails toUser = map.get(toAcc);
		nullChecker(toUser);
		long toBalance = toUser.getAccountBalance();
		long newFromBal = fromBalance - amount;
		long newToBal = toBalance += amount;
		fromUser.setAccountBalance(newFromBal);
		toUser.setAccountBalance(newToBal);
		transferHistory(fromAcc, "Transfer to " + toAcc, TransactionType.debit, amount, fromBalance,
				fromUser.getCustomerId());
		transferHistory(toAcc, "Transfer from " + fromAcc, TransactionType.credit, amount, toBalance,
				toUser.getCustomerId());
		start();
		setName("Asynchronous");
		return "Transaction Successful";
	}

	public void transferHistory(long accNo, String description, TransactionType type, long amount, long balance,
			long id) {
		Transaction tran = new Transaction(transactionNumber++, description, type, amount, balance, id, accNo);
		List<Transaction> arr = transaction.get(accNo);
		if (arr == null) {
			arr = new ArrayList<>();
		}
		arr.add(tran);
		transaction.put(accNo, arr);
	}

	public long checkBalance(long accNo) throws Exception {
		CustomerDetails obj = map.get(accNo);
		nullChecker(obj);
		return obj.getAccountBalance();
	}

	public String printMiniStatement(long accNo) throws Exception {
		List<Transaction> list = transaction.get(accNo);
		String out = "";
		nullChecker(list);
		out += "Transaction number\tDescription\tDebit/Credit\tAmount\tClosing Balance\n";
		for (int i = 0; i < list.size(); i++) {
			out += list.get(i).toString();
		}
		return out;
	}

	public void amountChecker(long amount) throws Exception {
		if (amount >= 10000) {
			throw new Exception("Maximum withdrawal limit for a single transaction is 10,000₹");
		} else if (amount <= 100) {
			throw new Exception("Minimum withdrawal limit for a single transaction is 100₹");
		}
	}

	public String getCustomerDetails() {
		String out = "";
		for (CustomerDetails obj : map.values()) {
			out += obj.toString();
		}
		return out;
	}

	public String checkATMBalance() {
		String out = "";
		int twoThosand = Amount.Instance.getTwoThosandCount();
		int fiveHundred = Amount.Instance.getFiveHundredCount();
		int hundred = Amount.Instance.getHundredCount();
		out += "Description\tNumber\tValue\n";
		out += "2000" + "\t\t" + twoThosand + "\t" + (2000 * twoThosand) + "\n";
		out += "500" + "\t\t" + fiveHundred + "\t" + (500 * fiveHundred) + "\n";
		out += "100" + "\t\t" + hundred + "\t" + (100 * hundred) + "\n \n";
		out += "Total amount available in the ATM = " + Amount.Instance.calculateTotal();
		return out;
	}

	public void storeTransactionToFile() throws IOException {
		FileWriter fw = null;
		for (List<Transaction> arr : transaction.values()) {
			Transaction obj1 = arr.get(0);
			File f = new File(obj1.getAccNo() + "_transacion.txt");
			if (!f.exists()) {
				f.createNewFile();
			}
			fw = new FileWriter(f, true);
//			fw.write("Transaction number\tDescription\tDebit/Credit\tAmount\tClosing Balance\n");
			for (int i = 0; i < arr.size(); i++) {
				Transaction obj = arr.get(i);
				fw.write(obj.toString());
			}
			fw.close();
		}
	}

	public void storeCustomerDetailsToFile() throws IOException {
		File f = new File("Customerdetails.txt");
		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f);
		for (CustomerDetails obj : map.values()) {
			fw.write(obj.toString());
			fw.close();
		}
	}

	public void storeAtMCashToFile() throws IOException {
		File f = new File("ATM.txt");
		if (!f.exists()) {
			f.createNewFile();
		}
		FileWriter fw = new FileWriter(f);
		fw.write(checkATMBalance());
		fw.close();
	}

	public void loadAtMCashFromFile() throws NumberFormatException, Exception {
		FileReader fr = new FileReader("ATM.txt");
		try (BufferedReader br = new BufferedReader(fr)) {
			String strCurrentLine = "";
			int i;
			br.readLine();
			while ((i = br.read()) != -1) {
				strCurrentLine += (char) i;
			}
			String[] arr = strCurrentLine.split("\t");
			for (i = 0; i < arr.length; i++) {
				if (i == 2) {
					Amount.Instance.setTwoThosandCount(Integer.parseInt(arr[i]));
				} else if (i == 5) {
					Amount.Instance.setFiveHundredCount(Integer.parseInt(arr[i]));
				} else if (i == 8) {
					Amount.Instance.setHundredCount(Integer.parseInt(arr[i]));
					br.close();
					return;
				}
			}
		}
	}

	public void loadCustomerDetailsFromFile() throws IOException {
		FileReader fr = new FileReader("Customerdetails.txt");
		try (BufferedReader br = new BufferedReader(fr)) {
			String strCurrentLine = "";
			int i;
			while ((i = br.read()) != -1) {
				if (i == 10) {
					strCurrentLine += "\t  \t";
					continue;
				}
				strCurrentLine += (char) i;
			}
			String[] arr = strCurrentLine.split("\t");
			long accNo = Long.parseLong(arr[0]);
			String name = arr[1];
			int pin = Integer.parseInt(arr[2]);
			long amount = Long.parseLong(arr[3]);
			CustomerDetails cus = new CustomerDetails();
			cus.setAccNo(accNo);
			cus.setAccountHolder(name);
			cus.setAccountBalance(amount);
			cus.setPinNumber(pin);
			map.put(accNo, cus);
			int count = 5, count1 = 6, count2 = 7, count3 = 8;
			for (i = 0; i < arr.length; i++) {
				if (i == count) {
					accNo = Integer.parseInt(arr[i]);
					continue;
				} else if (i == count1) {
					name = arr[i];
					continue;
				} else if (i == count2) {
					pin = Integer.parseInt(arr[i]);
					continue;
				} else if (i == count3) {
					amount = Long.parseLong(arr[i]);
					continue;
				} else if (i == count3 + 1) {
					CustomerDetails cus1 = new CustomerDetails();
					cus1.setAccNo(accNo);
					cus1.setAccountHolder(name);
					cus1.setAccountBalance(amount);
					cus1.setPinNumber(pin);
					map.put(accNo, cus1);
					count += 5;
					count1 += 5;
					count2 += 5;
					count3 += 5;
				}
			}
		}
	}

}