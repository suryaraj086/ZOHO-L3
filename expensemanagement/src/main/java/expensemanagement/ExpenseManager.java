package expensemanagement;

import expensemanagement.pojo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import exception.CustomException;

import java.util.List;

public class ExpenseManager {

	private Map<Integer, User> userMap = new HashMap<>();
	private Map<Integer, List<Integer>> friendsMap = new HashMap<>();
	private Map<Integer, Trip> tripMap = new HashMap<>();
	private Map<Integer, List<Expense>> expenseMap = new HashMap<>();

	public String addNewFriend(int userId, int friendId) throws CustomException, Exception {
		List<Integer> friends = friendsMap.get(userId);
		if (friends == null) {
			friends = new ArrayList<>();
		}
		if (!userMap.containsKey(friendId)) {
			throw new CustomException("User not found");
		}
		friends.add(friendId);
		return "added successfully";
	}

	public String addNewUser(User user) throws CustomException, Exception {
		nullChecker(user);
		userMap.put(user.getId(), user);
		return "added successfully";
	}

	public void nullChecker(Object inp) throws CustomException, Exception {
		if (inp == null) {
			throw new CustomException("Error");
		}
	}

	public String addNewTrip(Trip trip) throws CustomException, Exception {
		nullChecker(trip);
		tripMap.put(trip.getTripId(), trip);
		return "Added successfully";
	}

	public boolean checkFriendsList(int userId, int friendsId) {
		return friendsMap.get(userId).contains(friendsId);
	}

	public String addExpense(Expense expense) throws CustomException, Exception {
		nullChecker(expense);
		List<Expense> expenseList = expenseMap.get(expense.getFromUserId());
		if (expenseList == null) {
			expenseList = new ArrayList<>();
		}
		expenseList.add(expense);
		List<Expense> expenseLists = expenseMap.get(expense.getToUserId());
		if (expenseLists == null) {
			expenseLists = new ArrayList<>();
		}
		expenseLists.add(expense);
//		expenseMap.put(expense.getFromUserId(), expense);
//		expense.setAmount(-expense.getAmount());
//		expenseMap.put(expense.getToUserId(), expense);
		return "Added successfully";
	}

	public String payExpense(Expense expense) {
		expenseMap.get(expense.getFromUserId());
		return "Amount repayed";
	}
}
