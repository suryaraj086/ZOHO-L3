package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.expensemanagement.datastore.Trip;

@WebServlet("/AddTripAction")
public class AddTripAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AddTripAction() {
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
		String tripName = (String) obj.get("tripname");
		String startDate = (String) obj.get("startdate");
		String endDate = (String) obj.get("enddate");
		JSONArray arr = (JSONArray) obj.get("friendslist");
		SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
		HttpSession session = request.getSession();
		if (session.getAttribute("userId") == null) {
			response.sendError(440, "session expired");
			return;
		}
		int userId = (int) session.getAttribute("userId");

		try {
			if (arr == null || arr.isEmpty()) {
				throw new CustomException("Select friends for trip");
			}
			nullChecker(startDate);
			nullChecker(endDate);
			nullChecker(tripName);
			Date d = f.parse(startDate);
			Date d1 = f.parse(endDate);
			long frommilliseconds = d.getTime();
			long tomilliseconds = d1.getTime();
			if (frommilliseconds > tomilliseconds) {
				throw new CustomException("from date should be greater than to date");
			}
			int tripId = manager.generateTripId();
			tripId = tripId + 1;
			Trip trip = new Trip(tripId, tripName, frommilliseconds, tomilliseconds);
			manager.addNewTrip(trip, userId);
			addNewTripMembers(arr, userId, tripId, manager);
			manager.setTripId(tripId);
			response.getWriter().write("AddTrip.jsp?message=Trip added successfully");
		} catch (Exception e) {
			response.getWriter().write("AddTrip.jsp?message= " + e.getMessage());
		}

	}

	public void addNewTripMembers(JSONArray arr, int id, int tripId, Manager manager) throws CustomException {
		manager.addMemberToTrip(id, id, tripId);
		for (int i = 0; i < arr.size(); i++) {
			manager.addMemberToTrip(id, Integer.parseInt((String) arr.get(i)), tripId);
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
