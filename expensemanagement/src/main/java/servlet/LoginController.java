package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import exception.CustomException;

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

		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String page = request.getParameter("page");
//		Map<Integer,User> userMap=expenseManager.getUserData();
//		User data = userMap.get(Integer.parseInt(id));
		if (id != null && password != null) {
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddFriends.jsp");
			requestDispatcher.forward(request, response);
		}
		if (page != null) {
			if (page.equals("Add Friends")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddFriends.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("Add Trip")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddTrip.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("Add Expenses")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AddExpense.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("Amount spent")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("AmountSpend.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("Amount owed")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("ReturnOweAmount.jsp");
				requestDispatcher.forward(request, response);
			} else if (page.equals("Logout")) {
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("Login.jsp");
				requestDispatcher.forward(request, response);
			}
		}
		doGet(request, response);
	}

	public void init(ServletConfig config) {
		try {
			super.init(config);
//			APILayer logicLayer = new APILayer();
//			config.getServletContext().setAttribute("logic", logicLayer);
		} catch (ServletException e) {
			e.printStackTrace();
		}
		config.getServletContext();

	}

}
