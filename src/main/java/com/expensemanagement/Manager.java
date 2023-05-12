package com.expensemanagement;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.exception.CustomException;
import com.expensemanagement.datastore.*;

public class Manager {
	private Connector db = null;
	private ExpenseManager exp = null;

	public Manager() throws Exception {
		db = new DBLayer();
		exp = new ExpenseManager();
	}

	public int generateUserId() {
		return exp.generateUserId();
	}

	public int generateTripId() {
		return exp.generateTripId();
	}

	public void setTripId(int tripId) {
		exp.setTripId(tripId);
	}

	public int generateExpenseId() {
		return exp.generateExpenseId();
	}

	public void addNewFriend(int userId, int friendId) throws CustomException {
		db.addNewFriend(userId, friendId);
	}

	public void addNewUser(User user) throws Exception {
		exp.addNewUser(user);
		db.addNewUser(user);
	}

	public boolean login(int userId, String password) throws CustomException {
		return exp.login(userId, password);
	}

	public List<User> getFriendsList(int userId) {
		return db.getFriends(userId);
	}

	public void addNewTrip(Trip trip, int userId) throws CustomException {
		db.addNewTrip(trip, userId);
	}

	public boolean checkFriendsList(int userId, int friendsId) throws CustomException {
		return exp.checkFriendsList(userId, friendsId);
	}

	public void addMemberToTrip(int fromId, int toId, int tripId) throws CustomException {

		db.addNewMember(toId, tripId);
	}

	public void addExpense(Expense expense) throws CustomException {

		db.addNewExpense(expense.getFromUserId(), expense.getToUserId(), expense.getTripId(), expense.getAmount(),
				expense.getDescription(), expense.getExpenseId());

	}

	public List<User> getTripFriends(int tripId) throws CustomException {
		return db.getTripFriends(tripId);
	}

	public void payAllExpense(int userId) throws CustomException {
		db.payAllOweAmount(userId);
	}

	public void payIndividualExpense(int fromUser, int toUser, int expenseId) throws CustomException {
		System.out.println("from user " + fromUser);
		System.out.println("from user " + toUser);
		db.payOweAmount(fromUser, toUser, expenseId);
	}

	public List<Expense> showOweAmount(int userId) {
		return db.showOweAmount(userId);
	}

	public Map<Integer, User> getUserMap() {
		return exp.getUserMap();
	}

	public List<Expense> showTotalAmountSpent(int userId) {
		return db.showSpendAmount(userId);
	}

	public String removeFromFriendsList(int userId, int friendsId) throws CustomException {
		return db.removeFromFriendsList(userId, friendsId);
	}

	public List<Trip> getTripList(int userId, int start) {
		return db.getTrips(userId, start);
	}

	public List<Trip> getAllTrip(int userId) {
		return db.getAllTrips(userId);
	}

	public User getUser(int userId) throws CustomException {
		return exp.getUser(userId);
	}

	public void checkDuplicateTrip(long fromDate, long toDate, int userId) throws CustomException {
		exp.checkDuplicateDate(fromDate, toDate, userId);
	}

	public int getRows() throws CustomException {
		return db.getUserRows();
	}

	public int getFriendsRows(int userId) throws CustomException {
		return db.getFriendRows(userId);
	}

	public int getWithoutFriendsRows(int userId) throws CustomException {
		return db.getWithoutFriendRows(userId);
	}

	public int getTripRows(int userId) throws CustomException {
		return db.getTripRows(userId);
	}

	public List<User> getUserData(int start, int userId) throws CustomException {
		return db.getUserData(start, userId);
	}

	public List<User> getFriendsData(int start, int userId) throws CustomException {
		return db.getFriendsData(start, userId);
	}

	public List<User> getWithoutFriendsData(int start, int userId) throws CustomException {
		return db.getWithoutFriendsData(start, userId);
	}

	public User login(User bean) {
		return db.login(bean);
	}

	public String encrptyPassword(String password) {
		return exp.encryptPassword(password);
	}

	public List<User> getListWithoutFriends(int userId) {
		return db.getListWithoutFriends(userId);
	}

	public Set<Expense> retrieveOwedUserId(int userId) {
		return db.retrieveOwedUserId(userId);

	}

	public Set<Integer> retrieveOwedExpenseId(int userId, int toUser) {
		return db.retrieveOwedExpenseId(userId, toUser);
	}

}
