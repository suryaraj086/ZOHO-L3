package expensemanagement.pojo;

public class User {

	private String name;
	private int userId;
	private String emailId;
	private long phonenumber;
	private String password;

	public User(String name, int userId, String emailId, long phonenumber, String password) {
		super();
		this.name = name;
		this.userId = userId;
		this.emailId = emailId;
		this.phonenumber = phonenumber;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public long getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(long phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
