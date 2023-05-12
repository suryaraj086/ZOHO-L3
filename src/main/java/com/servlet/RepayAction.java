package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.exception.CustomException;
import com.expensemanagement.Manager;
import com.expensemanagement.datastore.Expense;
import com.expensemanagement.datastore.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet("/RepayAction")
public class RepayAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RepayAction() {
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
		if (page.equals("repay all")) {
			int iden = (int) session.getAttribute("userId");
			try {
				manager.payAllExpense(iden);
				response.getWriter().write("ReturnOweAmount.jsp?message=repay successful");
			} catch (CustomException e) {
				response.getWriter().write("ReturnOweAmount.jsp?message=" + e.getMessage());
				e.printStackTrace();
			}

		} else if (page.equals("repay individual owe amount")) {
			int iden = (int) session.getAttribute("userId");
			String expId = (String) obj.get("expenseid");
			System.out.println("exp"+expId);
			String userId = (String) obj.get("userid");
			try {
				nullChecker(expId);
				nullChecker(userId);
				manager.payIndividualExpense(iden, Integer.parseInt(userId), Integer.parseInt(expId));
				response.getWriter().write("ReturnOweAmount.jsp?message=repay successful");
			} catch (CustomException e) {
				e.printStackTrace();
				response.getWriter().write("ReturnOweAmount.jsp?message=" + e.getMessage());
			}
		} else if (page.equals("get owed user id")) {
			int iden = (int) session.getAttribute("userId");
			Set<Expense> set = manager.retrieveOwedUserId(iden);
			System.out.println(set);
			Gson gson = new Gson();
			String element = gson.toJson(set, new TypeToken<HashSet<Expense>>() {
			}.getType());
			response.getWriter().write(element);
		} else if (page.equals("get owed expense id")) {
			int iden = (int) session.getAttribute("userId");
			String userId = (String) obj.get("userid");
			try {
				nullChecker(userId);
				Set<Integer> set = manager.retrieveOwedExpenseId(iden, Integer.parseInt(userId));
				Gson gson = new Gson();
				String element = gson.toJson(set, new TypeToken<HashSet<Integer>>() {
				}.getType());
				response.getWriter().write(element);
			} catch (CustomException e) {
				e.printStackTrace();
				response.getWriter().write("ReturnOweAmount.jsp?message=" + e.getMessage());
			}
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

}
