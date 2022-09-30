package expensemanagement;

import java.util.List;
import java.util.Map;

import exception.CustomException;
import expensemanagement.pojo.Expense;
import expensemanagement.pojo.Trip;
import expensemanagement.pojo.User;

public class Manager {
	private Connector db=null;
	private ExpenseManager exp=null;

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

	public int generateExpenseId() {
		return exp.generateExpenseId();
	}

	public void addNewFriend(int userId, int friendId) throws Exception {
		exp.addNewFriend(userId, friendId);
		db.addNewFriend(userId, friendId);
	}

	public void addNewUser(User user) throws Exception {
		exp.addNewUser(user);
		db.addNewUser(user);
	}

	public boolean login(int userId, String password) throws CustomException {
		return exp.login(userId, password);
	}

	public List<Integer> getFriendsList(int userId) {
		return exp.getFriendsList(userId);
	}

	public void addNewTrip(Trip trip) throws Exception {
		exp.addNewTrip(trip);
		db.addNewTrip(trip);
	}

	public boolean checkFriendsList(int userId, int friendsId) throws CustomException {
		return exp.checkFriendsList(userId, friendsId);
	}

	public void addMemberToTrip(int fromId, int toId, int tripId) throws Exception {

		exp.addMemberToTrip(fromId, toId, tripId);
		db.addNewMember(toId, tripId);
	}

	public void addExpense(Expense expense) throws Exception {

		exp.addExpense(expense);
		db.addNewExpense(expense.getFromUserId(), expense.getToUserId(), expense.getTripId(), expense.getAmount(),
				expense.getDescription(), expense.getExpenseId());

	}

	public void payAllExpense(int userId) throws Exception {
		exp.payAllExpense(userId);
		db.payAllOweAmount(userId);
	}

	public void payIndividualExpense(int fromUser, int toUser, int amount, int tripId, int expenseId) throws Exception {

		exp.payIndividualExpense(fromUser, toUser, amount, expenseId);
		db.payOweAmount(fromUser, toUser, tripId, amount, expenseId);

	}

	public String showOweAmount(int userId) {
		return exp.showOweAmount(userId);
	}

	public Map<Integer, User> getUserMap() {
		return exp.getUserMap();
	}

}
