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

@WebServlet("/AddFriendsAction")
public class AddFriendsAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddFriendsAction() {
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
		JSONArray arr = (JSONArray) obj.get("friendslist");
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") == null) {
			response.sendError(440,"session expired");
			return;
		}
		int iden = (int) session.getAttribute("userId");
		try {
			if (arr == null || arr.size() == 0) {
				throw new CustomException("Select member to add to friends list");
			}
			addToList(arr, iden, manager);
			response.getWriter()
					.write("AddFriends.jsp?message=friends added successfully");

		} catch (Exception e) {
			response.getWriter()
					.write("AddFriends.jsp?message=" + e.getMessage());
		}

	}

	public void addToList(JSONArray arr, int id, Manager manager) throws NumberFormatException, Exception {
		for (int i = 0; i < arr.size(); i++) {
			manager.addNewFriend(id, Integer.parseInt((String) arr.get(i)));
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
