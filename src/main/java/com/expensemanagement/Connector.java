package com.expensemanagement;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.exception.CustomException;
import com.expensemanagement.datastore.Expense;
import com.expensemanagement.datastore.Trip;
import com.expensemanagement.datastore.User;

public interface Connector {

	public void createTable() throws CustomException;

	public String payOweAmount(int fromUserId, int toUserId, int expenseId) throws CustomException;

	public String payAllOweAmount(int fromUserId) throws CustomException;

	public String addNewTrip(Trip obj, int userId) throws CustomException;

	public String addNewFriend(int userId, int friendId) throws CustomException;

	public String addNewUser(User obj) throws CustomException;

	public String addNewMember(int memberId, int tripId) throws CustomException;

	public String addNewExpense(int fromUserId, int toUserId, int tripId, float f, String description, int expenseId)
			throws CustomException;

	public Map<Integer, User> retrieveUser() throws CustomException;

	public Map<Integer, Trip> retrieveTrip() throws CustomException;

	public Map<Integer, List<Integer>> retrieveTripMembers() throws CustomException;

	public Map<Integer, List<Integer>> retrieveUserFriends() throws CustomException;

	public Map<Integer, List<Expense>> retrieveUserExpense() throws CustomException;

	public int retrieveUserId() throws SQLException, CustomException;

	public int retrieveTripId() throws SQLException, CustomException;

	public int retrieveExpenseId() throws SQLException, CustomException;

	public String removeFromFriendsList(int userId, int friendsId) throws CustomException;

	public int getUserRows() throws CustomException;

	public List<User> getUserData(int start, int userId) throws CustomException;

	public List<User> getFriendsData(int start, int userId) throws CustomException;

	public List<User> getWithoutFriendsData(int start, int userId) throws CustomException;

	public User login(User bean);

	public List<User> getFriends(int userId);

	public List<Trip> getTrips(int userId, int start);

	public List<Trip> getAllTrips(int userId);

	public List<User> getListWithoutFriends(int userId);

	public List<User> getTripFriends(int tripId);

	public int getFriendRows(int userId) throws CustomException;

	public int getWithoutFriendRows(int userId) throws CustomException;

	public List<Expense> showSpendAmount(int userId);

	public List<Expense> showOweAmount(int userId);

	public int getTripRows(int userId) throws CustomException;

	public Set<Expense> retrieveOwedUserId(int userId);

	public Set<Integer> retrieveOwedExpenseId(int userId, int toUser);
}
