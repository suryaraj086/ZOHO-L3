package expensemanagement;

import expensemanagement.pojo.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import exception.CustomException;

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
	
	Connector db=new DBLayer();
	public ExpenseManager() throws Exception {
		userMap=db.retrieveUser();
		userId=db.retrieveUserId();
	}
	

	public int generateUserId() {
		return ++userId;
	}

	public int generateTripId() {
		return ++tripId;
	}

	public int generateExpenseId() {
		return ++expenseId;
	}

	public String addNewFriend(int userId, int friendId) throws CustomException {
		List<Integer> friends = friendsMap.get(userId);
		if (friends == null) {
			friends = new ArrayList<>();
		}
		if (!userMap.containsKey(friendId)) {
			throw new CustomException("User not found");
		}
		friends.add(friendId);
		friendsMap.put(userId, friends);
		return "added successfully";
	}

	public String addNewUser(User user) throws CustomException {
		nullChecker(user);
		user.setPassword(encryptPassword(user.getPassword()));
		userMap.put(user.getUserId(), user);
		return "added successfully";
	}

	public boolean login(int userId, String password) throws CustomException {
		User user = userMap.get(userId);
		nullChecker(user);
		if (user.getPassword().equals(encryptPassword(password))) {
			return true;
		}
		return false;
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
		checkFriendsList(fromId, toId);
		List<Integer> list = members.get(tripId);
		if (list == null) {
			list = new ArrayList<>();
		}
		list.add(toId);
	}

	public String addExpense(Expense expense) throws CustomException {
		nullChecker(expense);
		List<Expense> expenseList = expenseMap.get(expense.getFromUserId());
		if (expenseList == null) {
			expenseList = new ArrayList<>();
		}
		expenseList.add(expense);
		expense.setExpenseId(expenseId);
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

	public String payAllExpense(int userId) {
		
		List<Expense> exp = expenseMap.get(userId);
		
		for (int i = 0; i < exp.size(); i++) {
			
			Expense obj = exp.get(i);
			
			if (obj.getAmount() < 0) {
				
				List<Expense> toUser = expenseMap.get(obj.getToUserId());
				
				for (int j = 0; j < toUser.size(); j++) {
					
					if (toUser.get(i).getToUserId() == obj.getFromUserId()) {
						
						toUser.remove(i);
					}
				}
				exp.remove(i);
			}
		}
		return "Amount repayed";
	}

	public void payIndividualExpense(int fromUser, int toUser, int amount, int expenseId) {
		
		List<Expense> exp = expenseMap.get(fromUser);
		
		for (int i = 0; i < exp.size(); i++) {
			
			Expense obj = exp.get(i);
			
			if (obj.getAmount() < 0 && obj.getToUserId() == toUser && obj.getExpenseId() == expenseId) {
				
				obj.setAmount(obj.getAmount() + amount);
				
				List<Expense> list = expenseMap.get(obj.getToUserId());
				
				for (int j = 0; j < list.size(); j++) {
					
					Expense expens = list.get(i);
					
					if (expens.getAmount() > 0 && obj.getToUserId() == fromUser
							
							&& obj.getExpenseId() == expenseId + 1) {
						
						expens.setAmount(expens.getAmount() - amount);
					}
				}
			}
		}
	}

	private String encryptPassword(String password) {
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

	public String showOweAmount(int userId) {
		List<Expense> exp = expenseMap.get(userId);
		String out = "";
		for (int i = 0; i < exp.size(); i++) {
			Expense obj = exp.get(i);
			if (obj.getAmount() < 0) {
				out += " To " + obj.getToUserId() + ", you owe rs " + Math.abs(obj.getAmount()) + " in trip "
						+ obj.getTripId() + "\n";
			}
		}
		return out;
	}
}
