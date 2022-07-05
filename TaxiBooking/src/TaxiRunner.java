import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class TaxiRunner {

	public static void main(String[] args) {
		TaxiBooking tObj = new TaxiBooking();
		System.out.println("Enter number of taxi's you want to create");
		Scanner scan = new Scanner(System.in);
		int taxi = scan.nextInt();
		Taxi taxiobj = new Taxi();
		tObj.createTaxi(taxiobj);
		for (int i = 0; i < taxi - 1; i++) {
			Taxi taxipojo = new Taxi();
			tObj.createTaxi(taxipojo);
		}
		boolean bool = true;
		while (bool) {
			System.out.println(
					"-------------------------------\n1.Book taxi\n2.Print taxi details\n3.Change state to idle\n---------------------------------");
			int val = scan.nextInt();
			switch (val) {
			case 1:
				System.out.println("Enter the Customer id");
				int id = scan.nextInt();
				System.out.println("Enter from location");
				char from = scan.next().toUpperCase().charAt(0);
				System.out.println("Enter to location");
				char to = scan.next().toUpperCase().charAt(0);
				scan.nextLine();
				System.out.println("Enter the time(The time should be HH:MM)");
				String time = scan.nextLine();
				SimpleDateFormat s = new SimpleDateFormat("hh:mm");
				Date date = null;
				try {
					date = s.parse(time);
				} catch (ParseException e) {
					System.out.println(e.getMessage());
					continue;
				}
				int km = tObj.distanceCalculator(from, to);
				if (km >= 45) {
					System.out.println("Do you want to share the taxi\n1.Yes\n2.No");
					int choice = scan.nextInt();
					if (choice == 1) {
						System.out.println(tObj.bookTaxi(date.getTime(), from, to, id, true));
					} else {
						System.out.println(tObj.bookTaxi(date.getTime(), from, to, id, false));
					}
				} else {
					boolean temp = tObj.checkTimeAndDestination(from, to, date.getTime());
					if (temp == true) {
						System.out.println("Do you want to share the taxi\n1.Yes\n2.No");
						int val1 = scan.nextInt();
						if (val1 == 1) {
							System.out.println(tObj.bookShareTicket(from, to, date.getTime(), id));
						} else {
							System.out.println(tObj.bookTaxi(date.getTime(), from, to, id, false));
						}
						continue;
					} else {
						System.out.println(tObj.bookTaxi(date.getTime(), from, to, 1, false));
					}
				}
				break;
			case 2:
				System.out.println(tObj.printTaxiDetails());
				break;
			case 3:
				scan.nextLine();
				System.out.println("Enter the taxi number to change status");
				String taxino = scan.nextLine();
				tObj.changeStatus(taxino);
				break;
			default:
				bool = false;
				break;
			}
		}
		scan.close();
	}
}
