package com.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import com.exception.CustomException;
import com.expensemanagement.Manager;
import com.expensemanagement.datastore.User;

@WebServlet("/SignUpAction")
public class SignUpAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SignUpAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		String name = (String) obj.get("name");
		String email = (String) obj.get("email");
		String phone = (String) obj.get("phonenumber");
		String password = (String) obj.get("password");
		try {
			nullChecker(password);
			nullChecker(phone);
			nullChecker(email);
			nullChecker(name);
			int upperCaseCount = 0;
			int specialCount = 0;
			for (var i = 0; i < password.length(); i++) {
				if ((password.charAt(i) >= 65 && password.charAt(i) <= 90)) {
					upperCaseCount++;
				} else if (((password.charAt(i)) >= 32 && (password.charAt(i)) <= 47)
						|| ((password.charAt(i) >= 58) && (password.charAt(i)) <= 64)
						|| ((password.charAt(i) >= 91) && (password.charAt(i)) <= 96)) {
					specialCount++;
				}
			}
			if (upperCaseCount == 0) {
				throw new CustomException(
						"password must be greater than 8 characters and 1 special and 1 uppercase character");
			}
			if (specialCount == 0) {
				throw new CustomException(
						"password must be greater than 8 characters and 1 special and 1 uppercase character");
			}
			if (password.length() <= 8) {
				throw new CustomException(
						"password must be greater than 8 characters and 1 special and 1 uppercase character");
			}
			long phoneNo = Long.parseLong(phone);
			int userId = manager.generateUserId();
			User user = new User(name, userId, email, phoneNo, password);
			manager.addNewUser(user);
			response.getWriter().write("Login.jsp");
			return;
		} catch (Exception e) {
			response.getWriter().write("Login.jsp?message=" + e.getMessage());
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
