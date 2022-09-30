package expensemanagement;

import java.sql.*;
import expensemanagement.pojo.*;
import connection.ConnectionUtility;
import exception.CustomException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBLayer implements Connector {

	public void createTable() throws CustomException, Exception {

		Connection con = ConnectionUtility.CONNECTION.getConnection();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			stmt.execute(
					"create table trip(tridid int Not null primary key,tripname varchar(max),fromdate Date,todate Date)");
			stmt.execute(
					"create table user(userid int Not null primary key,username varchar(max),phonenumber bigint,password varchar(max))");
			stmt.execute("create table friends( FOREIGN KEY (userid) REFERENCES user(userid),friendsid int");
			stmt.execute(
					"create table tripmembers(tridid int,FOREIGN KEY(tripid) reference trip(tridid)),memberid int,FOREIGN KEY(memberid) reference user(userid))");
			stmt.execute(
					"create table tripexpense(expenseid int,tridid int, foreign key(tripid) reference trip(tripid),fromuser int,touser int,amount int,description varchar(255)");
		} catch (Exception e) {
			throw new CustomException("sql error");
		} finally {
			stmt.close();
			con.close();
		}
	}

	public String addNewTrip(Trip obj) throws Exception {

		Connection con = ConnectionUtility.CONNECTION.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into trip values(?,?,?,?)");
			stmt.setInt(1, obj.getTripId());
			stmt.setString(2, obj.getTripName());
			Date d1 = new Date(obj.getFromDate());
			stmt.setDate(3, (java.sql.Date) d1);
			Date d2 = new Date(obj.getToDate());
			stmt.setDate(4, (java.sql.Date) d2);
			int i = stmt.executeUpdate();
			return i + " records inserted";
		} catch (Exception e) {
		} finally {
			stmt.close();
			con.close();
		}
		return null;
	}

	public String addNewFriend(int userId, int friendId) throws Exception {

		Connection con = ConnectionUtility.CONNECTION.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into friends values(?,?)");
			stmt.setInt(1, userId);
			stmt.setInt(2, friendId);
			int i = stmt.executeUpdate();
			return i + " records inserted";
		} catch (Exception e) {
			throw new CustomException("Cannot execute query");
		} finally {
			stmt.close();
			con.close();
		}
	}

	public String addNewUser(User obj) throws Exception {

		Connection con = ConnectionUtility.CONNECTION.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into user values(?,?,?,?,?)");
			stmt.setInt(1, obj.getUserId());
			stmt.setString(2, obj.getName());
			stmt.setString(3, obj.getEmailId());
			stmt.setLong(4, obj.getPhonenumber());
			stmt.setString(5, obj.getPassword());
			int i = stmt.executeUpdate();
			return i + " records inserted";
		} catch (Exception e) {
			throw new CustomException("Cannot execute query");
		} finally {
			stmt.close();
			con.close();
		}
	}

	public String addNewMember(int memberId, int tripId) throws Exception {

		Connection con = ConnectionUtility.CONNECTION.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into tripmembers values(?,?,?,?)");
			stmt.setInt(1, memberId);
			stmt.setInt(2, tripId);
			int i = stmt.executeUpdate();
			return i + " records inserted";
		} catch (Exception e) {
			throw new CustomException("Cannot execute query");
		} finally {
			stmt.close();
			con.close();
		}
	}

	public String addNewExpense(int fromUserId, int toUserId, int tripId, float amount, String description,
			int expenseId) throws Exception {

		Connection con = ConnectionUtility.CONNECTION.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = con.prepareStatement("insert into tripexpense values(?,?,?,?,?,?)");
			stmt.setInt(1, expenseId);
			stmt.setInt(2, tripId);
			stmt.setInt(3, fromUserId);
			stmt.setInt(4, toUserId);
			stmt.setFloat(5, amount);
			stmt.setString(6, description);
			int i = stmt.executeUpdate();

			stmt = con.prepareStatement("insert into tripexpense values(?,?,?,?,?,?)");
			stmt.setInt(1, ++expenseId);
			stmt.setInt(2, tripId);
			stmt.setInt(3, toUserId);
			stmt.setInt(4, fromUserId);
			stmt.setFloat(5, -amount);
			stmt.setString(6, description);
			i = stmt.executeUpdate();

			return i + " records inserted";
		} catch (Exception e) {
			throw new CustomException("Cannot execute query");
		} finally {
			stmt.close();
			con.close();
		}
	}

	public String payOweAmount(int fromUserId, int toUserId, int tripId, int amount, int expenseId)
			throws CustomException {

		try (Statement st = ConnectionUtility.CONNECTION.getConnection().createStatement()) {
			String query = "DELETE FROM tripexpense where expenseid=" + expenseId;
			String query1 = "DELETE FROM tripexpense WHERE expenseid=" + (expenseId + 1);
			st.executeQuery(query);
			st.executeQuery(query1);
		} catch (Exception e) {
			throw new CustomException("Cannot execute query");
		}
		return null;
	}

	public String payAllOweAmount(int fromUserId) throws CustomException {

		try (Statement st = ConnectionUtility.CONNECTION.getConnection().createStatement()) {
			String query = "DELETE FROM tripexpense WHERE touser=" + fromUserId;
			String query1 = "DELETE FROM tripexpense WHERE fromuser=" + fromUserId + "and amount<0";
			st.executeQuery(query);
			st.executeQuery(query1);
		} catch (Exception e) {
			throw new CustomException("Cannot execute query");
		}
		return "successful";
	}

	public Map<Integer, User> retrieveUser() throws CustomException {
		int id = 0;
		Map<Integer, User> userMap = new HashMap<>();
		try (Statement st = ConnectionUtility.CONNECTION.getConnection().createStatement()) {
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
			throw new CustomException("Cannot execute query");
		}
		return userMap;
	}

	public Map<Integer, Trip> retrieveTrip() throws CustomException {
		int tripId = 0;
		Map<Integer, Trip> tripMap = new HashMap<>();
		try (Statement st = ConnectionUtility.CONNECTION.getConnection().createStatement()) {
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
			throw new CustomException("Cannot execute query");
		}
		return tripMap;
	}

	public Map<Integer, List<Integer>> retrieveTripMembers() throws CustomException {
		int tripId = 0;
		Map<Integer, List<Integer>> tripMemberMap = new HashMap<>();
		try (Statement st = ConnectionUtility.CONNECTION.getConnection().createStatement()) {
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
			throw new CustomException("Cannot execute query");
		}
		return tripMemberMap;
	}

	public Map<Integer, List<Integer>> retrieveUserFriends() throws CustomException {
		int tripId = 0;
		Map<Integer, List<Integer>> friendsMap = new HashMap<>();
		try (Statement st = ConnectionUtility.CONNECTION.getConnection().createStatement()) {
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
			throw new CustomException("SQL Error");
		}
		return friendsMap;
	}
	
	
	
	public Map<Integer, List<Integer>> retrieveUserExpense() throws CustomException {
		int tripId = 0;
		Map<Integer, List<Integer>> friendsMap = new HashMap<>();
		try (Statement st = ConnectionUtility.CONNECTION.getConnection().createStatement()) {
			String query = "SELECT * FROM tripexpense";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				tripId = rs.getInt("tripid");
				int memberId = rs.getInt("memberid");
				List<Integer> list = friendsMap.get(tripId);
				if (list == null) {
					list = new ArrayList<>();
				}
				list.add(memberId);
				friendsMap.put(tripId, list);
			}
		} catch (Exception e) {
			throw new CustomException("SQL Error");
		}
		return friendsMap;
	}
	
	

	public int retrieveUserId() throws SQLException, CustomException {
		int userId = 0;
		try (Statement st = ConnectionUtility.CONNECTION.getConnection().createStatement()) {
			String query = "SELECT * FROM user ORDER BY userid DESC LIMIT 1;";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				userId = rs.getInt("userid");
			}
		}
		return userId;
	}
	
	
	public int retrieveTripId() throws SQLException, CustomException {
		int tripId = 0;
		try (Statement st = ConnectionUtility.CONNECTION.getConnection().createStatement()) {
			String query = "SELECT * FROM trip ORDER BY tripid DESC LIMIT 1;";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				tripId = rs.getInt("userid");
			}
		}
		return tripId;
	}
	
	public int retrieveExpenseId() throws SQLException, CustomException {
		int userId = 0;
		try (Statement st = ConnectionUtility.CONNECTION.getConnection().createStatement()) {
			String query = "SELECT * FROM tripexpense ORDER BY expenseid DESC LIMIT 1;";
			ResultSet rs = st.executeQuery(query);
			while (rs.next()) {
				userId = rs.getInt("userid");
			}
		}
		return userId;
	}
	
	
}
