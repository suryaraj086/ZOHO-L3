package com.expensemanagement;

import com.expensemanagement.datastore.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.exception.CustomException;

import java.util.List;

public class ExpenseManager {

	private Map<Integer, User> userMap = new HashMap<>();
	private Map<Integer, List<Integer>> friendsMap = new HashMap<>();
	private Map<Integer, Trip> tripMap = new HashMap<>();
	private Map<Integer, List<Integer>> members = new HashMap<>();
	private Map<Integer, List<Expense>> expenseMap = new HashMap<>();
	private int userId;
	private int expenseId;
	private int tripId;

	private Connector db = new DBLayer();

	public ExpenseManager() throws Exception {
		userId = db.retrieveUserId();
		tripId = db.retrieveTripId();
		expenseId = db.retrieveExpenseId();
	}

	public int generateUserId() {
		return ++userId;
	}

	public int generateTripId() {
		return tripId;
	}
	
	public void setTripId(int tripId) {
		this.tripId=tripId;
	}

	public int generateExpenseId() {
		return ++expenseId;
	}

	public String addNewFriend(int userId, int friendId) throws CustomException {
		List<Integer> friends = friendsMap.get(userId);
		if (friends == null) {
			friends = new ArrayList<>();
		}
		if (userId == friendId) {
			return "added successfully";
		} else if (!userMap.containsKey(friendId)) {
			throw new CustomException("User not found");
		} else if (friends.contains(friendId)) {
			throw new CustomException("User already added to friends");
		}
		friends.add(friendId);
		friendsMap.put(userId, friends);
		return "added successfully";
	}

	public String addNewUser(User user) throws CustomException {
		nullChecker(user);
		user.setPassword(encryptPassword(user.getPassword()));
//		userMap.put(user.getUserId(), user);
		return "added successfully";
	}

	public boolean login(int userId, String password) throws CustomException {
		User user = userMap.get(userId);
		if (user == null) {
			throw new CustomException("Invalid user id");
		}
		if (user.getPassword().equals(encryptPassword(password))) {
			return true;
		}
		throw new CustomException("Invalid user id or password");
	}

	public void nullChecker(Object inp) throws CustomException {
		if (inp == null) {
			throw new CustomException("Error");
		}
	}

	public List<Integer> getFriendsList(int userId) {
		return friendsMap.get(userId);
	}

	public String addNewTrip(Trip trip) throws CustomException {
		nullChecker(trip);
		tripMap.put(trip.getTripId(), trip);
		return "Added successfully";
	}

	public boolean checkFriendsList(int userId, int friendsId) throws CustomException {
		List<Integer> list = friendsMap.get(userId);
		nullChecker(list);
		return list.contains(friendsId);
	}

	public void addMemberToTrip(int fromId, int toId, int tripId) throws CustomException {
		if (fromId == toId) {
			return;
		} else if (!checkFriendsList(fromId, toId)) {
			throw new CustomException("you are not friend with this id");
		}
		List<Integer> list = members.get(tripId);
		if (list == null) {
			list = new ArrayList<>();
		}
		list.add(toId);
		members.put(tripId, list);
	}

	public String addExpense(Expense expense) throws CustomException {
		nullChecker(expense);
		List<Expense> expenseList = expenseMap.get(expense.getFromUserId());
		if (expenseList == null) {
			expenseList = new ArrayList<>();
		}
		expenseList.add(expense);
		expenseMap.put(expense.getFromUserId(), expenseList);

		List<Expense> expenseLists = expenseMap.get(expense.getToUserId());
		if (expenseLists == null) {
			expenseLists = new ArrayList<>();
		}
		expenseId++;
		Expense exp = new Expense(expense.getToUserId(), expense.getFromUserId(), -expense.getAmount(),
				expense.getDescription(), expense.getTripId(), expenseId);
		expenseLists.add(exp);
		expenseMap.put(expense.getToUserId(), expenseLists);
		return "Added successfully";
	}

	public String payAllExpense(int userId) throws CustomException {

		List<Expense> exp = expenseMap.get(userId);
		if (exp == null || exp.isEmpty()) {
			throw new CustomException("You don't owe to anyone");
		}

		int val = 0;
		for (int i = 0; i < exp.size(); i++) {
			Expense obj = exp.get(i);
			if (obj.getAmount() < 0) {
				List<Expense> toUser = expenseMap.get(obj.getToUserId());
				for (int j = 0; j < toUser.size(); j++) {
					if (toUser.get(j).getAmount() > 0 && toUser.get(j).getToUserId() == obj.getFromUserId()) {
						toUser.remove(j);
						expenseMap.put(obj.getToUserId(), toUser);
						val++;
					}
				}
				exp.remove(i);
			}
		}

		if (val == 0) {
			throw new CustomException("You don't owe to anyone");
		}
		expenseMap.put(userId, exp);
		return "Amount repayed";
	}

