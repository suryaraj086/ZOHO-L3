package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
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

@WebServlet("/RemoveFriendsAction")
public class RemoveFriendsAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public RemoveFriendsAction() {
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
		HttpSession session = request.getSession();
		int iden = (int) session.getAttribute("userId");
		if (session.getAttribute("userId") == null) {
			response.sendError(440, "session expired");
			return;
		}
		JSONArray arr = (JSONArray) obj.get("friendslist");
		try {
			if (arr == null || arr.isEmpty()) {
				throw new CustomException("Select friends to remove");
			}
			removeFromFriendsList(arr, iden, manager);
			response.getWriter().write("AddFriends.jsp?message=removed successfully");
		} catch (CustomException e) {
			response.getWriter().write("AddFriends.jsp?message=" + e.getMessage());
		}
	}

	public void removeFromFriendsList(JSONArray arr, int userId, Manager manager) throws CustomException {
		for (int i = 0; i < arr.size(); i++) {
			nullChecker(arr.get(i));
			manager.removeFromFriendsList(userId, Integer.parseInt((String) arr.get(i)));
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

}
