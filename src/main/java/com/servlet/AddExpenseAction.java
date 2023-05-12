package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.exception.CustomException;
import com.expensemanagement.Manager;
import com.expensemanagement.datastore.Expense;
import com.expensemanagement.datastore.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet("/AddExpenseAction")
public class AddExpenseAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddExpenseAction() {
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
		Manager manager = (Manager) request.getServletContext().getAttribute("logic");
		String page = (String) obj.get("page");
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") == null) {
			response.sendError(440, "session expired");
			return;
		}
		if (page != null && page.equals("tripid for expense")) {
			String value = (String) obj.get("tripid");
			List<User> memberList = null;
			try {
				nullChecker(value);
				memberList = manager.getTripFriends(Integer.parseInt(value));
				Gson gson = new Gson();
				String element = gson.toJson(memberList, new TypeToken<ArrayList<User>>() {
				}.getType());
				response.getWriter().write(element);
			} catch (Exception e) {
				response.getWriter().write("AddExpense.jsp?message=" + e.getMessage());
			}
		} else {
			String tripId = (String) obj.get("tripid");
			String amount = (String) obj.get("amount");
			String description = (String) obj.get("description");
			JSONArray arr = (JSONArray) obj.get("friendslist");
			int iden = (int) session.getAttribute("userId");
			try {
				nullChecker(arr);
				if (arr.isEmpty()) {
					throw new CustomException("Field cannot be empty");
				}
				nullChecker(tripId);
				nullChecker(amount);
				String[] amountArray = amount.split("\\.");
				if (amountArray[0].length() >= 7) {
					throw new CustomException("Amount must less than 7 characters");
				}
				float payAmount = Float.parseFloat(amount);
				if (payAmount <= 0) {
					throw new CustomException("Amount must be greater than zero");
				}
				addNewExpense(arr, iden, manager, payAmount, description, Integer.parseInt(tripId));
				response.getWriter().write("AddExpense.jsp?message=Expense added successfully");
			} catch (Exception e) {
				response.getWriter().write("AddExpense.jsp?message= " + e.getMessage());
			}
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

	public void nullChecker(Object val) throws CustomException {
		if (val == null || val.equals("")) {
			throw new CustomException("Field cannot be empty");
		}
	}

	public void addNewExpense(JSONArray arr, int id, Manager manager, float amount, String description, int tripId)
			throws NumberFormatException, Exception {
		float val = amount / (arr.size());
		float roundOff = (float) Math.round(val * 100) / 100;
		for (int i = 0; i < arr.size(); i++) {
			if (id != Integer.parseInt((String) arr.get(i))) {
				Expense exp = new Expense(id, Integer.parseInt((String) arr.get(i)), roundOff, description, tripId,
						manager.generateExpenseId());
				manager.addExpense(exp);
			}
		}
	}
}
