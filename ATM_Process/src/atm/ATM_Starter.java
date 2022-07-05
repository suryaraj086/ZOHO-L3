package atm;

import java.util.Scanner;

public class ATM_Starter {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		ATM_Process obj = null;
		try {
			obj = new ATM_Process();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		boolean bool = true;
		while (bool) {
			System.out.println(
					"------------------\n1.Load cash to ATM\n2.Show customer details\n3.Show ATM Operations\n4.Store to file ");
			System.out.println("------------------");
			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter the number of 2000's");
				int twoThousands = scan.nextInt();
				System.out.println("Enter the number of 500's");
				int fiveHundreds = scan.nextInt();
				System.out.println("Enter the number of 100's");
				int hundreds = scan.nextInt();
				try {
					obj.loadToATM(twoThousands, fiveHundreds, hundreds);
					obj.storeAtMCashToFile();
				} catch (Exception e1) {
					System.out.println(e1.getMessage());
				}
				break;
			case 2:
				System.out.println(obj.getCustomerDetails());
				break;
			case 3:
				System.out.println("1. Check Balance\n" + "2. Withdraw Money\n" + "3. Transfer Money\n"
						+ "4. Check ATM Balance\n" + "5. Mini Statement");
				int atmProcess = scan.nextInt();
				switch (atmProcess) {
				case 1:
					System.out.println("Enter the account number");
					long accNo = scan.nextLong();
					try {
						System.out.println("The balance amount is " + obj.checkBalance(accNo));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				case 2:
					System.out.println("Enter the account number");
					long accountNumber = scan.nextLong();
					System.out.println("Enter the amount");
					int amount = scan.nextInt();
					System.out.println("Enter the pin number");
					int pin = scan.nextInt();
					try {
						System.out.println("-------" + obj.withdrawAmount(accountNumber, amount, pin) + "------");
						obj.storeAtMCashToFile();
						obj.storeCustomerDetailsToFile();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				case 3:
					System.out.println("Enter the your account");
					long fromAcc = scan.nextLong();
					System.out.println("Enter the transfering account");
					long toAcc = scan.nextLong();
					System.out.println("Enter the amount");
					long amount1 = scan.nextLong();
					System.out.println("Enter the pin number");
					int pin1 = scan.nextInt();
					try {
						System.out.println(obj.TransferAccount(fromAcc, toAcc, amount1, pin1));
						obj.storeCustomerDetailsToFile();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					break;
				case 4:
					System.out.println(obj.checkATMBalance());
					break;
				case 5:
					System.out.println("Enter the account number");
					long number = scan.nextLong();
					try {
						System.out.println(obj.printMiniStatement(number));
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					break;

				default:
					break;
				}
				break;
			case 4:
				try {
					obj.storeCustomerDetailsToFile();
					obj.storeTransactionToFile();
					obj.storeAtMCashToFile();
				} catch (Exception e) {
					e.getMessage();
				}
				break;
			default:

				break;
			}
		}
		scan.close();
	}
}