package com.expensemanagement;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.expensemanagement.datastore.*;
import com.connection.ConnectionUtility;
import com.exception.CustomException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DBLayer implements Connector {

	public void createTable() throws CustomException {
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection; Statement stmt = connection.createStatement()) {
			stmt.execute(
					"create table trip(tridid int Not null primary key,tripname varchar(50),fromdate Date,todate Date)");
			stmt.execute(
					"create table user(userid int Not null primary key,username varchar(50),phonenumber bigint,password varchar(50))");
			stmt.execute("create table friends( FOREIGN KEY (userid) REFERENCES user(userid),friendsid int");
			stmt.execute(
					"create table tripmembers(tridid int,FOREIGN KEY(tripid) references trip(tridid)),memberid int,FOREIGN KEY(memberid) references user(userid))");
			stmt.execute(
					"create table tripexpense(expenseid int,tridid int, foreign key(tripid) references trip(tripid),fromuser int,touser int,amount int,description varchar(100)");
		} catch (Exception e) {
			throw new CustomException("sql error");
		}
	}

	public String addNewTrip(Trip obj, int userId) throws CustomException {

		ConnectionUtility con = new ConnectionUtility();
		try (Connection connection = con.getConnection()) {
			PreparedStatement stmt = null;
			stmt = connection.prepareStatement("insert into trip values(?,?,?,?)");
			stmt.setInt(1, obj.getTripId());
			stmt.setString(2, obj.getTripName());
			Date d1 = new Date(obj.getFromDate());
			DateFormat simple = new SimpleDateFormat("yyyy-mm-dd");
			stmt.setString(3, simple.format(d1));
			Date d2 = new Date(obj.getToDate());
//			String query = "select * from trip where tripid in (select tripid from tripmembers where memberid=" + userId
//					+ ") and ((fromdate between '" + simple.format(d1) + "' and '" + simple.format(d2)
//					+ "') or (todate between '" + simple.format(d1) + "' and '" + simple.format(d2) + "'));";
			String query = "select * from trip where tripid in (select tripid from tripmembers where memberid=" + userId
					+ ") and ((fromdate <= '" + simple.format(d1) + "' AND todate >= '" + simple.format(d1)
					+ "') OR (fromdate >= '" + simple.format(d1) + "' AND fromdate <= '" + simple.format(d2) + "'));";

			System.out.println(query);
			ResultSet rs = stmt.executeQuery(query);
			boolean more = rs.next();
			if (more) {
				throw new CustomException("Already trip booked for the given date");
			}
			stmt.setString(4, simple.format(d2));
			int i = stmt.executeUpdate();
			return i + " records inserted";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public String addNewFriend(int userId, int friendId) throws CustomException {

		ConnectionUtility con = new ConnectionUtility();
		try (Connection connection = con.getConnection();) {
			PreparedStatement stmt = null;
			stmt = connection.prepareStatement("insert into friends values(?,?)");
			stmt.setInt(1, userId);
			stmt.setInt(2, friendId);
			int i = stmt.executeUpdate();
			return i + " records inserted";
		} catch (Exception e) {
			throw new CustomException("Cannot add new friend");
		}
	}

	public String addNewUser(User obj) throws CustomException {

		ConnectionUtility con = new ConnectionUtility();
		try (Connection connection = con.getConnection();) {
			PreparedStatement stmt = null;
			stmt = connection.prepareStatement("insert into user values(?,?,?,?,?)");
			String query = "select * from user where emailid='" + obj.getEmailId() + "';";
			ResultSet rs = stmt.executeQuery(query);
			boolean more = rs.next();
			if (more) {
				throw new CustomException("You are already existing user");
			}
			stmt.setInt(1, obj.getUserId());
			stmt.setString(2, obj.getName());
			stmt.setString(3, obj.getEmailId());
			stmt.setLong(4, obj.getPhonenumber());
			stmt.setString(5, obj.getPassword());
			int i = stmt.executeUpdate();
			return i + " records inserted";
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
	}

	public String addNewMember(int memberId, int tripId) throws CustomException {
		ConnectionUtility con = new ConnectionUtility();
		try (Connection connection = con.getConnection();) {
			PreparedStatement stmt = null;
			stmt = connection.prepareStatement("insert into tripmembers values(?,?)");
			stmt.setInt(2, memberId);
			stmt.setInt(1, tripId);
			int i = stmt.executeUpdate();
			return i + " records inserted";
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(e.getMessage());
		}
	}

	public String addNewExpense(int fromUserId, int toUserId, int tripId, float amount, String description,
			int expenseId) throws CustomException {

		ConnectionUtility connect = new ConnectionUtility();
		try (Connection con = connect.getConnection()) {
			PreparedStatement stmt = null;
			stmt = con.prepareStatement("insert into tripexpense values(?,?,?,?,?,?)");
			stmt.setInt(1, expenseId);
			stmt.setInt(2, tripId);
			stmt.setInt(3, fromUserId);
			stmt.setInt(4, toUserId);
			stmt.setFloat(5, amount);
			stmt.setString(6, description);
			int i = stmt.executeUpdate();
			return i + " records inserted";
		} catch (Exception e) {
			throw new CustomException("Cannot add new expense");
		}
	}

	public String payOweAmount(int fromUserId, int toUserId, int expenseId) throws CustomException {

		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection;) {
			PreparedStatement stmt = connection
					.prepareStatement("DELETE FROM tripexpense WHERE expenseid=? and fromuser=? and touser=?;");
			stmt.setInt(1, expenseId);
			stmt.setInt(2, toUserId);
			stmt.setInt(3, fromUserId);
			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new CustomException(
						"You don't owe to user id " + toUserId + " or invalid expense id " + expenseId);
			}
		} catch (Exception e) {
			throw new CustomException(e.getMessage());
		}
		return null;
	}

	public String payAllOweAmount(int fromUserId) throws CustomException {

		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection) {
			PreparedStatement stmt = connection.prepareStatement("DELETE FROM tripexpense WHERE touser=?;");
			stmt.setInt(1, fromUserId);
			int row = stmt.executeUpdate();
			if (row == 0) {
				throw new CustomException("You don't owe to anyone");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CustomException(e.getMessage());
		}

		return "successful";
	}

	public Map<Integer, User> retrieveUser() throws CustomException {
		int id = 0;
		Map<Integer, User> userMap = new HashMap<>();
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection; Statement st = connection.createStatement()) {
			String query = "SELECT * FROM user";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				id = rs.getInt("userid");
				String name = rs.getString("username");
				String email = rs.getString("emailid");
				long phonenumber = rs.getLong("phonenumber");
				String password = rs.getString("password");
				User user = new User(name, id, email, phonenumber, password);
				userMap.put(id, user);
			}
		} catch (Exception e) {
			throw new CustomException("Cannot retrieve user");
		}
		return userMap;
	}

	public Map<Integer, Trip> retrieveTrip() throws CustomException {
		int tripId = 0;
		Map<Integer, Trip> tripMap = new HashMap<>();
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection; Statement st = connection.createStatement()) {
			String query = "SELECT * FROM trip";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				tripId = rs.getInt("tripid");
				String name = rs.getString("tripname");
				Date fromDate = rs.getDate("fromdate");
				Date toDate = rs.getDate("todate");
				long fromMillis = fromDate.getTime();
				long toMillis = toDate.getTime();
				Trip trip = new Trip(tripId, name, fromMillis, toMillis);
				tripMap.put(tripId, trip);
			}
		} catch (Exception e) {
			throw new CustomException("Cannot retrieve trips");
		}

		return tripMap;
	}

	public Map<Integer, List<Integer>> retrieveTripMembers() throws CustomException {
		int tripId = 0;
		Map<Integer, List<Integer>> tripMemberMap = new HashMap<>();
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection; Statement st = connection.createStatement()) {
			String query = "SELECT * FROM tripmembers";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				tripId = rs.getInt("tripid");
				int memberId = rs.getInt("memberid");
				List<Integer> list = tripMemberMap.get(tripId);
				if (list == null) {
					list = new ArrayList<>();
				}
				list.add(memberId);
				tripMemberMap.put(tripId, list);
			}
		} catch (Exception e) {
			throw new CustomException("Cannot retrieve trips");
		}
		return tripMemberMap;
	}

	public Map<Integer, List<Integer>> retrieveUserFriends() throws CustomException {
		int tripId = 0;
		Map<Integer, List<Integer>> friendsMap = new HashMap<>();
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection; Statement st = connection.createStatement()) {
			String query = "SELECT * FROM friends";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				tripId = rs.getInt("userid");
				int memberId = rs.getInt("friendsid");
				List<Integer> list = friendsMap.get(tripId);
				if (list == null) {
					list = new ArrayList<>();
				}
				list.add(memberId);
				friendsMap.put(tripId, list);
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve friends list");
		}
		return friendsMap;
	}

	public Map<Integer, List<Expense>> retrieveUserExpense() throws CustomException {
		int userId = 0;
		Map<Integer, List<Expense>> expenseMap = new HashMap<>();
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection; Statement st = connection.createStatement()) {
			String query = "SELECT * FROM tripexpense";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				userId = rs.getInt("fromuser");
				int toUserID = rs.getInt("touser");
				int expenseId = rs.getInt("expenseid");
				int tripId = rs.getInt("tripid");
				float amount = rs.getFloat("amount");
				List<Expense> list = expenseMap.get(userId);
				if (list == null) {
					list = new ArrayList<>();
				}
				String description = rs.getString("description");
				Expense exp = new Expense(userId, toUserID, amount, description, tripId, expenseId);
				list.add(exp);
				expenseMap.put(userId, list);
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve expense details");
		}
		return expenseMap;
	}

	public int retrieveUserId() throws SQLException, CustomException {
		int userId = 0;
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection; Statement st = connection.createStatement()) {
			String query = "SELECT * FROM user ORDER BY userid DESC LIMIT 1;";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				userId = rs.getInt("userid");
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve user id");
		}
		return userId;
	}

	public int getUserRows() throws CustomException {
		int count = 0;
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection; Statement st = connection.createStatement()) {
			String query = "SELECT count(*) FROM user;";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve user id");
		}
		return count - 1;
	}

	public int getFriendRows(int userId) throws CustomException {
		int count = 0;
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection) {
			PreparedStatement stmt = connection
					.prepareStatement("SELECT count(*) FROM friends where userid=? and friendsid!=?;");
			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve user id");
		}
		return count;
	}

	public int getWithoutFriendRows(int userId) throws CustomException {
		int count = 0;
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection) {
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT count(*) FROM user where userid NOT IN (select friendsid from friends where userid=? and friendsid!=?) and userid!=?;");
			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			stmt.setInt(3, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve user id");
		}
		return count;
	}

	public int getTripRows(int userId) throws CustomException {
		int count = 0;
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection) {
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT count(*) FROM trip where tripid IN (select tripid from tripmembers where memberid=?);");
			stmt.setInt(1, userId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				count = rs.getInt("count(*)");
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve user id");
		}
		return count;
	}

	public List<User> getUserData(int start, int userId) throws CustomException {
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		ArrayList<User> arr = new ArrayList<>();
		int total = 10;
		try (connection) {
			PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user where userid!=? LIMIT ?,?;");
			stmt.setInt(1, userId);
			stmt.setInt(2, start);
			stmt.setInt(3, total);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("userid");
				String name = rs.getString("username");
				String email = rs.getString("emailid");
				long phonenumber = 0;
				String password = "";
				User user = new User(name, id, email, phonenumber, password);
				arr.add(user);
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve user id");
		}
		return arr;
	}

	public List<User> getFriendsData(int start, int userId) throws CustomException {
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		ArrayList<User> arr = new ArrayList<>();
		int total = 10;
		try (connection) {
			PreparedStatement stmt = connection.prepareStatement(
					"select * from friends join user on user.userid=friends.friendsid where friends.userid=? and user.userid!=? LIMIT ?,?;");
			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			stmt.setInt(3, start);
			stmt.setInt(4, total);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("friendsid");
				String name = rs.getString("username");
				String email = rs.getString("emailid");
				User user = new User(name, id, email, 0, "");
				arr.add(user);
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve user id");
		}
		return arr;

	}

	public List<User> getWithoutFriendsData(int start, int userId) throws CustomException {
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		ArrayList<User> arr = new ArrayList<>();
		int total = 10;
		try (connection) {
			PreparedStatement stmt = connection.prepareStatement(
					"SELECT * FROM user where userid NOT IN (select friendsid from friends where userid=?) and userid!=? LIMIT ?,?;");
			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			stmt.setInt(3, start);
			stmt.setInt(4, total);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("userid");
				String name = rs.getString("username");
				String email = rs.getString("emailid");
				User user = new User(name, id, email, 0, "");
				arr.add(user);
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve user id");
		}
		return arr;
	}

	public int retrieveTripId() throws SQLException, CustomException {
		int tripId = 0;
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection; Statement st = connection.createStatement()) {
			String query = "SELECT * FROM trip ORDER BY tripid DESC LIMIT 1;";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				tripId = rs.getInt("tripid");
			}
		} catch (Exception e) {
			throw new CustomException("SQL Error");
		}
		return tripId;
	}

	public int retrieveExpenseId() throws CustomException {
		int expenseId = 0;
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection; Statement st = connection.createStatement()) {
			String query = "SELECT * FROM tripexpense ORDER BY expenseid DESC LIMIT 1;";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				expenseId = rs.getInt("expenseid");
			}
		} catch (Exception e) {
			throw new CustomException("cannot retrieve expense id");
		}
		return expenseId;
	}

	public User login(User bean) {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection currentCon = null;
		User user = null;
		String emailId = bean.getEmailId();
		String password = bean.getPassword();
		try {
			ConnectionUtility con = new ConnectionUtility();
			currentCon = con.getConnection();
			String searchQuery = "select * from user where emailid=? AND password=?";
			stmt = currentCon.prepareStatement(searchQuery);
			stmt.setString(1, emailId);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			boolean more = rs.next();
			if (!more) {
				System.out.println("Sorry, you are not a registered user! Please sign up first");
				return null;
			}

			else if (more) {
				int id = rs.getInt("userid");
				String name = rs.getString("username");
				String email = rs.getString("emailid");
				long phonenumber = rs.getLong("phonenumber");
				user = new User(name, id, email, phonenumber, password);
			}
		} catch (Exception ex) {
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		} finally {
			freeUpResource(rs, stmt, currentCon);
		}
		return user;
	}

	public List<User> getFriends(int userId) {
		List<User> friends = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection currentCon = null;
		User user = null;
		try {
			ConnectionUtility con = new ConnectionUtility();
			currentCon = con.getConnection();
			stmt = currentCon.prepareStatement(
					"select * from friends join user on user.userid=friends.friendsid where friends.userid=?;");
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("friendsid");
				String name = rs.getString("username");
				String email = rs.getString("emailid");
				user = new User(name, id, email, 0, "");
				friends.add(user);
			}
		} catch (Exception ex) {
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		} finally {
			freeUpResource(rs, stmt, currentCon);
		}

		return friends;
	}

	public List<User> getListWithoutFriends(int userId) {
		List<User> friends = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection currentCon = null;
		User user = null;
		String searchQuery = "select * from user where userid NOT IN (select friendsid from friends where userid=?);";
		System.out.println("Query: " + searchQuery);
		try {
			ConnectionUtility con = new ConnectionUtility();
			currentCon = con.getConnection();
			stmt = currentCon.prepareStatement(searchQuery);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("userid");
				String name = rs.getString("username");
				String email = rs.getString("emailid");
				user = new User(name, id, email, 0, "");
				friends.add(user);
			}
		} catch (Exception ex) {
			System.out.println("Log In failed: An Exception has occurred! " + ex);
		} finally {
			freeUpResource(rs, stmt, currentCon);
		}
		return friends;
	}

	public List<Trip> getTrips(int userId, int start) {
		List<Trip> trips = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection currentCon = null;
		int total = 10;
		String searchQuery = "select * from trip where tripid in (select tripid from tripmembers where memberid=?) LIMIT ?,?;";
		try {
			ConnectionUtility con = new ConnectionUtility();
			currentCon = con.getConnection();
			stmt = currentCon.prepareStatement(searchQuery);
			stmt.setInt(1, userId);
			stmt.setInt(2, start);
			stmt.setInt(3, total);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("tripid");
				String name = rs.getString("tripname");
				Date fromDate = rs.getDate("fromdate");
				Date toDate = rs.getDate("todate");
				long fromMillis = fromDate.getTime();
				long toMillis = toDate.getTime();
				Trip trip = new Trip(id, name, fromMillis, toMillis);
				trips.add(trip);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("failed: An Exception has occurred! " + ex);
		} finally {
			freeUpResource(rs, stmt, currentCon);
		}
		return trips;
	}

	public List<Trip> getAllTrips(int userId) {
		List<Trip> trips = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection currentCon = null;
		String searchQuery = "select * from trip where tripid in (select tripid from tripmembers where memberid=?);";
		System.out.println("Query: " + searchQuery);
		try {
			ConnectionUtility con = new ConnectionUtility();
			currentCon = con.getConnection();
			stmt = currentCon.prepareStatement(searchQuery);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("tripid");
				String name = rs.getString("tripname");
				Date fromDate = rs.getDate("fromdate");
				Date toDate = rs.getDate("todate");
				long fromMillis = fromDate.getTime();
				long toMillis = toDate.getTime();
				Trip trip = new Trip(id, name, fromMillis, toMillis);
				trips.add(trip);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("failed: An Exception has occurred! " + ex);
		} finally {
			freeUpResource(rs, stmt, currentCon);
		}
		return trips;
	}

	public List<User> getTripFriends(int tripId) {
		List<User> users = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection currentCon = null;
		String searchQuery = "select * from user join tripmembers on user.userid=tripmembers.memberid where tripid=?;";
		System.out.println("Query: " + searchQuery);
		try {
			ConnectionUtility con = new ConnectionUtility();
			currentCon = con.getConnection();
			stmt = currentCon.prepareStatement(searchQuery);
			stmt.setInt(1, tripId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("userid");
				String name = rs.getString("username");
				String email = rs.getString("emailid");
				User user = new User(name, id, email, 0, "");
				users.add(user);
			}
		} catch (Exception ex) {
			System.out.println("failed: An Exception has occurred! " + ex);
		} finally {
			freeUpResource(rs, stmt, currentCon);
		}
		return users;
	}

	public List<Expense> showSpendAmount(int userId) {
		List<Expense> expense = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection currentCon = null;
		String searchQuery = "SELECT * FROM tripexpense INNER JOIN user ON tripexpense.touser = user.userid where fromuser=?;";
		System.out.println("Query: " + searchQuery);
		try {
			ConnectionUtility con = new ConnectionUtility();
			currentCon = con.getConnection();
			stmt = currentCon.prepareStatement(searchQuery);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				userId = rs.getInt("fromuser");
				int toUserID = rs.getInt("touser");
				int expenseId = rs.getInt("expenseid");
				int tripId = rs.getInt("tripid");
				float amount = rs.getFloat("amount");
				String description = rs.getString("description");
				String toUserName = rs.getString("username");
				Expense exp = new Expense(userId, toUserID, amount, description, tripId, expenseId);
				exp.setToUserName(toUserName);
				expense.add(exp);
			}
		} catch (Exception ex) {
			System.out.println("failed: An Exception has occurred! " + ex);
		} finally {
			freeUpResource(rs, stmt, currentCon);
		}
		return expense;
	}

	public Set<Expense> retrieveOwedUserId(int userId) {
		Set<Expense> expense = new HashSet<Expense>();
		Set<Integer> temp = new HashSet<Integer>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection currentCon = null;
		String searchQuery = "SELECT * FROM tripexpense INNER JOIN user ON tripexpense.fromuser = user.userid where touser=?;";
		System.out.println("Query: " + searchQuery);
		try {
			ConnectionUtility con = new ConnectionUtility();
			currentCon = con.getConnection();
			stmt = currentCon.prepareStatement(searchQuery);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				userId = rs.getInt("fromuser");
				String toUserName = rs.getString("username");
				Expense exp = new Expense(userId, 0, 0, "", 0, 0);
				if(!temp.contains(userId)) {
				 exp.setToUserName(toUserName);
				 expense.add(exp);
				 temp.add(userId);
				}
			}
		} catch (Exception ex) {
			System.out.println("failed: An Exception has occurred! " + ex);
		} finally {
			freeUpResource(rs, stmt, currentCon);
		}
		return expense;
	}

	public Set<Integer> retrieveOwedExpenseId(int userId, int toUser) {
		Set<Integer> expense = new HashSet<Integer>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection currentCon = null;
		String searchQuery = "SELECT expenseid FROM tripexpense INNER JOIN user ON tripexpense.touser = user.userid where fromuser=? and touser=?;";
		System.out.println("Query: " + searchQuery);
		try {
			ConnectionUtility con = new ConnectionUtility();
			currentCon = con.getConnection();
			stmt = currentCon.prepareStatement(searchQuery);
			stmt.setInt(1, toUser);
			stmt.setInt(2, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int expenseId = rs.getInt("expenseid");
				expense.add(expenseId);
			}
		} catch (Exception ex) {
			System.out.println("failed: An Exception has occurred! " + ex);
		} finally {
			freeUpResource(rs, stmt, currentCon);
		}
		return expense;
	}

	public List<Expense> showOweAmount(int userId) {
		List<Expense> expense = new ArrayList<>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Connection currentCon = null;
		String searchQuery = "SELECT * FROM tripexpense INNER JOIN user ON tripexpense.fromuser = user.userid where touser=?;";
		try {
			ConnectionUtility con = new ConnectionUtility();
			currentCon = con.getConnection();
			stmt = currentCon.prepareStatement(searchQuery);
			stmt.setInt(1, userId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				userId = rs.getInt("fromuser");
				int toUserID = rs.getInt("touser");
				int expenseId = rs.getInt("expenseid");
				int tripId = rs.getInt("tripid");
				float amount = rs.getFloat("amount");
				String description = rs.getString("description");
				String toUserName = rs.getString("username");
				Expense exp = new Expense(userId, toUserID, amount, description, tripId, expenseId);
				exp.setToUserName(toUserName);
				expense.add(exp);
			}
		} catch (Exception ex) {
			System.out.println("failed: An Exception has occurred! " + ex);
		} finally {
			freeUpResource(rs, stmt, currentCon);
		}
		return expense;
	}

	public void freeUpResource(ResultSet rs, PreparedStatement stmt, Connection currentCon) {

		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
			}
			rs = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (Exception e) {
			}
			stmt = null;
		}
		if (currentCon != null) {
			try {
				currentCon.close();
			} catch (Exception e) {
			}
			currentCon = null;
		}
	}

	public String removeFromFriendsList(int userId, int friendsId) throws CustomException {
		ConnectionUtility con = new ConnectionUtility();
		Connection connection = con.getConnection();
		try (connection) {
			String query = "DELETE FROM friends where userid=? and friendsid=?;";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setInt(1, userId);
			stmt.setInt(2, friendsId);
			int val = stmt.executeUpdate();
			if (val == 0) {
				throw new CustomException("Already removed");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CustomException("cannot remove from friends list");
		}
		return "removed successfully";
	}

}