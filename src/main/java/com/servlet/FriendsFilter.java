package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.expensemanagement.datastore.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@WebServlet("/FriendsFilter")
public class FriendsFilter extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FriendsFilter() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("filter");
		JSONObject obj = null;
		try {
			obj = getJson(request);
		} catch (IOException | org.json.simple.parser.ParseException e) {
			e.printStackTrace();
		}
		String page = (String) obj.get("page");
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") == null) {
			response.sendError(440, "session expired");
			return;
		}
		Manager manager = (Manager) request.getServletContext().getAttribute("logic");
		if (page.equals("filter_all")) {
			List<User> userList = null;
			try {
				int iden = (int) session.getAttribute("userId");
				userList = manager.getUserData(0, iden);
				System.out.println(userList);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			Gson gson = new Gson();
			String element = gson.toJson(userList, new TypeToken<ArrayList<User>>() {
			}.getType());
			response.getWriter().write(element);
		} else if (page.equals("filter_friends")) {
			int iden = (int) session.getAttribute("userId");
			List<User> friends = null;
			try {
				friends = manager.getFriendsData(0, iden);
			} catch (CustomException e) {
				response.getWriter().write("message=" + e.getMessage());
			}
			Gson gson = new Gson();
			String element = gson.toJson(friends, new TypeToken<ArrayList<User>>() {
			}.getType());
			response.getWriter().write(element);
		} else if (page.equals("filter_not_friends")) {
			int iden = (int) session.getAttribute("userId");
			List<User> user = null;
			try {
				user = manager.getWithoutFriendsData(0, iden);
			} catch (CustomException e) {
				response.getWriter().write("message=" + e.getMessage());
			}
			Gson gson = new Gson();
			String element = gson.toJson(user, new TypeToken<ArrayList<User>>() {
			}.getType());
			response.getWriter().write(element);
		} else if (page.equals("pagination") && ((String) obj.get("filter")).equals("filter_all")) {
			int pageNo = Integer.parseInt((String) obj.get("pageNo"));
			pageNo = pageNo * 10;
			List<User> userList = null;
			int userId = (int) session.getAttribute("userId");
			try {
				userList = manager.getUserData(pageNo, userId);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			Gson gson = new Gson();
			String element = gson.toJson(userList, new TypeToken<ArrayList<User>>() {
			}.getType());
			response.getWriter().write(element);

		} else if (page.equals("pagination") && ((String) obj.get("filter")).equals("filter_friends")) {
			int pageNo = Integer.parseInt((String) obj.get("pageNo"));
			pageNo = pageNo * 10;
			List<User> userList = null;
			try {
				int iden = (int) session.getAttribute("userId");
				userList = manager.getFriendsData(pageNo, iden);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			Gson gson = new Gson();
			String element = gson.toJson(userList, new TypeToken<ArrayList<User>>() {
			}.getType());
			response.getWriter().write(element);

		} else if (page.equals("pagination") && ((String) obj.get("filter")).equals("filter_not_friends")) {

			int pageNo = Integer.parseInt((String) obj.get("pageNo"));
			pageNo = pageNo * 10;
			List<User> userList = null;
			try {
				int iden = (int) session.getAttribute("userId");
				userList = manager.getWithoutFriendsData(pageNo, iden);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			Gson gson = new Gson();
			String element = gson.toJson(userList, new TypeToken<ArrayList<User>>() {
			}.getType());
			response.getWriter().write(element);
		} else if (page.equals("get_total_pages") && ((String) obj.get("filter")).equals("filter_all")) {

			JSONObject json = new JSONObject();
			try {
				int rows = manager.getRows();
				rows = (int) Math.ceil(rows / 10.0);
				json.put("pages", rows);
				response.getWriter().write(json.toString());
			} catch (CustomException e) {
				e.printStackTrace();
			}
		} else if (page.equals("get_total_pages") && ((String) obj.get("filter")).equals("filter_friends")) {

			JSONObject json = new JSONObject();
			try {
				int userId = (int) session.getAttribute("userId");
				int rows = manager.getFriendsRows(userId);
				rows = (int) Math.ceil(rows / 10.0);
				json.put("pages", rows);
				response.getWriter().write(json.toString());
			} catch (CustomException e) {
				e.printStackTrace();
			}
		} else if (page.equals("get_total_pages") && ((String) obj.get("filter")).equals("filter_not_friends")) {

			JSONObject json = new JSONObject();
			try {
				int userId = (int) session.getAttribute("userId");
				int rows = manager.getWithoutFriendsRows(userId);
				rows = (int) Math.ceil(rows / 10.0);
				json.put("pages", rows);
				response.getWriter().write(json.toString());
			} catch (CustomException e) {
				e.printStackTrace();
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

}
