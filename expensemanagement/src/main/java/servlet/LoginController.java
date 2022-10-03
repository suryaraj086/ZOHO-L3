package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import exception.CustomException;
import expensemanagement.*;
import expensemanagement.pojo.Trip;
import expensemanagement.pojo.User;
import com.google.gson.*;
import com.google.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		JSONObject obj = null;
		try {
			obj = getJson(request);
		} catch (IOException | org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}

		String id = (String) obj.get("id");
		int uId = 0;
		String password = (String) obj.get("password");
		String page = (String) obj.get("page");
		Manager manager = (Manager) request.getServletContext().getAttribute("logic");
		Map<Integer, User> userMap = manager.getUserMap();
		request.setAttribute("LoginController", userMap);
		System.out.println(obj.get("page"));
		System.out.println(obj.get("id"));
		System.out.println(obj.get("password"));
		try {

			if (id != null && password != null && manager.login(Integer.parseInt(id), password)) {
				uId = Integer.parseInt(id);
				HttpSession session = request.getSession();
				session.setAttribute("userId", uId);
				response.getWriter().write("Addfriends.jsp");
				response.getWriter().write(new Gson().toJson("Addfriends.jsp"));
//				response.sendRedirect("Addfriends.jsp");
			}
		} catch (CustomException | IOException e) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp?message=" + e.getMessage());
			requestDispatcher.forward(request, response);
		}

		if (page != null) {
			if (page.equals("Add to friends list")) {
				String[] arr = request.getParameterValues("friendslist");
				HttpSession session = request.getSession();
				int iden = (int) session.getAttribute("userId");
				try {
					addToList(arr, iden, manager);
					RequestDispatcher requestDispatcher = request
							.getRequestDispatcher("AddFriends.jsp?message=friends added");
					requestDispatcher.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (page.equals("added trip")) {
				int tripId = manager.generateTripId();
				String tripName = (String) obj.get("tripname");
				String startDate = (String) obj.get("startdate");
				String endDate = (String) obj.get("enddate");
				String[] arr = (String[]) obj.get("friendslist");

				SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
				try {
					Date d = f.parse(startDate);
					Date d1 = f.parse(endDate);
					long frommilliseconds = d.getTime();
					long tomilliseconds = d1.getTime();
					HttpSession session = request.getSession();
					int iden = (int) session.getAttribute("userId");
					Trip trip = new Trip(tripId, tripName, frommilliseconds, tomilliseconds);
					manager.addNewTrip(trip);
					addNewTripMembers(arr, iden, tripId, manager);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (page.equals("Add Friends")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddFriends.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("Add Trip")) {
				List<Integer> friends = manager.getFriendsList((int) request.getSession(false).getAttribute("userId"));
				request.setAttribute("friendsList", friends);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddTrip.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("Add Expenses")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddExpense.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("Amount spent")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AmountSpend.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("Return owed amount")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReturnOweAmount.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("Logout")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("signup")) {
				String name = (String) obj.get("name");
				String email = (String) obj.get("email");
				String phone = (String) obj.get("phonenumber");
				String pass = (String) obj.get("password");
				int userId = manager.generateUserId();
				try {
					User user = new User(name, userId, email, Long.parseLong(phone), pass);
					manager.addNewUser(user);
					response.sendRedirect("Login.jsp?message=your user id is " + userId);

				} catch (Exception e) {
					response.sendRedirect("Login.jsp?message=" + e.getMessage());
				}

			}
		}

	}

	public void addToList(String[] arr, int id, Manager manager) throws NumberFormatException, Exception {
		for (int i = 0; i < arr.length; i++) {
			manager.addNewFriend(id, Integer.parseInt(arr[i]));
		}
	}

	public void addNewTripMembers(String[] arr, int id, int tripId, Manager manager)
			throws NumberFormatException, Exception {
		for (int i = 0; i < arr.length; i++) {
			manager.addMemberToTrip(id, Integer.parseInt(arr[i]), tripId);
		}
	}

	private JSONObject getJson(HttpServletRequest request) throws IOException, org.json.simple.parser.ParseException {
		BufferedReader readerObj = request.getReader();
		StringBuilder builderObj = new StringBuilder();
		String line = null;
		while ((line = readerObj.readLine()) != null) {
			builderObj.append(line);
			System.out.println(line);
		}
		return (JSONObject) new JSONParser().parse(builderObj.toString());
	}

	public void init(ServletConfig config) {
		try {
			super.init(config);
			Manager logicLayer = new Manager();
			config.getServletContext().setAttribute("logic", logicLayer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		config.getServletContext();

	}

}
