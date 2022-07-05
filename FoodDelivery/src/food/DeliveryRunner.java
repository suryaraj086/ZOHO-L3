package food;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DeliveryRunner {

	public static void main(String[] args) {
		FoodDelivery obj = new FoodDelivery();
		Scanner scan = new Scanner(System.in);
		boolean bool = true;
		while (bool) {
			System.out.println("1.Book a deilvery\n2.Delivery Executive activity \n3.Delivery history");
			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				System.out.println("Enter the customer id");
				int id = scan.nextInt();
				scan.nextLine();
				System.out.println("Enter the restaurant");
				String restaurantLoc = scan.nextLine();
				System.out.println("Enter the delivery location");
				String delivery = scan.nextLine();
				System.out.println("Enter the time(The time should be HH:MM)");
				String time = scan.nextLine();
				SimpleDateFormat s = new SimpleDateFormat("hh:mm");
				Date date = null;
				try {
					date = s.parse(time);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				try {
					System.out.println(obj.order(id, restaurantLoc, delivery, date.getTime()));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				System.out.println(obj.getExecutiveActivity());
				break;
			case 3:
				System.out.println(obj.getDeliveryHistory());
				break;
			default:
				bool = false;
				break;
			}
		}
		scan.close();
	}
}