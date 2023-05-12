package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.exception.CustomException;
import com.expensemanagement.*;
import com.expensemanagement.datastore.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
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

		String emaiId = (String) obj.get("id");
		String password = (String) obj.get("password");
		String page = (String) obj.get("page");
		Manager manager = (Manager) request.getServletContext().getAttribute("logic");
		HttpSession session = request.getSession();

		if (page.equals("login")) {
			try {
				if (emaiId != null && password != null && !emaiId.isEmpty() && !password.isEmpty()) {
					password = manager.encrptyPassword(password);
					User user = new User(page, 0, emaiId, 0, password);
					user = manager.login(user);
					if (user == null) {
						throw new Exception("Invalid email id or password");
					}
					session.setAttribute("userId", user.getUserId());
					session.setAttribute("userName", user.getName());
					session.setAttribute("emailId", user.getEmailId());
					response.getWriter().write("AddFriends.jsp");
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("Login.jsp?message=" + e.getMessage());
				return;
			}
		} else if (page.equals("Add Friends")) {

			response.getWriter().write("AddFriends.jsp");

		} else if (page.equals("get friends data")) {
			if (session.getAttribute("userId") == null) {
				response.sendError(440, "session expired");
				return;
			}
			int iden = (int) session.getAttribute("userId");
			List<User> friends = manager.getFriendsList(iden);
			Gson gson = new Gson();
			String element = gson.toJson(friends, new TypeToken<ArrayList<Trip>>() {
			}.getType());
			response.getWriter().write(element);

		} else if (page.equals("Add Trip")) {

			response.getWriter().write("AddTrip.jsp");

		} else if (page.equals("Add Expenses")) {

			response.getWriter().write("AddExpense.jsp");

		} else if (page.equals("Amount spent")) {

			response.getWriter().write("AmountSpend.jsp");

		} else if (page.equals("get owed amount")) {
			if (session.getAttribute("userId") == null) {
				response.sendError(440, "session expired");
				return;
			}

			int iden = (int) session.getAttribute("userId");
			List<Expense> out = manager.showOweAmount(iden);
			Gson gson = new Gson();
			String element = gson.toJson(out, new TypeToken<ArrayList<Trip>>() {
			}.getType());
			response.getWriter().write(element);

		} else if (page.equals("List Trip")) {

			response.getWriter().write("TripList.jsp");

		} else if (page.equals("show trips")) {
			if (session.getAttribute("userId") == null) {
				response.sendError(440, "session expired");
				return;
			}
			int iden = (int) session.getAttribute("userId");
			List<Trip> tripList = manager.getTripList(iden, 0);
			Gson gson = new Gson();
			String element = gson.toJson(tripList, new TypeToken<ArrayList<Trip>>() {
			}.getType());
			response.getWriter().write(element);

		} else if (page.equals("show all trips")) {
			if (session.getAttribute("userId") == null) {
				response.sendError(440, "session expired");
				return;
			}
			int iden = (int) session.getAttribute("userId");
			List<Trip> tripList = manager.getAllTrip(iden);
			Gson gson = new Gson();
			String element = gson.toJson(tripList, new TypeToken<ArrayList<Trip>>() {
			}.getType());
			response.getWriter().write(element);

		} else if (page.equals("get_total_pages")) {
			if (session.getAttribute("userId") == null) {
				response.sendError(440, "session expired");
				return;
			}

			int iden = (int) session.getAttribute("userId");
			JSONObject json = new JSONObject();
			try {
				int rows = manager.getTripRows(iden);
				rows = (int) Math.ceil(rows / 10.0);
				rows = rows - 1;
				System.out.println("the rows are " + rows);
				json.put("pages", rows);
				response.getWriter().write(json.toString());
			} catch (CustomException e) {
				e.printStackTrace();
			}
		} else if (page.equals("pagination")) {
			if (session.getAttribute("userId") == null) {
				response.sendError(440, "session expired");
				return;
			}

			int pageNo = Integer.parseInt((String) obj.get("pageNo"));
			pageNo = pageNo * 10;
			List<Trip> userList = null;
			int iden = (int) session.getAttribute("userId");
			userList = manager.getTripList(iden, pageNo);
			Gson gson = new Gson();
			String element = gson.toJson(userList, new TypeToken<ArrayList<Trip>>() {
			}.getType());
			response.getWriter().write(element);
		} else if (page.equals("get spend amount")) {
			if (session.getAttribute("userId") == null) {
				response.sendError(440, "session expired");
				return;
			}

			int iden = (int) session.getAttribute("userId");
			List<Expense> expense = manager.showTotalAmountSpent(iden);
			Gson gson = new Gson();
			String element = gson.toJson(expense, new TypeToken<ArrayList<Expense>>() {
			}.getType());
			response.getWriter().write(element);
			System.out.println("hi");
		} else if (page.equals("Amount Owed")) {

			response.getWriter().write("OwedAmount.jsp");

		} else if (page.equals("Return owed amount")) {

			response.getWriter().write("ReturnOweAmount.jsp");

		} else if (page.equals("Logout")) {
			session.invalidate();
			response.getWriter().write("Login.jsp");
		}
	}

	public void nullChecker(Object val) throws CustomException {
		if (val == null || val.equals("")) {
			throw new CustomException("Field cannot be empty");
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