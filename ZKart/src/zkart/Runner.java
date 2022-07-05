package zkart;

import java.io.IOException;
import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {
		ZKart obj = null;
		try {
			obj = new ZKart();
		} catch (IOException e3) {
			System.out.println(e3.getMessage());
		}
		Scanner scan = new Scanner(System.in);
		boolean temp = true;
		String role = null;
		String name = null;
		while (temp) {
			System.out.println("1.Login\n2.Signup");
			int choice1 = scan.nextInt();
			if (choice1 == 1) {
				scan.nextLine();
				System.out.println("Enter the user name/Email");
				name = scan.nextLine();
				System.out.println("Enter the password");
				String pass = scan.nextLine();
				try {
					role = obj.loginChecker(name, pass);
					temp = false;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					continue;
				}
				boolean bool = true;
				while (bool) {
					switch (role) {
					case "Admin":
						System.out.println(
								"1.Display all stock\n2.Less no of stocks\n3.Add item to stock\n4.Update discount price for product");
						int choice = scan.nextInt();
						switch (choice) {
						case 1:

							System.out.println(obj.displayAllItems());
							break;
						case 2:
							System.out.println(obj.displayLessStocks());
							break;
						case 3:
							scan.nextLine();
							System.out.println("Enter the category");
							String cat1 = scan.nextLine();
							System.out.println("Enter the brand name");
							String brand1 = scan.nextLine();
							System.out.println("Enter the model name");
							String model1 = scan.nextLine();
							System.out.println("Enter the stocks to add");
							int stock1 = scan.nextInt();
							try {
								System.out.println(obj.changeStockPrice(cat1, brand1, model1, stock1));
							} catch (Exception e2) {
								System.out.println(e2.getMessage());
							}
							break;
						case 4:
							scan.nextLine();
							System.out.println("Enter the category");
							String cat = scan.nextLine();
							System.out.println("Enter the brand name");
							String brand = scan.nextLine();
							System.out.println("Enter the model name");
							String model = scan.nextLine();
							System.out.println("Enter the discount price");
							int discount = scan.nextInt();
							try {
								System.out.println(obj.changeDiscountPrice(cat, brand, model, discount));
							} catch (Exception e2) {
								System.out.println(e2.getMessage());
							}
							break;
						default:
							break;
						}
						break;
					case "Customer":
						System.out.println("1.Purchase\n2.buy from cart\n3.Remove from cart");
						int val = scan.nextInt();
						switch (val) {
						case 1:
							scan.nextLine();
							System.out.println(obj.listAllCategory());
							System.out.println("Enter the category to choose");
							String category = scan.nextLine().toUpperCase();
							try {
								System.out.println(obj.listModels(category));
							} catch (Exception e) {
								System.out.println("Invalid category");
								continue;
							}
							System.out.println("Enter the model to choose");
							String model = scan.nextLine();
							try {
								System.out.println(obj.listAllProduct(category, model));
							} catch (Exception e2) {
								System.out.println(e2.getMessage());
							}
							System.out.println("Enter the product number to choose");
							int product = scan.nextInt();
							System.out.println("Enter the how many product you want to buy");
							int stock = scan.nextInt();
							System.out.println("Do you want to add to cart\n1.Yes\n2.No");
							int cart = scan.nextInt();
							switch (cart) {
							case 1:
								try {
									System.out.println(obj.addToKart(category, model, product, name, stock));
								} catch (Exception e1) {
									System.out.println(e1.getMessage());
								}
								break;
							case 2:
								continue;

							default:
								break;
							}
							break;
						case 2:
							try {
								System.out.println("Do you want to user credit\n1.Yes\n2.No");
								int yesorno = scan.nextInt();
								if (yesorno == 1) {
									System.out.println(obj.buyProductFromCart(name, true));
								} else if (yesorno == 2) {
									System.out.println(obj.buyProductFromCart(name, false));
								} else {
									continue;
								}
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						case 3:
							scan.nextLine();
							System.out.println("Enter the product to remove");
							String prodName = scan.nextLine();
							try {
								System.out.println(obj.removeFromCart(prodName, name));
							} catch (Exception e) {
								System.out.println(e.getMessage());
							}
							break;
						default:
							break;
						}
						break;
					default:
						bool = false;
						break;
					}
				}
			} else if (choice1 == 2) {
				scan.nextLine();
				System.out.println("Enter the name");
				String name1 = scan.nextLine();
				System.out.println("Enter the password");
				String pass = scan.nextLine();
				System.out.println("Enter the email id");
				String email = scan.nextLine();
				System.out.println("Enter the mobile number");
				long mobilenum = scan.nextLong();
				UserDetails user = new UserDetails(name1, pass, email, mobilenum, 0, false);
				obj.addNewUser(user);
			} else {
				System.out.println("Enter the valid number");
			}
		}
		scan.close();
	}

}