	public void payIndividualExpense(int fromUser, int toUser, int expenseId) throws CustomException {

		List<Expense> exp = expenseMap.get(fromUser);
		List<Expense> list = expenseMap.get(toUser);
		if (exp == null || exp.isEmpty()) {
			throw new CustomException("you don't owe the amount to user id " + toUser);
		}
		int val = 0;
		for (int i = 0; i < exp.size(); i++) {
			Expense obj = exp.get(i);
			if (obj.getAmount() < 0 && obj.getToUserId() == toUser && obj.getExpenseId() == expenseId) {
				expenseMap.put(fromUser, exp);
				for (int j = 0; j < list.size(); j++) {
					Expense expens = list.get(j);
					if (expens.getAmount() > 0 && expens.getFromUserId() == toUser
							&& expens.getExpenseId() == expenseId - 1) {
						list.remove(j);
						exp.remove(i);
						val++;
					}
				}
			}
			if (val == 0) {
				throw new CustomException("you don't owe the amount to user id " + toUser);
			}
			expenseMap.put(toUser, list);
		}
	}

	public String encryptPassword(String password) {
		char[] chars = password.toCharArray();
		StringBuilder encryptedPassword = new StringBuilder();
		char k;
		for (char c : chars) {
			if (c == 'Z') {
				k = 'A';
			} else if (c == 'z') {
				k = 'a';
			} else if (c == '9') {
				k = '0';
			} else {
				k = (char) (c + 1);
			}
			encryptedPassword.append(k);
		}
		return encryptedPassword.toString();
	}

	public Map<Integer, User> getUserMap() {
		return userMap;
	}

	public List<Expense> showOweAmount(int userId) {
		List<Expense> exp = expenseMap.get(userId);
		List<Expense> out = new ArrayList<>();
		if (exp == null) {
			return null;
		}
		for (int i = 0; i < exp.size(); i++) {
			Expense obj = exp.get(i);
			if (obj.getAmount() < 0) {
				out.add(obj);
			}
		}
		return out;
	}

	public List<Expense> showAmountSpent(int userId) {
		List<Expense> exp = expenseMap.get(userId);
		List<Expense> out = new ArrayList<>();
		if (exp == null) {
			return null;
		}
		for (int i = 0; i < exp.size(); i++) {
			Expense obj = exp.get(i);
			if (obj.getAmount() > 0) {
				out.add(obj);
			}
		}
		return out;
	}

	public List<Integer> getTripFriends(int tripId) throws CustomException {
		List<Integer> member = members.get(tripId);
		if (member == null) {
			throw new CustomException("Invalid trip id");
		}
		return member;
	}

	public String removeFromFriendsList(int userId, int friendsId) throws CustomException {
		List<Integer> friends = friendsMap.get(userId);
		if (friends == null) {
			throw new CustomException("There is no friends yet");
		}
		int index = friends.indexOf(friendsId);
		if (index < 0) {
			throw new CustomException("you are not friend with user id " + friendsId);
		}
		friends.remove(index);
		return "Removed successfully";
	}

	public List<Trip> getTripList(int userId) {
		List<Trip> trip = new ArrayList<>();
		for (int tripId : members.keySet()) {
			List<Integer> memberList = members.get(tripId);
			if (memberList.contains(userId)) {
				Trip tripObj = tripMap.get(tripId);
				trip.add(tripObj);
			}
		}
		return trip;
	}

	public User getUser(int userId) throws CustomException {
		User user = userMap.get(userId);
		if (user == null) {
			throw new CustomException("user not found");
		}
		return user;
	}

	public void checkDuplicateDate(long fromDate, long toDate, int userId) throws CustomException {
		for (int tripId : members.keySet()) {
			List<Integer> member = members.get(tripId);
			if (member.contains(userId)) {
				Trip trip = tripMap.get(tripId);
				if ((fromDate >= trip.getFromDate() && fromDate <= trip.getToDate())
						|| (toDate >= trip.getFromDate() && toDate <= trip.getToDate())) {
					throw new CustomException("You are already in a trip for the given date");
				}
			}
		}
	}

}
