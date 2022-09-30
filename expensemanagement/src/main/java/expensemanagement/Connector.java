package expensemanagement;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import exception.CustomException;
import expensemanagement.pojo.Trip;
import expensemanagement.pojo.User;

public interface Connector {

	public void createTable() throws CustomException, Exception;

	public String payOweAmount(int fromUserId, int toUserId, int tripId, int amount, int expenseId) throws Exception;

	public String payAllOweAmount(int fromUserId) throws Exception;

	public String addNewTrip(Trip obj) throws Exception;

	public String addNewFriend(int userId, int friendId) throws Exception;

	public String addNewUser(User obj) throws Exception;

	public String addNewMember(int memberId, int tripId) throws Exception;

	public String addNewExpense(int fromUserId, int toUserId, int tripId, float f, String description, int expenseId)
			throws Exception;

	public Map<Integer, User> retrieveUser() throws Exception;

	public Map<Integer, Trip> retrieveTrip() throws Exception;

	public Map<Integer, List<Integer>> retrieveTripMembers() throws Exception;

	public Map<Integer, List<Integer>> retrieveUserFriends() throws Exception;

	public Map<Integer, List<Integer>> retrieveUserExpense() throws CustomException;

	public int retrieveUserId() throws SQLException, CustomException;

	public int retrieveTripId() throws SQLException, CustomException;

	public int retrieveExpenseId() throws SQLException, CustomException;
}
